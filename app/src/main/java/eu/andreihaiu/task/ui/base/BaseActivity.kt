package eu.andreihaiu.task.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract fun getLayoutResource(): Int

    val viewDataBinding: T
        get() = _viewDataBinding

    private lateinit var _viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)
        _viewDataBinding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewDataBinding.lifecycleOwner = this
    }
}

