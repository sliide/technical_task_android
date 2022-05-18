package com.hanna.sliidetest.presenter

import android.view.View
import androidx.core.view.isVisible
import com.hanna.sliidetest.R


class ViewStates(private val view: View) {

    fun setState(state: ViewState) {
        ViewState.values().onEach { viewState ->
            view.findViewById<View?>(viewState.viewId)?.isVisible = state == viewState
        }
    }

    enum class ViewState(val viewId: Int) {
        LOADING(R.id.loading_view),
        MAIN(R.id.main_view),
        ERROR(0);
    }
}