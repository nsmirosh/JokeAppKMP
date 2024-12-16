package nick.mirosh.joke.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform