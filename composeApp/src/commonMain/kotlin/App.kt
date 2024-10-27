
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import feature.EventsRoute
import feature.EventsRouteDestination
import feature.EventsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import theme.WorkCalendarTheme

@Composable
@Preview
fun App() {
    WorkCalendarTheme {
        KoinContext {
            NavHost(
                navController = rememberNavController(),
                startDestination = EventsRouteDestination,
            ) {
                composable<EventsRouteDestination>{
                    EventsRoute(
                        viewModel = koinViewModel<EventsViewModel>(),
                    )
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel { scope.get<T>() }
}