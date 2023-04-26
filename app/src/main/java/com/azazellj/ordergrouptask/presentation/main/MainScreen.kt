package com.azazellj.ordergrouptask.presentation.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.azazellj.ordergrouptask.domain.model.Query
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
) {
    val currentQuery: Query? by mainViewModel.currentQuery.collectAsState()

    var currentQueryText: String by remember { mutableStateOf("") }

    val context: Context = LocalContext.current

    fun openScanner() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                mainViewModel.fetchData(
                    query = barcode.rawValue.orEmpty(),
                )
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
            }
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            openScanner()
        },
    )

    MainScreenView(
        currentQueryText = currentQueryText,
        onCurrentQueryTextChanged = {
            currentQueryText = it
        },
        currentQuery = currentQuery,
        onCurrentQuerySearchClick = {
            mainViewModel.fetchData(
                query = currentQueryText,
            )
        },
        onOpenQRScannerClick = {
            if (!hasPermission(context, Manifest.permission.CAMERA)) {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                return@MainScreenView
            }
            openScanner()
        },
    )
}

fun hasPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}


@Composable
private fun MainScreenView(
    currentQueryText: String,
    onCurrentQueryTextChanged: (String) -> Unit,
    currentQuery: Query?,
    onCurrentQuerySearchClick: () -> Unit,
    onOpenQRScannerClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
            .fillMaxSize()
            .background(
                color = Color.White,
            ),
    ) {
        TextField(
            value = currentQueryText,
            onValueChange = onCurrentQueryTextChanged,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )

        Button(
            onClick = onCurrentQuerySearchClick,
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = "Search")
        }

        Button(
            onClick = onOpenQRScannerClick,
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = "Open QR Scanner")
        }

        if (currentQuery != null) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Text(text = "Unit name: ${currentQuery.unitName}")
                Text(text = "Station: ${currentQuery.station}")
                Text(text = "Available date: ${currentQuery.availableDate}")
                Text(text = "Query: ${currentQuery.query}")
            }
        }
    }
}
