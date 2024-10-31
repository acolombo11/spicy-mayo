package eu.acolombo.work.calendar.network

import eu.acolombo.work.calendar.network.data.Error

class ServerErrorException(val error: Error, message: String?) : Throwable(message)
class DeploymentErrorException(message: String?) : Throwable(message)
class NoConnectionException(message: String?) : Throwable(message)