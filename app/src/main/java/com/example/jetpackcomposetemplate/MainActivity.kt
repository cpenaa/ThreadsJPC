package com.example.jetpackcomposetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Reemplazamos setContentView(...) por setContent de Compose
        setContent {
            FinalProjectIDNPApp()
        }
    }
}

@Composable
fun FinalProjectIDNPApp() {
    // Surface aplica el fondo y estilo del tema
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HelloFinalProject()
    }
}

@Composable
fun HelloFinalProject() {
    Text(text = "Hola desde Compose 🚀")
}
