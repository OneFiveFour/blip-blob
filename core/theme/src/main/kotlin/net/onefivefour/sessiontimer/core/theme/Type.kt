package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val sairaFontFamily = FontFamily(
    Font(R.font.saira_condensed_regular, FontWeight.Normal),
    Font(R.font.saira_condensed_bold, FontWeight.Bold)
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),
    displayMedium = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    titleSmall = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    labelLarge = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    labelMedium = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.7.sp
    ),
    labelSmall = TextStyle(
        fontFamily = sairaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.7.sp
    )
)
