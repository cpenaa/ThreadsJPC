package com.example.jetpackcomposetemplate.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposetemplate.ui.viewmodel.SyncState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SyncStatusBar(state: SyncState) {

    AnimatedVisibility(
        visible = state != SyncState.Idle,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when (state) {
                        SyncState.Syncing -> Color(0xFF1565C0)   // Azul
                        SyncState.Success -> Color(0xFF2E7D32)   // Verde
                        SyncState.Error -> Color(0xFFC62828)     // Rojo
                        else -> Color.Transparent
                    }
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                    when (state) {
                        SyncState.Syncing -> "Sincronizando..."
                        SyncState.Success -> "Sincronización completa"
                        SyncState.Error -> "Error al sincronizar"
                        else -> ""
                    },
                color = Color.White
            )
        }
    }
}
