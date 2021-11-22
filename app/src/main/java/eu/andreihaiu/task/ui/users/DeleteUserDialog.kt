package eu.andreihaiu.task.ui.users

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import eu.andreihaiu.task.R
import eu.andreihaiu.task.databinding.DialogDeleteUserBinding
import eu.andreihaiu.task.ui.base.BaseDialogFragment

class DeleteUserDialog(
    private val userId: Long
) : BaseDialogFragment<DialogDeleteUserBinding>() {

    override fun getLayoutResource(): Int = R.layout.dialog_delete_user

    override fun onBindViews(savedInstanceState: Bundle?) {
        addClickListeners()
    }

    private fun addClickListeners() {
        viewDataBinding.btnPositive.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(RESULT_KEY to userId))
            dismiss()
        }

        viewDataBinding.btnNegative.setOnClickListener {
            dismiss()
        }

        viewDataBinding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "DeleteUserDialog"

        const val REQUEST_KEY = "request_key_delete"
        const val RESULT_KEY = "result_key_delete"
    }
}
