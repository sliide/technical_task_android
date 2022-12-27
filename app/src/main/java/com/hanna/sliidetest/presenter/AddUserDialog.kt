package com.hanna.sliidetest.presenter

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.hanna.sliidetest.R
import com.hanna.sliidetest.databinding.DialogAddUserBinding
import com.hanna.sliidetest.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserDialog : AppCompatDialogFragment(R.layout.dialog_add_user) {

    private val binding: DialogAddUserBinding by viewBinding(DialogAddUserBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addClickListeners()
    }

    private fun addClickListeners() {
        binding.btnPositive.setOnClickListener {
            if (!hasValidName()){
                Toast.makeText(requireContext(), "Please ensure a valid name is inserted.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isValidEmail(binding.etEmail.text?.toString().orEmpty())){
                Toast.makeText(requireContext(), "Please ensure a valid email is inserted.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedGenderId = binding.genderGroupRb.checkedRadioButtonId
            val genderText = requireView().findViewById<RadioButton>(selectedGenderId).text
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(
                    RESULT_NAME to binding.etName.text.toString(),
                    RESULT_EMAIL to binding.etEmail.text.toString(),
                    RESULT_GENDER to genderText.toString().lowercase()
                )
            )
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            dismiss()
        }
    }
    private fun hasValidName(): Boolean = !binding.etName.text?.toString().isNullOrEmpty()
    private fun isValidEmail(input: String?): Boolean {
        return input?.let {
            if (input.isEmpty()) {
                return@let false
            }
            android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
        } ?: false
    }

    companion object {
        const val REQUEST_KEY = "request_key_add"
        const val RESULT_NAME = "name"
        const val RESULT_EMAIL = "email"
        const val RESULT_GENDER = "gender"
    }
}