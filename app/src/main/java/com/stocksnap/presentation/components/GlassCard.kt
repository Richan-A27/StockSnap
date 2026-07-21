package com.stocksnap.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.stocksnap.ui.theme.GlassBackground
import com.stocksnap.ui.theme.GlassBorder

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            // Ambient soft shadow
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = androidx.compose.ui.graphics.Color.Black,
                spotColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(28.dp)
            ),
        content = content
    )
}
