package com.sliide.demoapp.ui.adduser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.sliide.demoapp.R
import com.sliide.demoapp.databinding.DialogAddUserBinding
import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.retrofit.UserNetworkEntity
import com.sliide.demoapp.ui.main.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class AddUserDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddUserBinding
    private var onUserCreateListener: OnUserCreateListener? = null
    private val viewModel: AddUserViewModel by viewModels()

    companion object {
        fun display(
            fragmentManager: FragmentManager,
            onUserCreateListener: OnUserCreateListener
        ): AddUserDialogFragment {
            val dialog = AddUserDialogFragment()
            dialog.setOnUserCreateListener(onUserCreateListener)
            dialog.show(fragmentManager, AddUserDialogFragment::class.simpleName)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        Log.e("DEMO", "VM instance is ${viewModel.hashCode()}")
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.window?.setWindowAnimations(R.style.AppTheme_FullScreenDialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addUserToolbar.title = "Add user"
        binding.addUserToolbar.setNavigationOnClickListener { dismiss() }
        binding.addUserToolbar.inflateMenu(R.menu.add_user_menu)
        binding.addUserToolbar.setOnMenuItemClickListener {
            viewModel.savedButtonClicked()
            true
        }

        binding.nameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.nameChanged(text?.toString().orEmpty())
        }

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.emailChanged(text?.toString().orEmpty())
        }

        binding.maleRadio.setOnCheckedChangeListener { _, selected ->
            if (selected) viewModel.genderChanged(
                Gender.MALE
            )
        }

        binding.femaleRadio.setOnCheckedChangeListener { _, selected ->
            if (selected) viewModel.genderChanged(
                Gender.FEMALE
            )
        }

        binding.statusSwitch.setOnCheckedChangeListener { _, selected ->
            if (selected) {
                viewModel.statusChanged(Status.ACTIVE)
            } else {
                viewModel.statusChanged(Status.INACTIVE)
            }
        }

    }

    private fun processViewState(viewState: AddUserViewState) {
        Log.e("DEMO", "AddUserViewState ${viewState}")
        binding.emailInputField.error = viewState.emailError
        binding.nameInputField.error = viewState.nameError
        if (viewState.user != null) {
            onUserCreateListener?.onUserCreateListener(viewState.user)
            dismiss()
        }
    }

    fun setOnUserCreateListener(onUserCreateListener: OnUserCreateListener) {
        this.onUserCreateListener = onUserCreateListener
    }

}