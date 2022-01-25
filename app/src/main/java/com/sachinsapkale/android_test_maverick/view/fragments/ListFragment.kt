package com.android_test_maverick.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android_test_maverick.UserModel
import com.android_test_maverick.view.ListAdapter
import com.sachin_sapkale_android_challenge.viewmodel.MainViewModel
import com.sachinsapkale.android_test_maverick.BuildConfig
import com.sachinsapkale.android_test_maverick.R
import com.sachinsapkale.android_test_maverick.databinding.FragmentListBinding
import com.sachinsapkale.android_test_maverick.utils.isNetworkAvailable
import com.sachinsapkale.android_test_maverick.view.fragments.CreateUserBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment(),
    CreateUserBottomSheetDialogFragment.CreateUserListener,
    ListAdapter.ListAdapterListener{
    private val viewModel: MainViewModel by viewModels()
    private val adapter = ListAdapter()
    var binding: FragmentListBinding? = null
    val deafultPageNumber: Int = 1 // setting default to get last page number
    val CREATE_USER_BOTTOMSHEET : String = "createUserBottomSheet"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        //set variables in Binding
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.myAdapter = adapter

        viewModel.userList.observe(viewLifecycleOwner, {
            adapter.setListener(this)
            adapter.setUser(it)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding?.let { it.progressDialog.visibility = View.VISIBLE }
            } else {
                binding?.let { it.progressDialog.visibility = View.GONE }
            }
        })

        if(!isNetworkAvailable(context)){
            viewModel.loading.postValue(false)
            viewModel.errorMessage.postValue(getString(R.string.error_connection))
            return
        }

        viewModel.getUserListFromPage(deafultPageNumber)
        binding?.btnAddUser?.setOnClickListener{ clickAddNewUser() }

    }

    override fun onNewUserClicked(user: UserModel) {
        viewModel.createNewUser(BuildConfig.ACCESS_TOKEN,user)
        viewModel.singleUser.observe(viewLifecycleOwner, {
            viewModel.getUserListFromPage(deafultPageNumber)
        })
    }

    override fun onUserDelete(user: UserModel): Boolean {

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(getString(R.string.are_you_sure))
            .setTitle(getString(R.string.remove_user,user.name))
            .setCancelable(true)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.dismiss()
                viewModel.deleteUser(BuildConfig.ACCESS_TOKEN,user)
                viewModel.deleteUser.observe(viewLifecycleOwner, {
                    viewModel.getUserListFromPage(deafultPageNumber)
                })

                viewModel.errorMessage.observe(viewLifecycleOwner, {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                })

            }).setNegativeButton("Cancel",DialogInterface.OnClickListener {dialogInterface, i -> dialogInterface.dismiss() })


        val alert = dialogBuilder.create()
        alert.show()
        return true
    }

    fun clickAddNewUser() {
        val btmsheet = CreateUserBottomSheetDialogFragment()
        btmsheet.setUpBottomSheetListener(this)
        btmsheet.show(childFragmentManager,CREATE_USER_BOTTOMSHEET)
    }
}