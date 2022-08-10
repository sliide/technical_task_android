package com.sliide.demoapp.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sliide.demoapp.R
import com.sliide.demoapp.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private lateinit var binding: FragmentUsersBinding
    private val viewModel: UsersViewModel by activityViewModels()
    private val usersAdapter = UsersAdapter(deleteUser = {
        deleteUser(it)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        binding.usersRecyclerview.adapter = usersAdapter
        binding.usersRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun processViewState(viewState: UsersViewState) {
        if (viewState.error != null) {
            Toast.makeText(requireActivity(), viewState.error, Toast.LENGTH_LONG).show()
        }

        if (viewState.showProgressBar) {
            binding.usersRecyclerview.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.usersRecyclerview.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

        if (viewState.usersList.isNotEmpty()) {
            usersAdapter.submitList(viewState.usersList)
        }
    }

    private fun deleteUser(userId: Int) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogCustom)
            .setTitle(getString(R.string.remove_user))
            .setPositiveButton(
                getString(R.string.remove)
            ) { dialog, _ ->
                viewModel.deleteUser(userId)
                dialog?.dismiss()
            }
            .setNegativeButton(getString(R.string.dismiss)) { dialog, _ ->
                dialog?.dismiss()
            }
            .create().show()
    }

}