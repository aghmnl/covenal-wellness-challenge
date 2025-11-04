package com.agus.wellnessapp.ui.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.ui.theme.AppPalettes

private const val TAG = "SessionListItem"

@Composable
fun SessionListItem(
    pose: Pose,
    isFavorite: Boolean,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    backgroundColor: Color,
    titleColor: Color,
    bodyColor: Color,
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "Composing item for pose: ${pose.id} - ${pose.englishName}, isFavorite=$isFavorite")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                Log.d(TAG, "Item ${pose.id} clicked.")
                onItemClick(pose.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pose.imageUrl)
                    .crossfade(true)
                    .error(android.R.drawable.ic_menu_gallery)
                    .build(),
                contentDescription = pose.englishName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pose.englishName ?: "Unknown Pose",
                    style = MaterialTheme.typography.titleLarge,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = pose.sanskritName ?: "Unknown",
                    style = MaterialTheme.typography.bodyMedium,
                    color = bodyColor
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) AppPalettes.darkVibrant.background else bodyColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SessionListItemPreview() {
    val samplePose = Pose(
        id = 1,
        englishName = "Boat Pose",
        sanskritName = "Navasana",
        description = "A great pose for core strength.",
        benefits = "Strengthens abs.",
        imageUrl = ""
    )
    SessionListItem(
        pose = samplePose,
        isFavorite = true,
        onItemClick = {},
        onFavoriteClick = {},
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        titleColor = MaterialTheme.colorScheme.onSurface,
        bodyColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}