package com.example.airbagtest.ui.ApplicationList

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airbagtest.R
import com.example.airbagtest.databinding.FragmentApplicationListBinding
import com.example.airbagtest.model.RunningAppProcess
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
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

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.uiState.observe(viewLifecycleOwner){
                updateUi(it)
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
        updateRecycler(applicationListUiState.runningAppProcess)
        updateFloatingButton(applicationListUiState.hasPermissions)
    }

    private fun updateRecycler(runningAppProcessList:List<RunningAppProcess>){
        applicationListAdapter.submitList(runningAppProcessList)
    }

    private fun updateFloatingButton(hasPermissions: Boolean){
        when(hasPermissions){
            true -> binding.fabPermissions.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_permission_granted))
            false -> binding.fabPermissions.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_permission_no_granted))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
        loadView()
        requestPermissions()
        requestProcessesToShowList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPermissions() {
        if (PermissionUtility.hasPackageUsageStatsPermissions(requireContext())) {
            hasPermissions(true)
            return
        }
        hasPermissions(false)
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    private fun requestProcessesToShowList(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.requestBackgroundProcesses()
        }
    }

    private fun hasPermissions(value: Boolean){
            viewModel.hasPermissions(value)
    }


}