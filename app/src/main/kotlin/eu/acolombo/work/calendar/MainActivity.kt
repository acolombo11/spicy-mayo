package eu.acolombo.work.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.acolombo.work.calendar.events.screen.EventsRoute
import eu.acolombo.work.calendar.design.theme.WorkCalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkCalendarTheme {
                EventsRoute()
            }
        }
    }
}
