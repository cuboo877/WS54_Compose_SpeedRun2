package com.example.ws54_compose_speedrun2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ws54_compose_speedrun2.service.SimpleNavController
import com.example.ws54_compose_speedrun2.service.SimpleNavHost
import com.example.ws54_compose_speedrun2.service.jsonToMap
import com.example.ws54_compose_speedrun2.service.readJsonFromAssets
import com.example.ws54_compose_speedrun2.ui.theme.Ws54_compose_speedrun2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ws54_compose_speedrun2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val jsonString = readJsonFromAssets(this, "weatherData.json")
                    val data = jsonToMap(jsonString)
                    val navController = SimpleNavController(Screen.Choose)
                    SimpleNavHost(navController = navController, modifier = Modifier.fillMaxSize(),data = data)
                }
            }
        }
    }
    enum class Screen{
        Choose,
        Home,
        Region
    }
}