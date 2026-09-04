package id.identitylab.wallet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform