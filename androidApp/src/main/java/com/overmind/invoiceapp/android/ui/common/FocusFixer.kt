package com.overmind.invoiceapp.android.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class FocusFixerData(
    val parentSize: IntSize,
    val focusedItem: FocusFixerFieldInfo?
)

@Composable fun rememberFocusFixerData(): MutableState<FocusFixerData> = remember {
    mutableStateOf(FocusFixerData(IntSize.Zero, null))
}

fun Modifier.focusFixerParent(
    data: MutableState<FocusFixerData>,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState
) =
    this.then(
        onSizeChanged { size ->
            data.value = data.value.copy(parentSize = size)
            recalculateScroll(data.value, coroutineScope, scrollState)
        }
    )

fun Modifier.focusFixer(
    focusFixerData: MutableState<FocusFixerData>,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState
): Modifier = composed {
    val itemInfo = remember { mutableStateOf(FocusFixerFieldInfo(0.0f, 0.0f)) }
    // This gap is a workaround to fix a problem with compose focus movement.
    // FocusManager.moveFocus() will not move the focus to a composable that is not visible
    val focusGapFix = with(LocalDensity.current) { (24.dp).toPx() + 1 }

    fillMaxWidth()
        .onFocusChanged {
            if (it.isFocused) {
                focusFixerData.value = focusFixerData.value.copy(focusedItem = itemInfo.value)
                recalculateScroll(focusFixerData.value, coroutineScope, scrollState)
            }
        }
        .onGloballyPositioned { coordinates ->
            itemInfo.value =
                FocusFixerFieldInfo(
                    y = coordinates.positionInParent().y,
                    height = coordinates.size.height.toFloat() + focusGapFix
                )
        }
}

data class FocusFixerFieldInfo(val y: Float, val height: Float) {
    val bottom: Float
        get() = y + height
}

private fun recalculateScroll(
    data: FocusFixerData,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState
) {

    if (data.focusedItem != null) {
        if ((data.focusedItem.bottom - scrollState.value) > data.parentSize.height) {
            coroutineScope.launch {
                scrollState.scrollBy((data.focusedItem.bottom - scrollState.value) - data.parentSize.height)
            }
        } else if ((data.focusedItem.y - scrollState.value) < 0) {
            coroutineScope.launch { scrollState.scrollBy(data.focusedItem.y - scrollState.value) }
        }
    }
}
