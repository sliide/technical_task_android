package com.sliide.presentation.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.sliide.presentation.theme.Dimens
import kotlin.math.roundToInt

@Composable
internal fun ScaffoldHideFabByScroll(
    fab: @Composable (Modifier) -> Unit,
    content: @Composable () -> Unit
) {
    val maxOffsetPx = with(LocalDensity.current) { Dimens.fabOffset.roundToPx().toFloat() }
    val fabOffsetPx = remember { mutableStateOf(0f) }
    val fabOffset = IntOffset(0, -fabOffsetPx.value.roundToInt())

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = fabOffsetPx.value + delta
                fabOffsetPx.value = newOffset.coerceIn(-maxOffsetPx, 0f)

                return Offset.Zero
            }
        }
    }

    Scaffold(
        Modifier.nestedScroll(nestedScrollConnection),
        floatingActionButton = { fab(Modifier.offset { fabOffset }) }
    ) {
        content()
    }
}