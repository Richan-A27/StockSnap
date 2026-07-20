package com.stocksnap.presentation.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.stocksnap.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = androidx.compose.ui.platform.LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                viewModel.signInWithGoogle(account, onLoginSuccess)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Custom Curved Background (Cream top, Forest Green curved bottom)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Top Cream Background
            drawRect(color = Color(0xFFF4F1E6), size = size)
            
            // Bottom Green Curved Background
            val path = Path().apply {
                moveTo(0f, h * 0.58f)
                quadraticBezierTo(w * 0.5f, h * 0.64f, w, h * 0.58f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D4D2D), Color(0xFF052A17))
                )
            )
        }

        // 2. Page Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TOP PORTION (Light Cream Brand Content) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.58f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // ISRAVEL Tree Logo
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "ISRAVEL Logo",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(10.dp))

                // ISRAVEL Label
                Text(
                    text = "ISRAVEL",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D4D2D),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "— SERVING FAMILIES SINCE 1964 —",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC0A355),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // StockSnap Branding
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Stock",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003019)
                    )
                    Text(
                        text = "Snáp",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5B9E5A),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Retail Product Onboarding\nSimplified",
                    fontSize = 15.sp,
                    color = Color.DarkGray.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp
                )
            }

            // --- MIDDLE OVERLAY PORTION (Circular Badge) ---
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color(0xFFE8F5E9), CircleShape)
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                // Viewfinder and Barcode symbol drawn natively
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val stroke = 2.dp.toPx()
                    val bracket = 5.dp.toPx()

                    // Top-Left corner viewfinder
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(0f, bracket), end = Offset(0f, 0f), strokeWidth = stroke)
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(0f, 0f), end = Offset(bracket, 0f), strokeWidth = stroke)
                    
                    // Top-Right
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(w - bracket, 0f), end = Offset(w, 0f), strokeWidth = stroke)
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(w, 0f), end = Offset(w, bracket), strokeWidth = stroke)
                    
                    // Bottom-Left
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(0f, h - bracket), end = Offset(0f, h), strokeWidth = stroke)
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(0f, h), end = Offset(bracket, h), strokeWidth = stroke)
                    
                    // Bottom-Right
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(w - bracket, h), end = Offset(w, h), strokeWidth = stroke)
                    drawLine(color = Color(0xFF0D4D2D), start = Offset(w, h), end = Offset(w, h - bracket), strokeWidth = stroke)

                    // Vertical Barcode Lines
                    val barCount = 6
                    val spacing = w / (barCount + 1)
                    val widths = listOf(1.5f, 3f, 1.5f, 4f, 2f, 1.5f)
                    for (i in 0 until barCount) {
                        drawLine(
                            color = Color(0xFF0D4D2D),
                            start = Offset((i + 1) * spacing, h * 0.2f),
                            end = Offset((i + 1) * spacing, h * 0.8f),
                            strokeWidth = widths[i] * density
                        )
                    }
                }
            }

            // --- BOTTOM PORTION (Dark Green Input/Google Section) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.42f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Welcome back!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(text = "Sign in to continue to ", fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
                        Text(text = "StockSnap", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF81C784))
                    }
                }

                if (loading) {
                    CircularProgressIndicator(
                        color = Color(0xFF81C784),
                        modifier = Modifier.size(36.dp)
                    )
                } else {
                    // Styled Pill Google Sign-In Button
                    Button(
                        onClick = {
                            val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(context.getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build()
                            val signInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso)
                            launcher.launch(signInClient.signInIntent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(26.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Custom High Fidelity Google G representation
                            Canvas(modifier = Modifier.size(18.dp)) {
                                val w = size.width
                                val h = size.height
                                val strokeW = 2.5.dp.toPx()
                                // Left yellow segment
                                drawArc(
                                    color = Color(0xFFFBBC05),
                                    startAngle = 120f,
                                    sweepAngle = 120f,
                                    useCenter = false,
                                    style = Stroke(width = strokeW)
                                )
                                // Top red segment
                                drawArc(
                                    color = Color(0xFFEA4335),
                                    startAngle = 240f,
                                    sweepAngle = 100f,
                                    useCenter = false,
                                    style = Stroke(width = strokeW)
                                )
                                // Bottom green segment
                                drawArc(
                                    color = Color(0xFF34A853),
                                    startAngle = 45f,
                                    sweepAngle = 75f,
                                    useCenter = false,
                                    style = Stroke(width = strokeW)
                                )
                                // Right blue segment & horizontal line
                                drawArc(
                                    color = Color(0xFF4285F4),
                                    startAngle = 340f,
                                    sweepAngle = 65f,
                                    useCenter = false,
                                    style = Stroke(width = strokeW)
                                )
                                drawLine(
                                    color = Color(0xFF4285F4),
                                    start = Offset(w * 0.5f, h * 0.5f),
                                    end = Offset(w * 0.95f, h * 0.5f),
                                    strokeWidth = strokeW
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Sign in with Google",
                                color = Color(0xFF1F1F1F),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                error?.let { err ->
                    Text(
                        text = err,
                        color = Color(0xFFEF5350),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                // Divider and Security Footer
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "or",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Outlined Security Badge
                    Surface(
                        color = Color.Transparent,
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Lock,
                                contentDescription = null,
                                tint = Color(0xFF81C784),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Secure • Private • Built for ISRAVEL",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Built by ISRAVEL, For ISRAVEL",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.35f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
