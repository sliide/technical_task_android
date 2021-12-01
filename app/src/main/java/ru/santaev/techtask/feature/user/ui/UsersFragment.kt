package ru.santaev.techtask.feature.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.santaev.techtask.databinding.FragmentUsersBinding
import ru.santaev.techtask.feature.user.ui.adapter.UserAdapter
import ru.santaev.techtask.utils.ViewModelFactory
import ru.santaev.techtask.utils.appComponent
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UsersViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentUsersBinding
    private val adapter = UserAdapter()

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
    }

    private fun initViews() {
        binding.users.adapter = adapter
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }
    }
}
