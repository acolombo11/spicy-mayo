package eu.acolombo.work.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme
import eu.acolombo.work.calendar.events.presentation.EventsRoute
import eu.acolombo.work.calendar.events.presentation.EventsRouteDestination
import eu.acolombo.work.calendar.events.presentation.EventsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

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