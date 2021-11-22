package eu.andreihaiu.task.viewmodels.base

import android.content.Intent
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import eu.andreihaiu.task.util.status.Status

interface BaseViewModel {
    val appNavigation: LiveData<AppNavigation>
    val status: LiveData<Status>
}

sealed class AppActivity(val className: String) {
    object Main : AppActivity("$PACKAGE_NAME.ui.main.MainActivity")
}

sealed class AppExternalAction(val name: String) {
    object Dial : AppExternalAction(Intent.ACTION_DIAL)
    object Browser : AppExternalAction(Intent.ACTION_VIEW)
    object Email : AppExternalAction(Intent.ACTION_SENDTO)
}

sealed class AppNavigation {
    object Back : AppNavigation()

    data class ActivityNavigation(
        val appActivityName: AppActivity,
        val isNewTask: Boolean = false,
        val params: Map<String, Any>? = null
    ) : AppNavigation()

    data class FragmentNavigation(
        val navDirections: NavDirections,
        val withUpAnimation: Boolean = false,
        val params: Map<String, Any>? = null
    ) : AppNavigation()

    data class ImplicitNavigation(
        val appExternalAction: AppExternalAction,
        val stringUri: String,
        val packageName: String? = null
    ) : AppNavigation()
}
