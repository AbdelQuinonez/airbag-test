package com.example.airbagtest.ui.applicationList

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airbagtest.databinding.FragmentApplicationListBinding
import com.example.airbagtest.ui.applicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.PermissionUtility
import com.example.airbagtest.utils.SimpleDialogUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationListFragment : Fragment() {
    private var _binding: FragmentApplicationListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApplicationListViewModel by viewModels()
    private val applicationListAdapter = ApplicationListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplicationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initObservable() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.uiState.collect { uiState ->
                    // New value received
                    updateUi(uiState)
                }
            }
        }
    }

    private fun loadView() {
        binding.rvRunningAppProcess.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = applicationListAdapter
        }
        binding.fabPermissions.setOnClickListener{
            SimpleDialogUtility(requireContext()).showDialog()
        }
    }

    private fun updateUi(applicationListUiState: ApplicationListUiState) {
        applicationListAdapter.submitList(applicationListUiState.runningAppProcess)
    }

    override fun onResume() {
        super.onResume()
        requestProcessesToShowList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
        loadView()
        requestPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPermissions() {
        if (PermissionUtility.hasPackageUsageStatsPermissions(requireContext())) {
            return
        }
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    private fun requestProcessesToShowList(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.requestBackgroundProcesses()
        }
    }

}