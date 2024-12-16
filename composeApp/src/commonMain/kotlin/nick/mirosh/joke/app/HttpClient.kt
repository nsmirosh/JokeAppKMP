package nick.mirosh.joke.app

import io.ktor.client.HttpClient

expect fun createPlatformHttpClient(): HttpClient
