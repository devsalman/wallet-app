package id.identitylab.wallet.did

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

/**
 * The DID Document produced by [DidJwk.resolve] for a `did:jwk` identifier.
 *
 * Resolution is deterministic and stateless: the whole document is derived from
 * the public JWK encoded in the DID identifier. The embedded JWK is preserved
 * verbatim (member order and unknown members survive), as required by the
 * did:jwk specification.
 */
class DidJwkDocument internal constructor(
    val id: String,
    val publicKeyJwk: JsonObject,
) {
    private val keyUse: String? get() = (publicKeyJwk["use"] as? JsonPrimitive)?.contentOrNull

    val verificationMethodId: String get() = "$id#0"

    /** True unless the JWK `use` is "enc" (encryption-only keys get no signing relationships). */
    val includesSigningRelationships: Boolean get() = keyUse != "enc"

    /** True unless the JWK `use` is "sig" (signing-only keys get no keyAgreement). */
    val includesKeyAgreement: Boolean get() = keyUse != "sig"

    fun toJsonObject(): JsonObject = buildJsonObject {
        putJsonArray("@context") {
            add("https://www.w3.org/ns/did/v1")
            add("https://w3id.org/security/suites/jws-2020/v1")
        }
        put("id", id)
        putJsonArray("verificationMethod") {
            addJsonObject {
                put("id", verificationMethodId)
                put("type", "JsonWebKey2020")
                put("controller", id)
                put("publicKeyJwk", publicKeyJwk)
            }
        }
        if (includesSigningRelationships) {
            signingRelationship("assertionMethod")
            signingRelationship("authentication")
            signingRelationship("capabilityInvocation")
            signingRelationship("capabilityDelegation")
        }
        if (includesKeyAgreement) {
            putJsonArray("keyAgreement") { add(verificationMethodId) }
        }
    }

    fun toJson(): String = toJsonObject().toString()

    private fun JsonObjectBuilder.signingRelationship(name: String) {
        putJsonArray(name) { add(verificationMethodId) }
    }
}