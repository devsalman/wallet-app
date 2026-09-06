package id.identitylab.wallet.did

data class JwkKeypair(
    val did: String,
    val publicJwk: String,
    val privateJwk: String,
)