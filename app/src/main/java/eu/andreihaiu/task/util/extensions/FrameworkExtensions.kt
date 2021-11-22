package eu.andreihaiu.task.util.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import eu.andreihaiu.task.R
import eu.andreihaiu.task.viewmodels.base.AppActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

fun Fragment.nav(directions: NavDirections, navUp: Boolean = false) {
    val options = navOptions {
        if (navUp) {
            anim {
                enter = R.anim.slide_up_anim
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.slide_down_anim
            }
        } else {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    }

    NavHostFragment.findNavController(this).navigate(directions, options)
}

fun Fragment.nav(
    @IdRes destinationId: Int,
    navUp: Boolean = false,
    @IdRes popId: Int? = null,
    popInclusive: Boolean = false,
    bundle: Bundle? = null
) {
    val options = navOptions {
        if (navUp) {
            anim {
                enter = R.anim.slide_up_anim
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.slide_down_anim
            }
        } else {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        popId?.let {
            this.popUpTo(it) {
                this.inclusive = popInclusive
            }
        }
    }

    NavHostFragment.findNavController(this).navigate(destinationId, bundle, options)
}

fun Activity.startActivityByName(
    appActivity: AppActivity,
    params: Map<String, Any>? = null,
    finishCurrent: Boolean = true
) {
    val intent = Intent(applicationContext, Class.forName(appActivity.className))
    params?.let { intent.putExtras(it.toBundle()) }
    if (finishCurrent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    } else {
        startActivity(intent)
    }
}

fun <T : Activity> Activity.startAndFinish(clazz: KClass<T>, bundle: Bundle? = null) {
    val intent: Intent? = Intent(applicationContext, clazz.java)
    bundle?.let {
        intent?.putExtras(bundle)
    }
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
    finish()
}

fun Map<String, Any>.toBundle(): Bundle {
    val bundle = Bundle()
    forEach { (key, value) -> bundle.putString(key, value.toString()) }
    return bundle
}

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T : ViewModel> Fragment.getSharedGraphViewModel(
    @IdRes navGraphId: Int,
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): T {
    val store = findNavController().getViewModelStoreOwner(navGraphId).viewModelStore
    return getKoin().getViewModel(
        ViewModelParameter(
            clazz,
            qualifier,
            parameters,
            Bundle(),
            store,
            null
        )
    )
}

fun View.setThrottledClickListener(callback: (View) -> Unit) {
    ViewTreeLifecycleOwner.get(this)?.let { owner ->
        this.setOnClickListener(
            throttleFirst(
                1000L,
                owner.lifecycleScope,
                callback
            )
        )
    }
}

fun <T> throttleFirst(
    skipMs: Long = 1000L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                destinationFunction(param)
                delay(skipMs)
            }
        }
    }
}
