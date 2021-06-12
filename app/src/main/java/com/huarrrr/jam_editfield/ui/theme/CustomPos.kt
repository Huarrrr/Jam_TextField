package com.huarrrr.jam_editfield.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

/**
 * Box：底部和四角的布局位置
 */
enum class CompostablePosition {
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight,
    CenterBottom
}

fun Modifier.compostablePosition(pos: CompostablePosition) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
        when (pos) {
            CompostablePosition.TopLeft -> {
                placeable.placeRelative(0, 0)
            }
            CompostablePosition.TopRight -> {
                placeable.placeRelative(constraints.maxWidth - placeable.width, 0)
            }
            CompostablePosition.BottomLeft -> {
                placeable.placeRelative(0, constraints.maxHeight - placeable.height)
            }
            CompostablePosition.BottomRight -> {
                placeable.placeRelative(
                    constraints.maxWidth - placeable.width,
                    constraints.maxHeight - placeable.height
                )
            }
            CompostablePosition.CenterBottom -> {
                placeable.placeRelative(
                    (constraints.maxWidth - placeable.width) / 2,
                    constraints.maxHeight - placeable.height
                )

            }

        }
    }
}