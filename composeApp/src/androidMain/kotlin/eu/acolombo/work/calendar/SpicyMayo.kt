package eu.acolombo.work.calendar

import eu.acolombo.work.calendar.di.KoinInitializer
import android.app.Application

class SpicyMayo: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}