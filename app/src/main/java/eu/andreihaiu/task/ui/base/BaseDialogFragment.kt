package eu.andreihaiu.task.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import eu.andreihaiu.task.R

abstract class BaseDialogFragment<T : ViewDataBinding> : AppCompatDialogFragment() {

    internal abstract fun getLayoutResource(): Int
    internal abstract fun onBindViews(savedInstanceState: Bundle?)

    private lateinit var _viewDataBinding: T
    val viewDataBinding: T
        get() = _viewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme)
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

        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        onBindViews(savedInstanceState)
    }

    protected fun showSnackbar(message: String) {
        Snackbar.make(viewDataBinding.root, message, Snackbar.LENGTH_LONG).show()
    }

    fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                0
            )
        }
    }
}