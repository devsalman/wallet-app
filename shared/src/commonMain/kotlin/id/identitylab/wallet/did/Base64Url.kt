package id.identitylab.wallet.did

import kotlin.io.encoding.Base64
import kotlin.io.encoding.Base64.PaddingOption

internal object Base64Url {
    private val encoder: Base64 = Base64.UrlSafe.withPadding(PaddingOption.ABSENT)

    fun encode(bytes: ByteArray): String = encoder.encode(bytes)

    fun decode(value: String): ByteArray = encoder.decode(value)
}