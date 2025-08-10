package com.example.ict_check

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ict_check.ui.QrCodeScanner
import com.example.ict_check.ui.theme.ICT_CheckTheme
import androidx.compose.material3.ExperimentalMaterial3Api



class MainActivity : ComponentActivity() {

    private val CAMERA_PERMISSION_CODE = 1001

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasCameraPermission()) {
            requestCameraPermission()
        }

        setContent {
            ICT_CheckTheme {
                var qrText by remember { mutableStateOf("Esperando escaneo...") }

                Scaffold(
                    topBar = {
                        androidx.compose.material3.CenterAlignedTopAppBar(
                            title = { Text("UNMSM - lector ICT")  })
                    },
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(16.dp),
                        ) {
                            // Mostrar la cámara para escanear
                            Box(modifier = Modifier.weight(1f)) {
                                QrCodeScanner { scannedValue ->
                                    Log.d("QR", scannedValue)
                                    qrText = scannedValue
                                }
                            }

                            // Mostrar el valor escaneado
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Valor escaneado:",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = qrText,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                )
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }



}