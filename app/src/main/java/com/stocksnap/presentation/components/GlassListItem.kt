package com.stocksnap.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.stocksnap.ui.theme.GlassBackground
import com.stocksnap.ui.theme.GlassBorder

@Composable
fun GlassListItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var baseModifier = modifier
        .fillMaxWidth()
        .shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            ambientColor = androidx.compose.ui.graphics.Color.Black,
            spotColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.06f)
        )
        .clip(RoundedCornerShape(20.dp))
        .background(GlassBackground)
        .border(
            width = 1.dp,
            color = GlassBorder,
            shape = RoundedCornerShape(20.dp)
        )
        
    if (onClick != null) {
        baseModifier = baseModifier.clickable(onClick = onClick)
    }

    Box(
        modifier = baseModifier.padding(12.dp),
        content = content
    )
}
