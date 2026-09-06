package id.identitylab.wallet.did

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class DidJwkTest {

    private val json = Json

    @Test
    fun generateProducesAPaddedFreeJwkDid() = runTest {
        val keypair = DidJwk.generate()

        assertTrue(keypair.did.startsWith(DidJwk.PREFIX), "DID must carry the did:jwk prefix")
        assertFalse(keypair.did.contains('='), "base64url identifier must be unpadded")
        assertFalse(keypair.did.contains('/'), "base64url identifier must be url-safe")
        assertEquals("EC", publicJwkField(keypair.publicJwk, "kty"), "P-256 public JWK should be an EC key")
        assertTrue(keypair.privateJwk.contains("\"d\""), "private JWK must contain the private scalar")
    }

    @Test
    fun resolveRoundTripsGeneratedDid() = runTest {
        val keypair = DidJwk.generate()
        val document = DidJwk.resolve(keypair.did)

        assertEquals(keypair.did, document.id)
        assertEquals("${keypair.did}#0", document.verificationMethodId)
        assertTrue(document.includesSigningRelationships)
        assertTrue(document.includesKeyAgreement)

        val verificationMethod = verificationMethod(document)
        assertEquals("JsonWebKey2020", verificationMethod["type"]!!.jsonPrimitive.content)
        assertEquals(keypair.did, verificationMethod["controller"]!!.jsonPrimitive.content)
        assertEquals(json.parseToJsonElement(keypair.publicJwk), verificationMethod["publicKeyJwk"])
    }

    @Test
    fun generatedDidEmbedsOnlyPublicMaterial() = runTest {
        val keypair = DidJwk.generate()
        val decoded = Base64Url.decode(keypair.did.removePrefix(DidJwk.PREFIX)).decodeToString()
        assertFalse(decoded.contains("\"d\""), "public JWK in DID must not contain the private scalar")
    }

    @Test
    fun resolvesKnownSpecTestVector() {
        val did = "did:jwk:eyJjcnYiOiJQLTI1NiIsImt0eSI6IkVDIiwieCI6ImFjYklRaXVNczNpOF91c3pFakoydHBUdFJNNEVVM3l6OTFQSDZDZEgyVjAiLCJ5IjoiX0tjeUxqOXZXTXB0bm1LdG00NkdxRHo4d2Y3NEk1TEtncmwyR3pIM25TRSJ9"

        val document = DidJwk.resolve(did)
        val expectedJwk = json.parseToJsonElement(
            """{"crv":"P-256","kty":"EC","x":"acbIQiuMs3i8_uszEjJ2tpTtRM4EU3yz91PH6CdH2V0","y":"_KcyLj9vWMptnmKtm46GqDz8wf74I5LKgrl2GzH3nSE"}"""
        )

        assertEquals(did, document.id)
        assertEquals(expectedJwk, document.publicKeyJwk)

        val doc = document.toJsonObject()
        assertEquals(
            listOf("https://www.w3.org/ns/did/v1", "https://w3id.org/security/suites/jws-2020/v1"),
            strings(doc["@context"]!!),
        )
        val vmId = "$did#0"
        for (relationship in listOf(
            "assertionMethod",
            "authentication",
            "capabilityInvocation",
            "capabilityDelegation",
        )) {
            assertEquals(listOf(vmId), strings(doc.getValue(relationship)), "missing $relationship")
        }
        assertEquals(listOf(vmId), strings(doc.getValue("keyAgreement")))
    }

    @Test
    fun didDerivationIsDeterministic() {
        val jwk = """{"crv":"P-256","kty":"EC","x":"acbIQiuMs3i8_uszEjJ2tpTtRM4EU3yz91PH6CdH2V0","y":"_KcyLj9vWMptnmKtm46GqDz8wf74I5LKgrl2GzH3nSE"}"""
        assertEquals(DidJwk.didFromPublicJwk(jwk), DidJwk.didFromPublicJwk(jwk))
    }

    @Test
    fun resolveRejectsPrivateKeyMaterial() = runTest {
        val keypair = DidJwk.generate()
        val didFromPrivate = DidJwk.didFromPublicJwk(keypair.privateJwk)

        val exception = assertFailsWith<DidJwkException> {
            DidJwk.resolve(didFromPrivate)
        }
        assertTrue(exception.message.orEmpty().contains("public key material"))
    }

    @Test
    fun resolveRejectsNonJwkPayload() {
        val did = DidJwk.PREFIX + Base64Url.encode("[1,2,3]".encodeToByteArray())
        assertFailsWith<DidJwkException> { DidJwk.resolve(did) }
    }

    @Test
    fun resolveRejectsInvalidBase64url() {
        assertFailsWith<DidJwkException> { DidJwk.resolve("did:jwk:@@@@") }
    }

    @Test
    fun resolveRejectsMissingPrefixOrEmpty() {
        assertFailsWith<DidJwkException> { DidJwk.resolve("did:key:z6MkhaXgBZDvotDkL5257faiztiGiC2QtKLGpbnnEGta2doK") }
        assertFailsWith<DidJwkException> { DidJwk.resolve("did:jwk:") }
        assertFailsWith<DidJwkException> { DidJwk.resolve("did:jwk") }
    }

    @Test
    fun resolveRejectsPaddedIdentifier() = runTest {
        val keypair = DidJwk.generate()
        val padded = keypair.did.removePrefix(DidJwk.PREFIX) + "="
        assertFailsWith<DidJwkException> { DidJwk.resolve(DidJwk.PREFIX + padded) }
    }

    @Test
    fun distinctKeysProduceDistinctDids() = runTest {
        val first = DidJwk.generate()
        val second = DidJwk.generate()
        assertNotEquals(first.did, second.did)
    }

    private fun verificationMethod(document: DidJwkDocument): JsonObject {
        val array = document.toJsonObject()["verificationMethod"] as JsonArray
        return array.single() as JsonObject
    }

    private fun publicJwkField(jwk: String, field: String): String {
        val element = json.parseToJsonElement(jwk)
        return (element as JsonObject)[field]!!.jsonPrimitive.content
    }

    private fun strings(element: JsonElement): List<String> =
        (element as JsonArray).map { (it as JsonPrimitive).content }
}