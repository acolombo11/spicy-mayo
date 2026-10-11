package eu.acolombo.work.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme
import eu.acolombo.work.calendar.di.koinModules
import eu.acolombo.work.calendar.events.presentation.EventsRoute
import eu.acolombo.work.calendar.events.presentation.EventsRouteDestination
import eu.acolombo.work.calendar.events.presentation.EventsViewModel
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Suppress("PreviewPublic")
@Composable
@Preview
fun App() {
    KoinApplication(
        KoinConfiguration { modules(koinModules) },
    ) {
        WorkCalendarTheme {
            val backStack = remember { mutableStateListOf<Any>(EventsRouteDestination) }
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<EventsRouteDestination> {
                        EventsRoute(
                            viewModel = koinViewModel<EventsViewModel>(),
                        )
                    }
                },
            )
        }
    }
}
