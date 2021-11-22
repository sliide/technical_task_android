package eu.andreihaiu.task.ui.users

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import eu.andreihaiu.task.R
import eu.andreihaiu.task.databinding.DialogAddUserBinding
import eu.andreihaiu.task.ui.base.BaseDialogFragment
import eu.andreihaiu.task.util.extensions.isValidEmail

class AddUserDialog : BaseDialogFragment<DialogAddUserBinding>() {

    override fun getLayoutResource(): Int = R.layout.dialog_add_user

    override fun onBindViews(savedInstanceState: Bundle?) {
        addClickListeners()
    }

    private fun addClickListeners() {
        viewDataBinding.btnPositive.setOnClickListener {
            if (checkFieldsEmpty()) return@setOnClickListener
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(
                    RESULT_NAME to viewDataBinding.etName.text,
                    RESULT_EMAIL to viewDataBinding.etEmail.text,
                    RESULT_GENDER to viewDataBinding.etGender.text
                )
            )
            dismiss()
        }

        viewDataBinding.btnNegative.setOnClickListener {
            dismiss()
        }

        viewDataBinding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun checkFieldsEmpty(): Boolean {
        return viewDataBinding.etName.text.isBlank() || viewDataBinding.etGender.text.isBlank() ||
                !viewDataBinding.etEmail.text.toString().isValidEmail()
    }

    companion object {
        const val TAG = "AddUserDialog"

        const val REQUEST_KEY = "request_key_add"
        const val RESULT_NAME = "name"
        const val RESULT_EMAIL = "email"
        const val RESULT_GENDER = "gender"
    }
}
