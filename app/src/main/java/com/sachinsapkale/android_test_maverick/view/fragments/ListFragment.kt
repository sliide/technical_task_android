package com.android_test_maverick.view.fragments

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
import com.sachinsapkale.android_test_maverick.view.fragments.CreateUserBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(),
    CreateUserBottomSheetDialogFragment.CreateUserListener,
    ListAdapter.ListAdapterListener{
    private val viewModel: MainViewModel by viewModels()
    private val adapter = ListAdapter()
    var binding: FragmentListBinding? = null
    val deafultPageNumber: Int = 1 // setting default to get last page number

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

        viewModel.userList.observe(this, {
            adapter.setUser(it)
            adapter.setListener(this)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding?.let { it.progressDialog.visibility = View.VISIBLE }
            } else {
                binding?.let { it.progressDialog.visibility = View.GONE }
            }
        })

        viewModel.getLastPageNumbner(deafultPageNumber)

    }

    override fun onNewUserClicked(user: UserModel) {
        viewModel.createNewUser(BuildConfig.ACCESS_TOKEN,user)
        viewModel.singleUser.observe(this, {
            Toast.makeText(context, getString(R.string.user_added,user.name), Toast.LENGTH_SHORT).show()
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onUserDelete(user: UserModel) {
        viewModel.createNewUser(BuildConfig.ACCESS_TOKEN,user)
        viewModel.singleUser.observe(this, {
            Toast.makeText(context, getString(R.string.user_added,user.name), Toast.LENGTH_SHORT).show()
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}