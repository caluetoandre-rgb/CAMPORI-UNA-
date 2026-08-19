package com.example.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.PathfinderGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer
import java.util.concurrent.Executors

@Composable
fun CameraQrScanner(
    onCodeScanned: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var isScanningActive by remember { mutableStateOf(true) }
    var lastScannedCode by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CamporiNavy)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = PathfinderYellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Câmera QR Scanner Ativa",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Fechar Câmera",
                        tint = Color.White
                    )
                }
            }

            if (hasCameraPermission) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    // CameraX Preview View
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }

                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            val cameraExecutor = Executors.newSingleThreadExecutor()

                            cameraProviderFuture.addListener({
                                try {
                                    val cameraProvider = cameraProviderFuture.get()

                                    val preview = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }

                                    val reader = MultiFormatReader().apply {
                                        val hints = mapOf(
                                            DecodeHintType.POSSIBLE_FORMATS to listOf(com.google.zxing.BarcodeFormat.QR_CODE),
                                            DecodeHintType.TRY_HARDER to true
                                        )
                                        setHints(hints)
                                    }

                                    val imageAnalysis = ImageAnalysis.Builder()
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build()
                                        .also { analysis ->
                                            analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                                                if (isScanningActive) {
                                                    val scanned = processImageProxy(reader, imageProxy)
                                                    if (!scanned.isNullOrBlank()) {
                                                        isScanningActive = false
                                                        lastScannedCode = scanned
                                                        onCodeScanned(scanned)
                                                    }
                                                }
                                                imageProxy.close()
                                            }
                                        }

                                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                                    cameraProvider.unbindAll()
                                    cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        cameraSelector,
                                        preview,
                                        imageAnalysis
                                    )
                                } catch (exc: Exception) {
                                    Log.e("CameraQrScanner", "Use case binding failed", exc)
                                }
                            }, ContextCompat.getMainExecutor(ctx))

                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // Target scanning reticle overlay
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .border(2.dp, PathfinderYellow, RoundedCornerShape(12.dp))
                    )

                    // Help text overlay
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = if (lastScannedCode != null) "✓ Código Lido: $lastScannedCode" else "Aponte a câmera para o QR Code do Crachá",
                            color = if (lastScannedCode != null) PathfinderGreen else Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            } else {
                // Permission request fallback box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(Color(0xFF1E1E1E))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VideocamOff,
                            contentDescription = null,
                            tint = PathfinderYellow,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Permissão de Câmera Necessária",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Para ler crachás instantaneamente na portaria, autorize o acesso à câmera.",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                            colors = ButtonDefaults.buttonColors(containerColor = PathfinderYellow, contentColor = CamporiNavy),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Autorizar Câmera", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

private fun processImageProxy(reader: MultiFormatReader, imageProxy: ImageProxy): String? {
    val plane = imageProxy.planes[0]
    val buffer: ByteBuffer = plane.buffer
    val data = ByteArray(buffer.remaining())
    buffer.get(data)

    val width = imageProxy.width
    val height = imageProxy.height

    val source = PlanarYUVLuminanceSource(
        data,
        width,
        height,
        0,
        0,
        width,
        height,
        false
    )

    val bitmap = BinaryBitmap(HybridBinarizer(source))
    return try {
        val result = reader.decodeWithState(bitmap)
        result.text
    } catch (e: Exception) {
        null
    } finally {
        reader.reset()
    }
}
