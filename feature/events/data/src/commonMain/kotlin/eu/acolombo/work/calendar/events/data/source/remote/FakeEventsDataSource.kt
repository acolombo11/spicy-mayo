package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.model.extensions.now
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.random.Random
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

internal class FakeEventsDataSource(
    private val dispatcher: CoroutineDispatcher,
) : EventsDataSource {
    val events = generateRandomEvents()

    override suspend fun getEvents(date: LocalDate): List<Event> = withContext(dispatcher) {
        delay(2000)
        events[date].orEmpty()
    }

    /**
     * I created this with the help of ChatGPT, I needed to fix small syntax details but otherwise
     * the logic was correct (I'm sure if I had also instructed GPT on the syntax mistakes it made,
     * I wouldn't have had to fix them manually). Here are the prompts I wrote (initial and additions):
     *
     * I need you to generate random events in the next 7 days. The function should be a list of the events you generate, but should take as input today's date.
     * This is the Event class:
     * data class Event(
     *     val summary: String?,
     *     val start: Instant?,
     *     val end: Instant?,
     *     val attendees: List<String> = emptyList(),
     *     val type: String,
     * )
     * The attendees are selected randomly in a list 12 names(examples Connor, Silvia, Mario, Mohammad, etc.), the summary is also selected randomly in a list of 14 names(examples are Daily Standup, Sprint Planning, Lunch with kids, etc.). Type of events are always the same:
     * "default", "outOfOffice", "workingLocation" "focusTime".
     * Most events should be of type default, but every day there should be a workingLocation event, rarely a focusTime event and almost every day an outOfOffice event of 1 hr. 1 day of the week should contain a outOfOffice event during all day.
     *   +
     * Each event shouldn't have all the attendees, but just a random selection of 2 to 12
     *   +
     * Don't add events on saturdays and sundays
     *   +
     * Add an event every day with summary "Daily Standup" from 9:30 to 10, with all attendees, and exclude it from general events
     *   +
     * The startDate parameter of the function should have a default value of the current day LocalData, also please use trailing commas everywhere
     *   +
     * Make it a map where the key is the date and the value is the list of events for that day
     */
    private fun generateRandomEvents(
        startDate: LocalDate = LocalDate.now(),
    ): Map<LocalDate, List<Event>> {
        val summariesOffice = listOf(
            "Sprint Planning", "Code Review", "1:1 Meeting",
            "Team Sync", "Design Review", "Project Update", "Performance Check",
            "Retrospective", "All Hands", "Customer Demo", "Coffee Break", "Planning Session",
        )
        val attendeesList = listOf(
            "Connor", "Silvia", "Mario", "Mohammad", "Emma", "David",
            "Olivia", "Lucas", "Sophia", "Amir", "Ella", "Noah",
        )
        val summariesOutOfOffice = listOf(
            "Lunch with kids \uD83C\uDF5D",
            "Picnic with friends \uD83E\uDDFA",
            "Gym Break \uD83D\uDCAA",
            "Free Palestine \uD83C\uDF49",
        )

        val timeZone = TimeZone.currentSystemDefault()

        val events = mutableMapOf<LocalDate, List<Event>>()

        // Generate events for the next 7 days so there's always a weekend included
        for (i in 0 until 7) {
            val date = startDate.plus(DatePeriod(days = i))

            // Skip weekends
            if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
                continue
            }
            val dayEvents = mutableListOf<Event>()

            // Add a "Daily Standup" event from 9:30 to 10:00
            dayEvents.add(
                createFixedEvent(
                    summary = "Daily Standup",
                    date = date,
                    startTime = LocalTime(hour = 9, minute = 30),
                    durationMinutes = 30,
                    attendees = attendeesList,
                ),
            )

            // Generate default events
            val defaultEventCount = Random.nextInt(2, 4)
            repeat(defaultEventCount) {
                dayEvents.add(
                    createRandomEvent(
                        type = "default",
                        summary = summariesOffice.random(),
                        date = date,
                        attendees = attendeesList,
                    ),
                )
            }

            // Add a daily working location event
            dayEvents.add(
                Event(
                    summary = "Working Location: Home",
                    start = date.atStartOfDayIn(timeZone),
                    end = date.plus(DatePeriod(days = 1)).atStartOfDayIn(timeZone),
                    attendees = emptyList(),
                    type = "workingLocation",
                ),
            )

            // Add a rare focus time event
            if (Random.nextInt(10) > 7) { // ~20% chance
                dayEvents.add(
                    createRandomEvent(
                        type = "focusTime",
                        summary = "Deep Focus",
                        date = date,
                    ),
                )
            }

            // Add an out-of-office event that happens almost every day
            if (Random.nextInt(10) > 2) { // ~70% chance
                dayEvents.add(
                    createRandomEvent(
                        type = "outOfOffice",
                        summary = summariesOutOfOffice.random(),
                        date = date,
                    ),
                )
            }

            // Add an out-of-office event 1 full day of the week
            val isFullDayOutOfOffice = (Random.nextInt(7) == i) // Once a week
            dayEvents.add(createOutOfOfficeEvent(date, isFullDayOutOfOffice, timeZone))

            events[date] = dayEvents
        }
        return events
    }

    private fun createRandomEvent(
        type: String,
        summary: String,
        date: LocalDate,
        attendees: List<String> = emptyList(),
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Event {
        val startHour = Random.nextInt(9, 16)
        val duration = Random.nextInt(1, 3) // Between 1 and 2 hours
        val start = date.atTime(startHour, 0).toInstant(timeZone)
        val end = start.plus(duration.hours)
        return Event(
            summary = summary,
            start = start,
            end = end,
            attendees = attendees,
            type = type,
        )
    }

    private fun createFixedEvent(
        summary: String,
        date: LocalDate,
        startTime: LocalTime,
        durationMinutes: Int,
        attendees: List<String>,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Event {
        val start = date.atTime(startTime).toInstant(timeZone)
        val end = start.plus(durationMinutes.minutes)
        return Event(
            summary = summary,
            start = start,
            end = end,
            attendees = attendees,
            type = "default",
        )
    }

    private fun createOutOfOfficeEvent(
        date: LocalDate,
        isFullDay: Boolean,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Event = if (isFullDay) {
        Event(
            summary = "Out of Office",
            start = date.atStartOfDayIn(timeZone),
            end = date.plus(DatePeriod(days = 1)).atStartOfDayIn(timeZone),
            attendees = emptyList(),
            type = "outOfOffice",
        )
    } else {
        val startHour = Random.nextInt(8, 17)
        val start = date.atTime(hour = startHour, minute = 0)
        val end = date.atTime(hour = startHour + Random.nextInt(1, 3), minute = 0)
        Event(
            summary = "Out of Office",
            start = start.toInstant(timeZone),
            end = end.toInstant(timeZone),
            attendees = emptyList(),
            type = "outOfOffice",
        )
    }
}
