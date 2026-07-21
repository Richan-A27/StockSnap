package com.stocksnap.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stocksnap.ui.theme.GlassBackground
import com.stocksnap.ui.theme.GlassBorder
import com.stocksnap.ui.theme.PrimaryGreen

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    text: String? = null,
    containerColor: Color = if (isPrimary) PrimaryGreen.copy(alpha = 0.85f) else GlassBackground,
    contentColor: Color = if (isPrimary) Color.White else Color.Black,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = "button_scale"
    )

    val shadowElevation = if (isPressed) 8.dp else 20.dp

    Box(
        modifier = modifier
            .scale(scale)
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black.copy(alpha = if (isPrimary) 0.15f else 0.08f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(containerColor)
            .border(
                width = 1.dp,
                color = if (isPrimary) Color.White.copy(alpha = 0.2f) else GlassBorder,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (text != null) {
                    Text(text = text, color = contentColor, style = androidx.compose.material3.MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                }
                if (content != null) {
                    content()
                }
            }
        }
    }
}
