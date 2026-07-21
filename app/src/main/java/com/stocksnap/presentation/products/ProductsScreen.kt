package com.stocksnap.presentation.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.presentation.components.GlassListItem
import com.stocksnap.presentation.components.GlassSearchBar
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary

@Composable
fun ProductsScreen(
    onProductClick: (Long) -> Unit = {},
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current

    // Reload products whenever the screen is composed
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        // Ambient glass background blurs
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize().blur(80.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.12f), Color.Transparent)
                ),
                radius = size.width,
                center = Offset(size.width * 0.5f, 0f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF34C759).copy(alpha = 0.08f), Color.Transparent)
                ),
                radius = size.width * 0.8f,
                center = Offset(0f, size.height * 0.8f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Product Catalog",
                style = MaterialTheme.typography.displayMedium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            GlassSearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) },
                onSearch = { },
                placeholder = "Search by name, code or barcode...",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (products.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No products found in catalog.", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 100.dp), // Padding for Nav bar
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products) { product ->
                        GlassListItem(
                            onClick = { onProductClick(product.id) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(product.frontImagePath.ifEmpty { product.frontImageUrl ?: "" })
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.White.copy(alpha = 0.5f)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (product.code.isNotEmpty()) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(PrimaryGreen.copy(alpha = 0.1f))
                                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                                                    .padding(end = 8.dp)
                                            ) {
                                                Text(
                                                    text = product.code,
                                                    color = PrimaryGreen,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Text(
                                            text = "Barcode: ${product.barcode}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSecondary,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
