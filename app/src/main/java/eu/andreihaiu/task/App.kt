package eu.andreihaiu.task

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import eu.andreihaiu.task.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*

class App : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        // Init Dependency Injection
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }

        // Init Logging Library
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}