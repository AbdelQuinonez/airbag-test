package com.example.airbagtest.ui.applicationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.airbagtest.databinding.RunningAppProcessItemBinding
import com.example.airbagtest.domain.model.RunningAppProcess

class ApplicationListAdapter :
    ListAdapter<RunningAppProcess, ApplicationListAdapter.ViewHolder>(RunningAppProcessDiffUtil) {

    class ViewHolder(private val runningProcessItemBinding: RunningAppProcessItemBinding) :
        RecyclerView.ViewHolder(runningProcessItemBinding.root) {
        fun bind(runningAppProcess: RunningAppProcess) {
            runningProcessItemBinding.apply {
                tvProcessName.text = runningAppProcess.processName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RunningAppProcessItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val runningAppProcess = getItem(position)
        holder.bind(runningAppProcess)
    }
}

object RunningAppProcessDiffUtil : DiffUtil.ItemCallback<RunningAppProcess>() {

    override fun areItemsTheSame(
        oldItem: RunningAppProcess,
        newItem: RunningAppProcess
    ): Boolean =
        oldItem.processName == newItem.processName


    override fun areContentsTheSame(
        oldItem: RunningAppProcess,
        newItem: RunningAppProcess
    ): Boolean =
        oldItem == newItem

}