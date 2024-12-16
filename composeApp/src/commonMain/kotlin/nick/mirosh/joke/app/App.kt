package nick.mirosh.joke.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview


const val URL = "https://v2.jokeapi.dev/joke/Any?type=single"

@Composable
fun App() {
    MaterialTheme {
        var client: HttpClient? = null
        //to make sure that we're not creating a new client on every recomposition
        LaunchedEffect(Unit) {
            client = createPlatformHttpClient().config {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
            }
        }
        val scope = rememberCoroutineScope()
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Column {
                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        val joke: Joke = client!!.get(URL).body()
                        println(joke.joke)
                    }
                }) {
                    Text("Generate a joke")
                }
            }
        }
    }
}

@Serializable
data class Joke(
    val joke: String,
)
