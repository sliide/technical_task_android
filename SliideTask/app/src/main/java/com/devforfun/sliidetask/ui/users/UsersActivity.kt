package com.devforfun.sliidetask.ui.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devforfun.sliidetask.R
import com.devforfun.sliidetask.databinding.ActivityUsersBinding
import com.devforfun.sliidetask.services.model.User
import com.devforfun.sliidetask.utils.CreateUserDialogListener
import com.devforfun.sliidetask.utils.DialogListener
import com.devforfun.sliidetask.utils.createUserDialog
import com.devforfun.sliidetask.utils.noTitleYesOrNoDialog
import kotlinx.android.synthetic.main.activity_users.view.*

class UsersActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(UsersViewModel::class.java) }
    lateinit var binding: ActivityUsersBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        lifecycle.addObserver(viewModel)
        initViews()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.usersResult.observe(this, { userResult ->
            binding.progressBar.visibility = View.GONE
            binding.fabAddUser.visibility = View.VISIBLE
            userResult?.let {
                it.success?.let { usersList ->
                    if (usersList.isNotEmpty()) {
                        usersAdapter.updateData(usersList.toMutableList())
                        binding.itemList.visibility = View.VISIBLE
                        binding.messageText.visibility = View.GONE
                    } else {
                        binding.itemList.visibility = View.GONE
                        binding.messageText.text = getString(R.string.no_users_available)
                    }
                }
                it.errorStringId?.let { errorStringId ->
                    binding.itemList.visibility = View.GONE
                    binding.messageText.text = getString(errorStringId)

                }
            }
        })

        viewModel.deleteUserResult.observe(this, { deleteUserResult ->
            deleteUserResult?.let { deleteResult ->
                deleteResult.deletedUserId?.let { usersAdapter.deleteUser(it) }
                deleteResult.error?.let {
                    Toast.makeText(
                        this,
                        "Error deleting the user",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })

        viewModel.createUserResult.observe(this, { createUserResult ->
            createUserResult?.let { result ->
                result.success?.let {
                    usersAdapter.insertUser(it)
                    viewModel.getUsers()
                }

                result.error?.let {
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initViews() {
        usersAdapter = UsersAdapter(arrayListOf()) { user -> onUserLongPressed(user) }
        val viewManager = LinearLayoutManager(this)
        binding.itemList.apply {
            layoutManager = viewManager
            adapter = usersAdapter
        }
        binding.messageText.text = getString(R.string.searching_for_users)

        binding.fabAddUser.setOnClickListener {
            createUserDialog(getString(R.string.user_create_dialog_message),
                R.string.ok, R.string.cancel, object : CreateUserDialogListener {
                    override fun onPositiveClick(name: String, email: String) {
                        if (viewModel.isInputValid(name, email)) {
                            viewModel.createUser(name, email)
                            Toast.makeText(
                                this@UsersActivity,
                                "Creating user: $name",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {
                            Toast.makeText(
                                this@UsersActivity,
                                "Invalid username or email",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onNegativeClick() {}
                }).show()
        }
    }

    private fun onUserLongPressed(user: User) {
        noTitleYesOrNoDialog(getString(R.string.delete_user_warning_message, user.name),
            R.string.ok,
            R.string.cancel, object : DialogListener {
                override fun onPositiveClick() {
                    Toast.makeText(
                        this@UsersActivity,
                        "Deleting user " + user.name,
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.deleteUser(user.id)
                }

                override fun onNegativeClick() {

                }

            }).show()
    }

}