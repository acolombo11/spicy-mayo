package eu.acolombo.work.calendar.events.data.source

import eu.acolombo.work.calendar.events.data.model.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate

class DefaultEventsRepository(
    private val remote: EventsDataSource,
    private val dispatcher: CoroutineDispatcher,
) : EventsRepository {
    override fun getEvents(date: LocalDate): Flow<List<Event>> = flow {
        emit(remote.getEvents(date))
    }.flowOn(dispatcher)
}
