import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview
import sergio.sastre.composable.preview.scanner.jvm.JvmAnnotationInfo
import sergio.sastre.composable.preview.scanner.jvm.JvmAnnotationScanner

/**
 * Renders every `@Preview` under the feature's package and records/verifies it as a screenshot.
 *
 * This single file is added to the `jvmTest` source set of every presentation module by the root build script,
 * which also passes the package to scan as the `screenshot.package` system property.
 */
@Suppress("DEPRECATION") // The scanner's :jvm module is the only one that supports desktop previews.
@RunWith(Parameterized::class)
class PreviewScreenshotTest(private val preview: ComposablePreview<JvmAnnotationInfo>) {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screenshot() = runDesktopComposeUiTest(width = WIDTH, height = HEIGHT) {
        setContent { preview() }
        val name = "${preview.declaringClass.substringAfterLast('.')}.${preview.methodName}"
        onRoot().captureRoboImage("src/jvmTest/screenshots/$name.png")
    }

    companion object {
        private const val WIDTH = 411
        private const val HEIGHT = 891

        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun previews(): List<ComposablePreview<JvmAnnotationInfo>> =
            JvmAnnotationScanner("androidx.compose.ui.tooling.preview.Preview")
                .scanPackageTrees(System.getProperty("screenshot.package"))
                .includePrivatePreviews()
                .getPreviews()
    }
}
