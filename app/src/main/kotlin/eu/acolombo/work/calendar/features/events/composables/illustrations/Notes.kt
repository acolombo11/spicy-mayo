package eu.acolombo.work.calendar.features.events.composables.illustrations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Notes(
    fill: Color = Color(0xFF85b65f),
    stroke: Color = Color(0xFF101419),
) : Illustration(fill, stroke) {
    override val image: ImageVector = Builder(
        name = "Notes",
        defaultWidth = 416.701.dp,
        defaultHeight = 620.46.dp,
        viewportWidth = 416.701f,
        viewportHeight = 620.46f,
    ).apply {
        path(fill = strokeColor) {
            moveToRelative(246.954f, 179.223f)
            curveToRelative(-0.015f, 5.028f, -2.556f, 10.0f, -6.62f, 12.961f)
            curveToRelative(0.056f, -0.211f, -0.237f, -0.392f, -0.237f, -0.587f)
            curveToRelative(-0.015f, 0.335f, -0.028f, 0.669f, -0.056f, 1.005f)
            curveToRelative(-2.556f, 1.661f, -5.363f, 2.751f, -7.751f, 2.513f)
            curveToRelative(-4.819f, -0.503f, -9.247f, -3.756f, -11.16f, -8.197f)
            curveToRelative(-0.879f, -2.053f, -1.34f, -4.428f, -3.044f, -5.88f)
            curveToRelative(-2.36f, -1.997f, -6.089f, -1.201f, -8.533f, 0.699f)
            curveToRelative(-2.429f, 1.913f, -4.036f, 4.651f, -6.089f, 6.955f)
            curveToRelative(-2.052f, 2.319f, -4.651f, 3.631f, -7.695f, 3.128f)
            curveToRelative(-0.419f, -3.225f, -3.589f, -5.475f, -6.773f, -6.159f)
            curveToRelative(-5.517f, -1.215f, -11.648f, 1.299f, -14.735f, 6.019f)
            curveToRelative(-3.087f, 4.735f, -3.393f, 11.928f, -0.056f, 16.495f)
            curveToRelative(-2.597f, 3.952f, -5.907f, 7.457f, -9.72f, 10.251f)
            lineToRelative(0.071f, -0.112f)
            curveToRelative(-7.249f, -0.643f, -14.539f, -6.745f, -15.865f, -13.909f)
            curveToRelative(-1.313f, -7.165f, 2.625f, -14.972f, 9.175f, -18.156f)
            curveToRelative(-6.243f, -3.604f, -9.901f, -10.587f, -8.379f, -17.625f)
            curveToRelative(1.521f, -7.039f, 7.192f, -12.848f, 13.965f, -15.292f)
            curveToRelative(6.773f, -2.459f, 14.469f, -1.76f, 21.075f, 1.117f)
            curveToRelative(1.788f, -5.308f, 7.751f, -8.449f, 13.197f, -9.791f)
            curveToRelative(5.447f, -1.327f, 11.508f, 0.293f, 15.559f, 4.176f)
            curveToRelative(5.587f, -4.693f, 15.712f, -5.265f, 21.619f, -0.964f)
            curveToRelative(5.895f, 4.288f, 8.324f, 12.765f, 5.587f, 19.539f)
            curveToRelative(4.051f, 2.975f, 6.467f, 6.773f, 6.467f, 11.815f)
        }
        path(fill = fillColor) {
            moveToRelative(405.789f, 483.625f)
            lineToRelative(-82.304f, 55.519f)
            horizontalLineToRelative(-180.859f)
            lineToRelative(97.987f, -55.295f)
            curveToRelative(-32.987f, -1.467f, -66.971f, -0.676f, -99.256f, -7.612f)
            curveToRelative(-38.411f, -8.252f, -48.013f, -10.768f, -69.364f, -21.74f)
            curveToRelative(-17.309f, 1.601f, -32.099f, -0.144f, -40.44f, -2.668f)
            curveToRelative(-5.126f, -39.523f, -8.864f, -77.879f, 3.747f, -115.697f)
            curveToRelative(8.855f, -26.592f, 18.254f, -54.076f, 37.471f, -74.481f)
            curveToRelative(20.39f, -21.619f, 56.708f, -40.965f, 86.26f, -37.852f)
            lineToRelative(5.453f, -5.372f)
            curveToRelative(3.813f, -2.793f, 7.123f, -6.299f, 9.72f, -10.251f)
            curveToRelative(-3.337f, -4.567f, -3.031f, -11.76f, 0.056f, -16.495f)
            curveToRelative(3.087f, -4.72f, 9.217f, -7.233f, 14.735f, -6.019f)
            curveToRelative(3.184f, 0.684f, 6.355f, 2.933f, 6.773f, 6.159f)
            curveToRelative(3.044f, 0.503f, 5.643f, -0.809f, 7.695f, -3.128f)
            curveToRelative(2.053f, -2.304f, 3.66f, -5.041f, 6.089f, -6.955f)
            curveToRelative(2.444f, -1.9f, 6.173f, -2.696f, 8.533f, -0.699f)
            curveToRelative(1.704f, 1.452f, 2.165f, 3.827f, 3.044f, 5.88f)
            curveToRelative(1.913f, 4.441f, 6.341f, 7.695f, 11.16f, 8.197f)
            curveToRelative(2.388f, 0.237f, 5.195f, -0.852f, 7.751f, -2.513f)
            curveToRelative(-0.237f, 5.391f, -1.327f, 10.712f, -3.128f, 15.781f)
            curveToRelative(-0.685f, 1.927f, -1.467f, 3.827f, -2.361f, 5.669f)
            curveToRelative(-4.86f, 10.14f, -12.652f, 18.883f, -22.359f, 24.651f)
            curveToRelative(4.651f, 1.676f, 8.352f, 5.432f, 10.809f, 9.72f)
            curveToRelative(2.459f, 4.301f, 3.813f, 9.148f, 4.916f, 13.967f)
            curveToRelative(4.916f, 21.619f, 5.363f, 43.979f, 8.156f, 65.975f)
            curveToRelative(2.78f, 21.996f, 9.595f, 45.501f, 23.044f, 63.125f)
            curveToRelative(4.204f, -1.201f, 8.533f, -0.321f, 12.345f, 1.803f)
            verticalLineToRelative(-52.667f)
            curveToRelative(0.0f, -8.407f, 6.816f, -15.223f, 15.224f, -15.223f)
            curveToRelative(4.203f, 0.0f, 8.001f, 1.704f, 10.767f, 4.456f)
            curveToRelative(2.752f, 2.751f, 4.456f, 6.564f, 4.456f, 10.767f)
            verticalLineToRelative(87.204f)
            curveToRelative(4.092f, 0.544f, 9.968f, 3.307f, 11.141f, 7.273f)
            curveToRelative(1.172f, 3.952f, -4.067f, 8.757f, -7.195f, 11.425f)
            lineToRelative(1.207f, 1.072f)
            curveToRelative(2.403f, 3.701f, 5.949f, 6.471f, 5.293f, 10.828f)
            curveToRelative(-0.685f, 4.525f, -5.127f, 8.365f, -9.679f, 8.031f)
            curveToRelative(2.388f, 2.472f, 3.016f, 6.48f, 1.521f, 9.581f)
            curveToRelative(-1.508f, 3.1f, -4.468f, 6.06f, -7.919f, 6.06f)
            lineToRelative(109.508f, 1.523f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(48.962f, 316.851f)
            curveToRelative(-3.387f, 36.007f, -2.108f, 72.449f, 3.796f, 108.131f)
            curveToRelative(1.006f, 6.079f, 2.181f, 12.243f, 5.048f, 17.697f)
            curveToRelative(2.867f, 5.455f, 22.757f, 17.961f, 28.778f, 19.275f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(120.682f, 321.858f)
            curveToRelative(-2.524f, 19.137f, -2.673f, 38.588f, -0.441f, 57.761f)
            curveToRelative(0.537f, 4.62f, 1.345f, 9.521f, 4.456f, 12.98f)
            curveToRelative(3.56f, 3.959f, 9.233f, 5.067f, 14.493f, 5.897f)
            curveToRelative(35.244f, 5.569f, 70.865f, 8.755f, 106.54f, 9.527f)
            curveToRelative(7.852f, -6.908f, 13.289f, -11.576f, 23.497f, -13.852f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(266.54f, 426.282f)
            curveToRelative(13.787f, -0.437f, 28.717f, -0.141f, 36.557f, 1.679f)
            curveToRelative(4.193f, 0.972f, 8.892f, 2.973f, 9.955f, 7.144f)
            curveToRelative(0.847f, 3.324f, -1.119f, 6.855f, -3.887f, 8.88f)
            curveToRelative(-2.769f, 2.025f, -5.04f, 2.588f, -8.376f, 3.388f)
            curveToRelative(3.777f, -1.055f, 9.46f, 1.389f, 11.304f, 4.851f)
            curveToRelative(1.844f, 3.461f, 0.955f, 8.151f, -2.028f, 10.697f)
            curveToRelative(-2.48f, 2.117f, -5.897f, 2.667f, -9.125f, 3.133f)
            curveToRelative(3.207f, 2.028f, 5.624f, 5.989f, 4.12f, 9.472f)
            curveToRelative(-1.503f, 3.483f, -5.712f, 5.545f, -9.387f, 4.603f)
            curveToRelative(1.079f, 3.235f, -0.767f, 7.38f, -3.937f, 8.633f)
            curveToRelative(-3.172f, 1.255f, -6.725f, 0.836f, -10.107f, 0.397f)
            curveToRelative(-13.672f, -1.771f, -27.344f, -3.541f, -41.016f, -5.311f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(271.746f, 354.785f)
            horizontalLineToRelative(30.031f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(271.746f, 361.425f)
            horizontalLineToRelative(30.031f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(271.746f, 424.678f)
            verticalLineToRelative(-61.34f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(301.777f, 426.615f)
            verticalLineToRelative(-63.277f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(292.574f, 499.266f)
            curveToRelative(-5.256f, 8.337f, -11.06f, 16.02f, -16.316f, 24.357f)
            curveToRelative(-5.169f, -8.088f, -10.337f, -16.177f, -15.505f, -24.265f)
            curveToRelative(10.524f, 2.496f, 21.309f, 2.457f, 31.821f, -0.092f)
            close()
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(260.753f, 486.263f)
            verticalLineToRelative(13.003f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(292.574f, 488.762f)
            verticalLineToRelative(10.504f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(268.977f, 512.247f)
            curveToRelative(4.496f, 1.209f, 9.291f, 1.285f, 13.824f, 0.221f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(209.193f, 311.107f)
            curveToRelative(-1.5f, 33.007f, 5.345f, 66.361f, 19.725f, 96.108f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(195.572f, 191.626f)
            curveToRelative(-2.792f, -5.157f, -9.704f, -7.488f, -15.184f, -5.397f)
            curveToRelative(-5.48f, 2.091f, -9.047f, 8.133f, -8.596f, 13.981f)
            curveToRelative(0.452f, 5.847f, 4.671f, 11.135f, 10.147f, 13.235f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(187.677f, 193.754f)
            curveToRelative(-2.4f, -0.596f, -5.111f, 0.351f, -6.619f, 2.309f)
            curveToRelative(-1.508f, 1.96f, -1.729f, 4.823f, -0.539f, 6.991f)
        }
        path(fill = strokeColor) {
            moveToRelative(212.417f, 210.474f)
            curveToRelative(-1.193f, 1.705f, -3.104f, 2.428f, -4.269f, 1.612f)
            curveToRelative(-1.165f, -0.815f, -1.143f, -2.859f, 0.051f, -4.564f)
            curveToRelative(1.193f, -1.705f, 3.105f, -2.427f, 4.269f, -1.612f)
            curveToRelative(1.165f, 0.816f, 1.143f, 2.859f, -0.051f, 4.564f)
        }
        path(fill = strokeColor) {
            moveToRelative(230.44f, 220.635f)
            curveToRelative(-1.193f, 1.705f, -2.888f, 2.58f, -3.784f, 1.953f)
            curveToRelative(-0.896f, -0.628f, -0.656f, -2.519f, 0.537f, -4.224f)
            curveToRelative(1.193f, -1.705f, 2.888f, -2.58f, 3.784f, -1.952f)
            curveToRelative(0.896f, 0.627f, 0.656f, 2.517f, -0.537f, 4.223f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(218.658f, 215.617f)
            curveToRelative(-0.501f, 2.192f, -1.001f, 4.385f, -1.503f, 6.579f)
            curveToRelative(-1.679f, 0.0f, -3.357f, -0.411f, -4.841f, -1.196f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(205.447f, 223.798f)
            curveToRelative(-0.091f, 1.844f, 0.593f, 3.715f, 1.855f, 5.063f)
            curveToRelative(1.26f, 1.349f, 3.079f, 2.159f, 4.925f, 2.192f)
        }
        path(fill = strokeColor) {
            moveToRelative(211.137f, 194.494f)
            curveToRelative(-0.692f, -0.051f, -1.407f, -0.045f, -2.052f, 0.209f)
            curveToRelative(-0.645f, 0.255f, -1.212f, 0.803f, -1.317f, 1.488f)
            curveToRelative(-0.175f, 1.125f, 0.863f, 2.04f, 1.819f, 2.659f)
            curveToRelative(1.411f, 0.911f, 2.891f, 1.712f, 4.424f, 2.395f)
            curveToRelative(0.928f, 0.412f, 1.945f, 0.788f, 2.937f, 0.569f)
            curveToRelative(0.861f, -0.189f, 1.603f, -0.852f, 1.888f, -1.687f)
            curveToRelative(1.343f, -3.927f, -5.052f, -5.44f, -7.699f, -5.633f)
        }
        path(fill = strokeColor) {
            moveToRelative(236.913f, 208.385f)
            curveToRelative(-0.685f, 1.927f, -1.467f, 3.827f, -2.361f, 5.669f)
            curveToRelative(-1.089f, -0.935f, -2.123f, -1.94f, -3.085f, -3.016f)
            curveToRelative(-0.671f, -0.755f, -1.341f, -1.607f, -1.439f, -2.625f)
            curveToRelative(-0.071f, -0.88f, 0.335f, -1.788f, 1.033f, -2.304f)
            curveToRelative(2.151f, -1.593f, 4.315f, 0.153f, 5.852f, 2.276f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(189.496f, 231.041f)
            curveToRelative(2.491f, 3.376f, 6.052f, 5.951f, 10.039f, 7.259f)
            curveToRelative(3.987f, 1.308f, 8.38f, 1.343f, 12.387f, 0.099f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(287.037f, 447.666f)
            curveToRelative(4.892f, 0.124f, 11.797f, -0.252f, 16.689f, -0.128f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(287.037f, 465.457f)
            curveToRelative(4.892f, 0.123f, 9.785f, 0.245f, 14.679f, 0.368f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(282.366f, 478.687f)
            curveToRelative(4.359f, 0.377f, 8.716f, 0.755f, 13.075f, 1.132f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(159.032f, 223.798f)
            curveToRelative(-1.305f, 10.019f, 5.535f, 18.94f, 14.477f, 23.643f)
            curveToRelative(6.607f, 3.473f, 14.429f, 4.575f, 21.739f, 3.057f)
            curveToRelative(7.308f, -1.517f, 11.995f, -5.113f, 16.673f, -10.929f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(44.815f, 314.483f)
            curveToRelative(27.358f, 5.768f, 55.472f, 7.944f, 83.393f, 6.451f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(233.236f, 303.527f)
            curveToRelative(-7.815f, 2.232f, -15.628f, 4.465f, -23.443f, 6.699f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(359.629f, 488.762f)
            lineToRelative(-57.913f, 37.669f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(334.845f, 488.762f)
            lineToRelative(-57.913f, 37.669f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(201.396f, 529.647f)
            lineToRelative(55.317f, -35.633f)
        }
        path(
            stroke = strokeColor,
            strokeLineWidth = strokeWidth,
            strokeLineCap = Round,
            strokeLineJoin = StrokeJoin.Companion.Round,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(240.613f, 530.292f)
            lineToRelative(24.14f, -17.56f)
        }
    }.build()
}

@Composable
@Preview
private fun Preview() {
    val notes = Notes().image
    Image(
        modifier = Modifier
            .background(Color(0xFFFFF6ED))
            .padding(18.dp)
            .background(Color(0xFFF7EBDF)),
        imageVector = notes,
        contentDescription = notes.name,
    )
}

