package hashem.mousavi.animatedbottombar.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import hashem.mousavi.animatedbottombar.model.Tab

@Composable
fun MainScreen() {

    var selectedTab by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = selectedTab, fontSize = 20.sp, color = Color.White)
        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onTabSelected = {
                selectedTab = it
            }
        )
    }

}

@Composable
fun BottomBar(
    modifier: Modifier,
    onTabSelected: (String) -> Unit,
) {

    fun offset(tab: Tab): Float {
        val totalCount = tab.count
        val index = tab.ordinal
        val progress = index / totalCount.toFloat()
        return if (progress < 0.5f) -index * 40f - 20f else -(totalCount - index - 1) * 40f - 20f
    }

    var selectedItemIndex by remember {
        mutableStateOf(0)
    }

    var previousItemIndex by remember {
        mutableStateOf(0)
    }

    var width by remember {
        mutableStateOf(0)
    }

    var height by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = Unit){
        selectedItemIndex = 2
        onTabSelected(Tab.Home.name)
    }

    Box(
        modifier = modifier
            .onSizeChanged {
                width = it.width
                height = it.height
            }
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Gray,
                        Color.DarkGray,
                        Color.Black
                    )
                ),
                shape = bottomBarShape()
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.8f),
                shape = bottomBarBorderShape()
            )
            .padding(bottom = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Tab.values().forEach { tab ->
                Icon(
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = offset(tab).toInt()) }
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {
                                previousItemIndex = selectedItemIndex
                                selectedItemIndex = tab.ordinal
                                onTabSelected(tab.name)
                            }
                        )
                        .requiredSize(30.dp),
                    imageVector = tab.icon,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }


        //Indicator
        Spacer(
            modifier = Modifier
                .offset(y = 60.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .clip(shape = bottomBarIndicatorClipShape(selectedItemIndex, previousItemIndex))
                .background(
                    color = Color.White,
                    shape = bottomBarIndicatorShape()
                )


        )

        Spacer(
            modifier = Modifier
                .offset(y = 64.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.DarkGray,
                            Color.LightGray
                        ),
                        startY = 0f,
                        endY = height.toFloat()
                    ),
                    shape = bottomBarShape()
                )
        )
    }

}

fun bottomBarShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Outline {
            val path = Path().apply {
                val width = size.width
                val midWidth = width / 2
                val height = size.height
                val yControl1 = with(density) { -40.dp.toPx() }
                val yControl2 = with(density) { -5.dp.toPx() }

                moveTo(x = 0f, y = yControl2)
                cubicTo(
                    x1 = midWidth / 2,
                    y1 = yControl1,
                    x2 = midWidth,
                    y2 = yControl1,
                    x3 = midWidth,
                    y3 = yControl1
                )
                cubicTo(
                    x1 = 3 * midWidth / 2,
                    y1 = yControl1,
                    x2 = width,
                    y2 = yControl2,
                    x3 = width,
                    y3 = yControl2
                )

                lineTo(x = width, y = height)
                lineTo(x = 0f, y = height)
                close()
            }
            return Outline.Generic(path)
        }

    }
}

fun bottomBarBorderShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Outline {
            val path = Path().apply {
                val width = size.width
                val midWidth = width / 2
                val yControl1 = with(density) { -40.dp.toPx() }
                val yControl2 = with(density) { -5.dp.toPx() }

                moveTo(x = 0f, y = yControl2)
                cubicTo(
                    x1 = midWidth / 2,
                    y1 = yControl1,
                    x2 = midWidth,
                    y2 = yControl1,
                    x3 = midWidth,
                    y3 = yControl1
                )
                cubicTo(
                    x1 = 3 * midWidth / 2,
                    y1 = yControl1,
                    x2 = width,
                    y2 = yControl2,
                    x3 = width,
                    y3 = yControl2
                )
            }
            return Outline.Generic(path)
        }

    }
}

fun bottomBarIndicatorShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Outline {
            val path = Path().apply {
                val width = size.width
                val height = size.height
                val midWidth = width / 2
                val yControl1 = with(density) { -40.dp.toPx() }
                val yControl2 = with(density) { -5.dp.toPx() }

                moveTo(x = 0f, y = yControl2)
                cubicTo(
                    x1 = midWidth / 2,
                    y1 = yControl1,
                    x2 = midWidth,
                    y2 = yControl1,
                    x3 = midWidth,
                    y3 = yControl1
                )
                cubicTo(
                    x1 = 3 * midWidth / 2,
                    y1 = yControl1,
                    x2 = width,
                    y2 = yControl2,
                    x3 = width,
                    y3 = yControl2
                )
                lineTo(x = width, y = height)
                lineTo(x = 0f, y = height)
                close()
            }
            return Outline.Generic(path)
        }

    }
}

@Composable
fun bottomBarIndicatorClipShape(
    index: Int,
    previousItemIndex: Int,
): Shape {

    val animate = remember(index) {
        Animatable(0f)
    }

    LaunchedEffect(key1 = index) {
        animate.animateTo(
            1f,
            animationSpec = tween(
                400,
                easing = Easing {
                    OvershootInterpolator().getInterpolation(it)
                }
            )
        )
    }


    return ClipShape(index, previousItemIndex, animate.value)
}

class ClipShape(
    private val index: Int,
    private val previousItemIndex: Int,
    private val animate: Float,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        val path = Path().apply {
            val width = size.width
            val height = size.height
            val padding = 20f


            val left = padding
            val right = left + width / 5 - padding

            addRect(
                rect = Rect(
                    left = left,
                    top = -1000f,
                    right = right,
                    bottom = height
                )
            )

            val offsetX = width / 5f * (index - previousItemIndex)
            val previousOffsetX = width / 5f * previousItemIndex

            translate(Offset(x = previousOffsetX + offsetX * animate, y = 0f))
        }
        return Outline.Generic(path)
    }

}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}