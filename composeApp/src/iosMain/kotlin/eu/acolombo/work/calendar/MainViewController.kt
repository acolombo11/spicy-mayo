package eu.acolombo.work.calendar
import eu.acolombo.work.calendar.di.KoinInitializer
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = { KoinInitializer().init() },
) {
    App()
}