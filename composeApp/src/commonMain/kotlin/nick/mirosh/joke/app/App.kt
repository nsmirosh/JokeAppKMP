package nick.mirosh.joke.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Column {
                Button(onClick = {
                    scope.launch(Dispatchers.IO) { requestJoke() }
                }) {
                    Text("Generate a joke")
                }
            }
        }
    }
}

private suspend fun requestJoke() {
    val client = createPlatformHttpClient()
    val response = client.get("https://v2.jokeapi.dev/joke/Any?type=single")
    if (response.status.value == 200) {
        println("Joke received!")
    }
}