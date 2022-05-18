package com.hanna.sliidetest.presenter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hanna.sliidetest.R
import com.hanna.sliidetest.data.network.Status
import com.hanna.sliidetest.databinding.FragmentUsersBinding
import com.hanna.sliidetest.utils.provideViewModel
import com.hanna.sliidetest.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
                        Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}