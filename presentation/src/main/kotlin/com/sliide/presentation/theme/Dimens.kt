package com.sliide.presentation.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

open class Dimens private constructor() {
    val small = 4.dp
    val medium = 8.dp
    val normal = 12.dp
    val default = 16.dp
    val large = 24.dp

    val elevation = 8.dp

    val fabOffset = 144.dp
    val emailTextSize = 14.sp

    companion object : Dimens()
}