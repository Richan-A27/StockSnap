package com.stocksnap.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stocksnap.ui.theme.GlassBackground
import com.stocksnap.ui.theme.GlassBorder
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary

@Composable
fun GlassSegmentedControl(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) PrimaryGreen.copy(alpha = 0.15f) else Color.Transparent,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "segment_bg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) PrimaryGreen else TextSecondary,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "segment_text"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .clickable { onItemSelected(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    color = textColor,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}
