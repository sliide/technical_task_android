package ru.santaev.techtask.feature.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import ru.santaev.techtask.databinding.FragmentUserCreationBinding
import ru.santaev.techtask.utils.ViewModelFactory
import ru.santaev.techtask.utils.appComponent
import ru.santaev.techtask.utils.observeEvent
import ru.santaev.techtask.utils.observeToast
import javax.inject.Inject

class UserCreationFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UserCreationViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentUserCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserCreationBinding.inflate(layoutInflater, container, false)
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
        viewModel.exit.observeEvent(viewLifecycleOwner) { exit ->
            if (exit) {
                dismiss()
            }
        }
    }
}
