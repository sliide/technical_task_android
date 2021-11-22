package eu.andreihaiu.task.ui.users

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import eu.andreihaiu.task.R
import eu.andreihaiu.task.databinding.FragmentUsersBinding
import eu.andreihaiu.task.ui.base.BaseDialogFragment
import eu.andreihaiu.task.ui.base.BaseFragment
import eu.andreihaiu.task.util.extensions.setThrottledClickListener
import eu.andreihaiu.task.viewmodels.users.UserEvent
import eu.andreihaiu.task.viewmodels.users.UsersViewModelImpl

class UsersFragment : BaseFragment<UsersViewModelImpl, FragmentUsersBinding>(
    UsersViewModelImpl::class
), FragmentResultListener {

    private lateinit var userDialog: BaseDialogFragment<*>
    private var toast: Toast? = null
    private val usersAdapter = UsersAdapter(
        onDeleteCallback = { user ->
            user.id?.let {
                openDialog(
                    DeleteUserDialog(it),
                    DeleteUserDialog.REQUEST_KEY,
                    DeleteUserDialog.TAG
                )
            }
        }
    )

    override fun getLayoutResource(): Int = R.layout.fragment_users

    override fun onBindViews(savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel
        setupRecyclerView()
        addObservers()
        addClickListeners()
    }

    private fun setupRecyclerView() {
        viewDataBinding.rvUsers.adapter = usersAdapter
    }

    private fun addObservers() {
        viewModel.users.observe(viewLifecycleOwner, {
            usersAdapter.submitList(it)
        })

        viewModel.userEvent.observe(viewLifecycleOwner, {
            toast?.cancel()
            when (it) {
                UserEvent.ADD_SUCCES -> {
                    toast = Toast.makeText(context, "User added with success", Toast.LENGTH_SHORT)
                }
                UserEvent.DELETE_SUCCES -> {
                    toast = Toast.makeText(context, "User deleted with success", Toast.LENGTH_SHORT)
                }
            }
            toast?.show()
        })
    }

    private fun addClickListeners() {
        viewDataBinding.fabAdd.setThrottledClickListener {
            openDialog(AddUserDialog(), AddUserDialog.REQUEST_KEY, AddUserDialog.TAG)
        }

        viewDataBinding.swRefresh.setOnRefreshListener {
            viewDataBinding.swRefresh.isRefreshing = false
            viewModel.getUsers()
        }
        viewDataBinding.swRefresh.isNestedScrollingEnabled = false
    }

    private fun openDialog(type: BaseDialogFragment<*>, requestKey: String, tag: String) {
        userDialog = type
        parentFragmentManager.setFragmentResultListener(requestKey, this, this)
        userDialog.show(parentFragmentManager, tag)
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        when (requestKey) {
            AddUserDialog.REQUEST_KEY -> {
                viewModel.addUser(
                    result.get(AddUserDialog.RESULT_NAME).toString(),
                    result.get(AddUserDialog.RESULT_EMAIL).toString(),
                    result.get(AddUserDialog.RESULT_GENDER).toString()
                )
            }
            DeleteUserDialog.REQUEST_KEY -> {
                viewModel.deleteUser(
                    result.get(DeleteUserDialog.RESULT_KEY).toString().toLong()
                )
            }
        }
    }
}