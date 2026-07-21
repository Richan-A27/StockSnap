package com.stocksnap.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.stocksnap.ui.theme.NavGlassBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.TextSecondary

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun GlassNavigationBar(
    items: List<NavItem>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .shadow(
                elevation = 30.dp,
                shape = RoundedCornerShape(30.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(30.dp))
            .background(NavGlassBackground)
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                
                val iconTint by animateColorAsState(
                    targetValue = if (isSelected) Color.White else TextSecondary,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "nav_icon_tint"
                )
                
                val pillWidth by animateDpAsState(
                    targetValue = if (isSelected) 64.dp else 0.dp,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "nav_pill_width"
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onNavigate(item.route) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Animated pill background
                    if (pillWidth > 0.dp) {
                        Box(
                            modifier = Modifier
                                .width(pillWidth)
                                .height(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(PrimaryGreen)
                        )
                    }
                    
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = iconTint
                    )
                }
            }
        }
    }
}
