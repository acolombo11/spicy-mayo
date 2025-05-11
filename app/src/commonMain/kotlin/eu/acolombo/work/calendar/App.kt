package eu.acolombo.work.calendar

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme
import eu.acolombo.work.calendar.di.koinModules
import eu.acolombo.work.calendar.events.presentation.EventsRoute
import eu.acolombo.work.calendar.events.presentation.EventsRouteDestination
import eu.acolombo.work.calendar.events.presentation.EventsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Suppress("PreviewPublic")
@Composable
@Preview
fun App() {
    KoinMultiplatformApplication(
        KoinConfiguration { modules(koinModules) },
    ) {
        WorkCalendarTheme {
            NavHost(
                navController = rememberNavController(),
                startDestination = EventsRouteDestination,
            ) {
                composable<EventsRouteDestination> {
                    EventsRoute(
                        viewModel = koinViewModel<EventsViewModel>(),
                    )
                }
            }
        }
    }
}
