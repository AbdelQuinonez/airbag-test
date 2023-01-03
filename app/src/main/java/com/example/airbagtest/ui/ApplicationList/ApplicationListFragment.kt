package com.example.airbagtest.ui.ApplicationList

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.example.airbagtest.databinding.FragmentApplicationListBinding
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.PermissionUtility
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

        lifecycleScope.launch{
            viewModel.uiState.observe(viewLifecycleOwner){
                updateUi(it)
            }
        }

        lifecycleScope.launch {
            viewModel.outputWorkInfoItems.observe(viewLifecycleOwner) {
                if (it[0].state == WorkInfo.State.SUCCEEDED) {
                    requestProcessesToShowList()
                }
            }
        }

    }

    private fun loadView() {
        binding.rvRunningAppProcess.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = applicationListAdapter
        }
    }

    private fun updateUi(applicationListUiState: ApplicationListUiState) {
        applicationListAdapter.submitList(applicationListUiState.runningAppProcess)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
        requestPermissions()
        loadView()
        requestProcessesToShowList()

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
        lifecycleScope.launch{
            viewModel.requestBackgroundProcesses()
        }
    }


}