package eu.andreihaiu.task.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.andreihaiu.task.ui.main.MainActivity
import eu.andreihaiu.task.util.extensions.startAndFinish
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getNextActivityClass()
    }

    private fun getNextActivityClass() {
        Timber.d("Start Main")
        startAndFinish(MainActivity::class)
    }
}