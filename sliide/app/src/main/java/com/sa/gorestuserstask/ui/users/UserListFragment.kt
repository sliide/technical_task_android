package com.sa.gorestuserstask.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sa.gorestuserstask.databinding.FragmentUserListBinding
import com.sa.gorestuserstask.di.DaggerAppComponent
import retrofit2.Retrofit
import javax.inject.Inject

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding
        get() = requireNotNull(_binding) {
            "The binding was either accessed too late or too early. " +
                    "Check ${this.javaClass.name} and it's presentation logic!"
        }

    @Inject
    lateinit var retrofit: Retrofit

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder()
            .application(requireActivity().application)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
