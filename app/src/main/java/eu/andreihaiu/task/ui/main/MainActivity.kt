package eu.andreihaiu.task.ui.main

import android.os.Bundle
import eu.andreihaiu.task.R
import eu.andreihaiu.task.databinding.ActivityMainBinding
import eu.andreihaiu.task.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
}