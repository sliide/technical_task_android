package com.sachinsapkale.android_test_maverick.view.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.android_test_maverick.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sachinsapkale.android_test_maverick.R
import com.sachinsapkale.android_test_maverick.databinding.FragmentCreateUserBinding
import com.what3words.lib.isEmailValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUserBottomSheetDialogFragment : BottomSheetDialogFragment() {

    lateinit var listener: CreateUserListener
    var binding: FragmentCreateUserBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.saveBtn.setOnClickListener { setUpRequestBody() }
        }
    }

    fun setUpBottomSheetListener(clistener: CreateUserListener) {
        listener = clistener
    }

    fun setUpRequestBody() {
        val name = binding?.enterNameEdTxt?.text.toString()
        val email = binding?.enterEmailEdTxt?.text.toString()
        val radioButtonID = binding?.genderRg?.checkedRadioButtonId
        if (radioButtonID == -1) {
            Toast.makeText(context, getString(R.string.invalid_gender), Toast.LENGTH_SHORT).show()
            return
        }
        val radioButton: RadioButton =
            binding?.genderRg?.findViewById(radioButtonID!!) as RadioButton
        val gender = radioButton.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, getString(R.string.empty_name), Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, getString(R.string.empty_email), Toast.LENGTH_SHORT).show()
            return
        }

        if (!email.isEmailValid()) {
            Toast.makeText(context, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
            return
        }

        val newUser = UserModel(0, name, email, gender, "active")

        listener.onNewUserClicked(newUser)
        dismiss()
    }

    interface CreateUserListener {
        fun onNewUserClicked(user: UserModel)
    }
}