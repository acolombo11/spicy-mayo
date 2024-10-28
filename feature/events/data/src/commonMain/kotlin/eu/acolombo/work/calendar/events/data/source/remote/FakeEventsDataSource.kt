package eu.acolombo.work.calendar.events.data.source.remote

import eu.acolombo.work.calendar.events.data.model.Event
import eu.acolombo.work.calendar.events.data.source.EventsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

internal class FakeEventsDataSource(
    private val dispatcher: CoroutineDispatcher,
) : EventsDataSource {
    private val events = generateRandomEvents()

    override suspend fun getEvents(date: LocalDate): List<Event> = withContext(dispatcher) {
        delay(2000)
        events[date.dayOfWeek]?.map { it.toEvent(date) }.orEmpty()
    }

    private fun generateRandomEvents(): Map<DayOfWeek, List<DayEvent>> {
        val summariesOffice = listOf(
            "Sprint Planning", "Code Review", "1:1 Meeting", "Planning Session",
            "Team Sync", "Design Review", "Project Update", "Performance Check",
            "Retrospective", "All Hands", "Customer Demo", "Coffee Break",
        )
        val summariesOutOfOffice = listOf(
            "Lunch \uD83C\uDF5D",
            "Picnic \uD83E\uDDFA",
            "Gym Break \uD83D\uDCAA",
            "Free \uD83C\uDF49 Palestine",
        )
        val attendeesList = listOf(
            "Connor", "Silvia", "Mario", "Mohammad", "Emma", "David",
            "Olivia", "Lucas", "Sophia", "Amir", "Ella", "Noah",
        )

        val events = mutableMapOf<DayOfWeek, List<DayEvent>>()

        // One day of the week is fully off
        val offDay = DayOfWeek.entries.random()

        // Generate events for each day of the week
        DayOfWeek.entries.forEach { dayOfWeek ->
            // Skip weekends
            events[dayOfWeek] = when (dayOfWeek) {
                DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> emptyList()
                else -> {
                    val dayEvents = mutableListOf<DayEvent>()

                    // Add a "Daily Standup" event from 9:30 to 10:00
                    dayEvents.add(
                        DayEvent(
                            summary = "Daily Standup",
                            day = dayOfWeek,
                            start = LocalTime(hour = 9, minute = 30),
                            duration = 30.minutes,
                            attendees = attendeesList,
                            type = "default",
                        ),
                    )

                    // Generate default events
                    val defaultEventCount = Random.nextInt(2, 4)
                    repeat(defaultEventCount) {
                        dayEvents.add(
                            createRandomEvent(
                                type = "default",
                                summary = summariesOffice.random(),
                                day = dayOfWeek,
                                attendees = attendeesList,
                            ),
                        )
                    }

                    // Add a daily working location event
                    dayEvents.add(
                        DayEvent(
                            summary = "Working Location: Home",
                            day = dayOfWeek,
                            start = LocalTime(0, 0, 0),
                            duration = 24.hours,
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
                                day = dayOfWeek,
                            ),
                        )
                    }

                    // Add out-of-office events
                    dayEvents.add(
                        createOutOfOfficeEvent(
                            day = dayOfWeek,
                            summary = summariesOutOfOffice.random(),
                            isFullDay = dayOfWeek == offDay,
                        ),
                    )

                    dayEvents.sortedBy { it.start }
                }
            }
        }
        return events
    }

    private val minutes = listOf(0, 15, 30, 45)

    private fun createRandomEvent(
        type: String,
        summary: String,
        day: DayOfWeek,
        attendees: List<String> = emptyList(),
    ) = DayEvent(
        summary = summary,
        day = day,
        start = LocalTime(hour = Random.nextInt(9, 17), minute = minutes.random()),
        duration = Random.nextInt(1, 3).hours,
        attendees = attendees.takeIf { it.isNotEmpty() }?.shuffled()?.takeRandom().orEmpty(),
        type = type,
    )

    private fun createOutOfOfficeEvent(
        day: DayOfWeek,
        summary: String,
        isFullDay: Boolean,
    ): DayEvent = if (isFullDay) {
        DayEvent(
            summary = "Out of Office",
            day = day,
            start = LocalTime(0, 0, 0),
            duration = 24.hours,
            attendees = emptyList(),
            type = "outOfOffice",
        )
    } else {
        DayEvent(
            summary = summary,
            day = day,
            start = LocalTime(hour = Random.nextInt(8, 17), minute = 0),
            duration = Random.nextInt(1, 3).hours,
            attendees = emptyList(),
            type = "outOfOffice",
        )
    }

    private fun <T> Iterable<T>.takeRandom(
        from: Int = count(),
        until: Int = count(),
    ): List<T> = take(Random.nextInt(minOf(count(), from), minOf(count(), until)))
}

private data class DayEvent(
    val summary: String?,
    val day: DayOfWeek,
    val start: LocalTime,
    val duration: Duration,
    val attendees: List<String> = emptyList(),
    val type: String,
)

private fun DayEvent.toEvent(
    date: LocalDate,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Event {
    val start: Instant = date.atTime(start).toInstant(timeZone)
    val end: Instant = start.plus(duration)
    return Event(
        summary = summary,
        start = start,
        end = end,
        attendees = attendees,
        type = type,
    )
}
