package eu.acolombo.work.calendar.events.presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingIndicator(
    circlesNumber: Int = 3,
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1500,
    size: Dp = 200.dp,
) {
    val circles = List(circlesNumber) { remember { Animatable(initialValue = 0f) } }

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / circlesNumber.toLong()) * (index + 1))

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .size(size = size)
            .background(color = Color.Transparent),
    ) {
        circles.forEach {
            Box(
                modifier = Modifier
                    .scale(scale = it.value)
                    .size(size = size)
                    .clip(shape = CircleShape)
                    .background(color = circleColor.copy(alpha = (1 - it.value))),
            )
        }
    }
}

