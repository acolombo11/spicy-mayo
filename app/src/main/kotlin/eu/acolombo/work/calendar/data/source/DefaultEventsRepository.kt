package eu.acolombo.work.calendar.data.source

import eu.acolombo.work.calendar.data.model.Event
import eu.acolombo.work.calendar.data.source.remote.RetrofitEventsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate

class DefaultEventsRepository(
    private val remote: EventsDataSource = RetrofitEventsDataSource(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : EventsRepository {
    override fun getEvents(date: LocalDate): Flow<List<Event>> = flow {
        emit(remote.getEvents(date))
    }.flowOn(dispatcher)
}
