package ru.santaev.techtask.feature.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.santaev.techtask.R
import ru.santaev.techtask.databinding.FragmentUsersBinding
import ru.santaev.techtask.feature.user.ui.adapter.UserAdapter
import ru.santaev.techtask.utils.ViewModelFactory
import ru.santaev.techtask.utils.appComponent
import ru.santaev.techtask.utils.observe
import ru.santaev.techtask.utils.observeEvent
import ru.santaev.techtask.utils.observeToast
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UsersViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentUsersBinding
    private val adapter = UserAdapter(
        userLongClickListener = { viewModel.onUserLongClicked(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeToast(viewModel)
    }

    private fun initViews() {
        binding.users.adapter = adapter
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }
        binding.btnAddUser.setOnClickListener {
            UserCreationFragment().show(childFragmentManager, null)
        }
        viewModel.showUserDeletingConfirmation.observeEvent(viewLifecycleOwner) {
            showUserDeleteConfirmationDialog()
        }
    }

    private fun showUserDeleteConfirmationDialog() {
        // TODO popup can be improved by adding information about a user
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.user_deleting_confirmation_title))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.onUserDeletingConfirmed()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .show()
    }
}
