package eu.acolombo.work.calendar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
