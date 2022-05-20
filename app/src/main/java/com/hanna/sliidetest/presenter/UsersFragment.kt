package com.hanna.sliidetest.presenter

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hanna.sliidetest.R
import com.hanna.sliidetest.data.network.Status
import com.hanna.sliidetest.databinding.FragmentUsersBinding
import com.hanna.sliidetest.presenter.AddUserDialog.Companion.REQUEST_KEY
import com.hanna.sliidetest.presenter.AddUserDialog.Companion.RESULT_EMAIL
import com.hanna.sliidetest.presenter.AddUserDialog.Companion.RESULT_GENDER
import com.hanna.sliidetest.presenter.AddUserDialog.Companion.RESULT_NAME
import com.hanna.sliidetest.utils.provideViewModel
import com.hanna.sliidetest.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val binding: FragmentUsersBinding by viewBinding(FragmentUsersBinding::bind)
    private val usersAdapter = UsersAdapter()

    @Inject
    lateinit var factory: UsersViewModelFactory
    private val viewModel: UsersViewModel by provideViewModel { factory }

    private val viewStates: ViewStates by lazy {
        ViewStates(requireView())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val name = bundle.getString(RESULT_NAME).orEmpty()
            val email = bundle.getString(RESULT_EMAIL).orEmpty()
            val gender = bundle.getString(RESULT_GENDER).orEmpty()

            viewModel.addUser(name, email, gender)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.usersList) {
            adapter = usersAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.usersData.collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        if (resource.data.isNullOrEmpty()) {
                            viewStates.setState(ViewStates.ViewState.LOADING)
                        } else {
                            viewStates.setState(ViewStates.ViewState.MAIN)
                            usersAdapter.submitList(resource.data.orEmpty())
                        }
                    }
                    Status.SUCCESS -> {
                        viewStates.setState(ViewStates.ViewState.MAIN)
                        usersAdapter.submitList(resource.data.orEmpty())
                    }
                    Status.ERROR -> {
                        viewStates.setState(ViewStates.ViewState.ERROR)
                        Toast.makeText(requireContext(), resource.message?: "Error!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.addUserState.observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        //allow work to be done in background
                    }
                    Status.SUCCESS -> {
                        //view will automatically be updated
                    }
                    Status.ERROR -> {
//                        In case of error, display message to user
                        AlertDialog.Builder(requireContext())
                            .setTitle("An Error Occurred")
                            .setMessage(resource.message + "\nWould you like to retry?")
                            .setPositiveButton("Yes"
                            ) { p0, p1 ->
                                p0?.dismiss()
                                findNavController().navigate(R.id.action_UsersFragment_to_AddUserDialog)
                            }
                            .setNegativeButton("No"){ p0, p1 ->
                                p0?.dismiss()
                            }
                            .create().show()
                    }
                }
            }
        }
    }
}