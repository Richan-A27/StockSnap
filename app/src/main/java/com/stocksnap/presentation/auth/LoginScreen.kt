package com.stocksnap.presentation.auth

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.stocksnap.presentation.components.GlassButton
import com.stocksnap.presentation.components.GlassCard
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary
import java.security.MessageDigest

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
        } catch (e: ApiException) {
            e.printStackTrace()
            val clientId = context.getString(R.string.default_web_client_id)
            viewModel.setError("Google Sign-In failed (Code ${e.statusCode}). ClientID: ${clientId.take(15)}...")
        } catch (e: Exception) {
            e.printStackTrace()
            viewModel.setError("Sign-In error: ${e.localizedMessage}")
        }
    }

    // Calculate SHA-1 for debugging Code 10
    val sha1 = remember {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            val signatures = info.signatures
            if (signatures != null && signatures.isNotEmpty()) {
                val md = MessageDigest.getInstance("SHA-1")
                md.update(signatures[0].toByteArray())
                val digest = md.digest()
                digest.joinToString("") { "%02x".format(it) }
            } else {
                "No Signature"
            }
        } catch (e: Exception) {
            "Error computing SHA-1"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        // Ambient background gradient blobs
        Canvas(modifier = Modifier
            .fillMaxSize()
            .blur(80.dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.15f), Color.Transparent)
                ),
                radius = size.width * 0.8f,
                center = Offset(size.width * 0.8f, size.height * 0.2f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF34C759).copy(alpha = 0.1f), Color.Transparent)
                ),
                radius = size.width * 0.9f,
                center = Offset(size.width * 0.1f, size.height * 0.8f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "ISRAVEL Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "StockSnap",
                style = MaterialTheme.typography.displayLarge,
                color = TextPrimary
            )
            
            Text(
                text = "Retail Product Onboarding",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Sign in to continue",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (loading) {
                        CircularProgressIndicator(
                            color = PrimaryGreen,
                            modifier = Modifier.size(36.dp)
                        )
                    } else {
                        GlassButton(
                            onClick = {
                                val clientId = context.getString(R.string.default_web_client_id)
                                val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(clientId)
                                    .requestEmail()
                                    .build()
                                val signInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso)
                                
                                signInClient.signOut().addOnCompleteListener {
                                    launcher.launch(signInClient.signInIntent)
                                }
                            },
                            isPrimary = false
                        ) {
                            Canvas(modifier = Modifier.size(20.dp)) {
                                val w = size.width
                                val h = size.height
                                val strokeW = 3.dp.toPx()
                                drawArc(Color(0xFFFBBC05), 120f, 120f, false, style = Stroke(width = strokeW))
                                drawArc(Color(0xFFEA4335), 240f, 100f, false, style = Stroke(width = strokeW))
                                drawArc(Color(0xFF34A853), 45f, 75f, false, style = Stroke(width = strokeW))
                                drawArc(Color(0xFF4285F4), 340f, 65f, false, style = Stroke(width = strokeW))
                                drawLine(Color(0xFF4285F4), Offset(w * 0.5f, h * 0.5f), Offset(w, h * 0.5f), strokeWidth = strokeW)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Sign in with Google",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    error?.let { err ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = err,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Secure • Private • Built for ISRAVEL",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "App SHA-1: $sha1",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = TextSecondary.copy(alpha = 0.5f)
            )
        }
    }
}
