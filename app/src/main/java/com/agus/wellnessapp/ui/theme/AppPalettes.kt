package com.agus.wellnessapp.ui.theme

import androidx.compose.ui.graphics.Color

object AppPalettes {

    // 1. Vibrant
    val vibrant = CardColors(
        background = Color(0xFFB02038),
        title = Color(0x8FFFFFFF),
        body = Color(0xC4FFFFFF)
    )

    // 2. Light Vibrant (Your chosen theme)
    val lightVibrant = CardColors(
        background = Color(0xFFF8F0D0),
        title = Color(0x6C000000),
        body = Color(0x8B000000)
    )

    // 3. Dark Vibrant
    val darkVibrant = CardColors(
        background = Color(0xFF602808),
        title = Color(0x63FFFFFF),
        body = Color(0x8AFFFFFF)
    )

    // 4. Muted
    val muted = CardColors(
        background = Color(0xFF989080),
        title = Color(0x84000000),
        body = Color(0xB3000000)
    )

    // 5. Light Muted
    val lightMuted = CardColors(
        background = Color(0xFFB0A898),
        title = Color(0x79000000),
        body = Color(0xA0000000)
    )

    // 6. Dark Muted
    val darkMuted = CardColors(
        background = Color(0xFF383838),
        title = Color(0x5EFFFFFF),
        body = Color(0x87FFFFFF)
    )
}