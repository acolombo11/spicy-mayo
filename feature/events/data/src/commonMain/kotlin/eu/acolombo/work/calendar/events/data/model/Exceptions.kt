package eu.acolombo.work.calendar.events.data.model

class ServerErrorException(val error: Error, message: String?) : Throwable(message)
class DeploymentErrorException(message: String?) : Throwable(message)
class NoConnectionException(message: String?) : Throwable(message)