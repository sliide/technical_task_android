package com.android_test_maverick.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android_test_maverick.remote.source.RemoteDataSource
import com.android_test_maverick.local.source.LocalDataSource
import com.sachinsapkale.android_test_maverick.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        RemoteDataSource.self?.let { it.destroyInstance() }
        LocalDataSource.INSTANCE = null
    }
}