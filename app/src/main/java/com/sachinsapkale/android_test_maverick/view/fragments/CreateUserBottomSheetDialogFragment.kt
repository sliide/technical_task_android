package com.sachinsapkale.android_test_maverick.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android_test_maverick.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sachinsapkale.android_test_maverick.R

class CreateUserBottomSheetDialogFragment : BottomSheetDialogFragment() {

    lateinit var listener: CreateUserListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setUpBottomSheetListener(clistener: CreateUserListener){
        listener = clistener
    }

    interface CreateUserListener {
        fun onNewUserClicked(user: UserModel)
    }
}