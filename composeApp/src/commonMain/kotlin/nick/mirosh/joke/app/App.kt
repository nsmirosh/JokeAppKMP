package nick.mirosh.joke.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview


const val URL = "https://v2.jokeapi.dev/joke/Any?type=single"

@Composable
fun App() {
    MaterialTheme {
        var client: HttpClient? by remember { mutableStateOf(null) }
        //to make sure that we're not creating a new client on every recomposition
        var joke by remember { mutableStateOf<Joke?>(null) }

        LaunchedEffect(Unit) {
            client = createHttpClient()
        }
        val scope = rememberCoroutineScope()
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    joke = client!!.get(URL).body()
                }
            }) {
                Text("Generate a joke")
            }
            joke?.let {
                Text(
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp,
                    text = it.joke
                )
            }
        }
    }
}

private fun createHttpClient() =
    createPlatformHttpClient().config {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }


@Serializable
data class Joke(
    val joke: String,
)
