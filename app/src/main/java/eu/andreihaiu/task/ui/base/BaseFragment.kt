package eu.andreihaiu.task.ui.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import eu.andreihaiu.task.util.extensions.ViewModelType
import eu.andreihaiu.task.util.extensions.getSharedGraphViewModel
import eu.andreihaiu.task.util.extensions.nav
import eu.andreihaiu.task.util.extensions.startActivityByName
import eu.andreihaiu.task.viewmodels.base.AppNavigation
import eu.andreihaiu.task.viewmodels.base.BaseAndroidViewModelImpl
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.reflect.KClass

abstract class BaseFragment<TViewModel : BaseAndroidViewModelImpl, TBinding : ViewDataBinding>(
    viewModelClass: KClass<TViewModel>,
    viewModelType: ViewModelType = ViewModelType.SINGLE,
    @IdRes graphId: Int = 0
) : Fragment() {

    protected abstract fun getLayoutResource(): Int
    protected abstract fun onBindViews(savedInstanceState: Bundle?)

    private lateinit var _viewDataBinding: TBinding
    val viewDataBinding: TBinding
        get() = _viewDataBinding

    protected val viewModel: TViewModel by lazy {
        return@lazy when (viewModelType) {
            ViewModelType.SINGLE -> getViewModel(clazz = viewModelClass)
            ViewModelType.SHARED -> getSharedViewModel(clazz = viewModelClass)
            ViewModelType.GRAPH_SHARED -> getSharedGraphViewModel(graphId, viewModelClass)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewDataBinding =
            DataBindingUtil.inflate(layoutInflater, getLayoutResource(), container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        onBindViews(savedInstanceState)
    }

    open fun onBackPressed() {
        findNavController().popBackStack()
    }

    private fun addObservers() {
        viewModel.appNavigation.observe(viewLifecycleOwner, { appNav ->
            when (appNav) {
                is AppNavigation.Back -> onBackPressed()
                is AppNavigation.ActivityNavigation -> {
                    activity?.startActivityByName(
                        appNav.appActivityName,
                        appNav.params,
                        appNav.isNewTask
                    )
                }
                is AppNavigation.FragmentNavigation -> nav(
                    appNav.navDirections,
                    appNav.withUpAnimation
                )
                is AppNavigation.ImplicitNavigation -> {
                    val intent = Intent(appNav.appExternalAction.name, Uri.parse(appNav.stringUri))
                    appNav.packageName?.let { intent.setPackage(it) }
                    startActivity(intent)
                }
            }
        })
    }
}