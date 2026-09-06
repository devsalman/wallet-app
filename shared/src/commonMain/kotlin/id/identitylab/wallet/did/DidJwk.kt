package id.identitylab.wallet.did

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.EC
import dev.whyoleg.cryptography.algorithms.ECDSA
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

/**
 * Minimal `did:jwk` implementation (https://github.com/quartzjer/did-jwk).
 *
 * A `did:jwk` is a deterministic transformation of a public JSON Web Key (JWK)
 * into a DID: the identifier is `did:jwk:` + base64url (no padding) of the
 * UTF-8 JSON of the public JWK. Resolution is pure decoding of the identifier,
 * producing a DID Document with a single `JsonWebKey2020` verification method.
 */
object DidJwk {

    const val SCHEME = "did"
    const val METHOD = "jwk"
    const val PREFIX = "$SCHEME:$METHOD:"

    private val json = Json
    private val privateJwkMembers = setOf("d", "k", "p", "q", "dp", "dq", "qi", "oth", "t")

    /**
     * Generates a fresh P-256 (ES256) key pair and returns the derived
     * `did:jwk` alongside the public and private JWK strings.
     *
     * The private JWK belongs to the wallet: persist it (encrypted) for signing;
     * never put it in a DID. The DID itself is derived from the public JWK only.
     */
    suspend fun generate(provider: CryptographyProvider = CryptographyProvider.Default): JwkKeypair {
        val ecdsa = provider.get(ECDSA)
        val keyPair = ecdsa.keyPairGenerator(EC.Curve.P256).generateKey()
        val publicJwk = keyPair.publicKey.encodeToByteArray(EC.PublicKey.Format.JWK).decodeToString()
        val privateJwk = keyPair.privateKey.encodeToByteArray(EC.PrivateKey.Format.JWK).decodeToString()
        return JwkKeypair(
            did = didFromPublicJwk(publicJwk),
            publicJwk = publicJwk,
            privateJwk = privateJwk,
        )
    }

    /** Derives a `did:jwk` from a public JWK string. */
    fun didFromPublicJwk(publicJwk: String): String =
        PREFIX + Base64Url.encode(publicJwk.encodeToByteArray())

    /**
     * Resolves a `did:jwk` into its DID Document. The document is fully derived
     * from the identifier and validated against the spec: the encoded JWK must be
     * valid JSON, an object, and contain only public key material.
     */
    fun resolve(did: String): DidJwkDocument {
        val identifier = did.substringAfter(PREFIX)
        if (identifier.isEmpty() || !did.startsWith(PREFIX)) {
            throw DidJwkException("Not a did:jwk string: $did")
        }
        val decodedBytes = try {
            Base64Url.decode(identifier)
        } catch (e: IllegalArgumentException) {
            throw DidJwkException("Invalid base64url in did:jwk identifier", e)
        }
        val jwk = parseJwk(decodedBytes.decodeToString(), did)
        validatePublicJwk(jwk, did)
        return DidJwkDocument(id = did, publicKeyJwk = jwk)
    }

    private fun parseJwk(text: String, did: String): JsonObject {
        val element = try {
            json.parseToJsonElement(text)
        } catch (e: SerializationException) {
            throw DidJwkException("did:jwk identifier does not decode to valid JSON: $did", e)
        }
        return (element as? JsonObject)
            ?: throw DidJwkException("did:jwk identifier does not decode to a JWK object: $did")
    }

    private fun validatePublicJwk(jwk: JsonObject, did: String) {
        val kty = (jwk["kty"] as? JsonPrimitive)?.contentOrNull
            ?: throw DidJwkException("did:jwk JWK is missing 'kty': $did")
        if (kty !in setOf("EC", "OKP", "RSA")) {
            throw DidJwkException("Unsupported JWK 'kty' '$kty' in did:jwk identifier: $did")
        }
        if (jwk.keys.any { it in privateJwkMembers }) {
            throw DidJwkException("did:jwk identifier must contain only public key material: $did")
        }
        val missing = jwkRequiredMembers.getValue(kty).filterNot { it in jwk }
        if (missing.isNotEmpty()) {
            throw DidJwkException("did:jwk JWK (kty=$kty) is missing required member(s) ${missing.joinToString(", ")}: $did")
        }
    }

    private val jwkRequiredMembers = mapOf(
        "EC" to setOf("crv", "x", "y"),
        "OKP" to setOf("crv", "x"),
        "RSA" to setOf("n", "e"),
    )
}