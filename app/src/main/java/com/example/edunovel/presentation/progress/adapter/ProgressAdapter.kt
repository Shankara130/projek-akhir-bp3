package com.example.edunovel.presentation.progress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edunovel.databinding.ItemProgressBinding
import com.example.edunovel.domain.model.Progress
import java.text.SimpleDateFormat
import java.util.*

class ProgressAdapter(
    private val onDeleteClick: (Progress) -> Unit
) : ListAdapter<Progress, ProgressAdapter.ProgressViewHolder>(ProgressDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = ItemProgressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProgressViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ProgressViewHolder(
        private val binding: ItemProgressBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(progress: Progress) {
            binding.apply {
                tvSubject.text = progress.subject
                tvChapter.text = "Chapter ${progress.chapterId}"
                tvScore.text = "${progress.score}%"
                
                // Status badge
                if (progress.isCompleted) {
                    tvStatus.text = "Selesai"
                    tvStatus.setBackgroundColor(itemView.context.getColor(android.R.color.holo_green_light))
                } else {
                    tvStatus.text = "Belum Selesai"
                    tvStatus.setBackgroundColor(itemView.context.getColor(android.R.color.holo_orange_light))
                }
                
                // Format date
                val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
                tvDate.text = dateFormat.format(Date(progress.updatedAt))
                
                // Progress bar
                progressBar.progress = progress.score
                
                // Delete button
                btnDelete.setOnClickListener {
                    onDeleteClick(progress)
                }
            }
        }
    }
    
    class ProgressDiffCallback : DiffUtil.ItemCallback<Progress>() {
        override fun areItemsTheSame(oldItem: Progress, newItem: Progress): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Progress, newItem: Progress): Boolean {
            return oldItem == newItem
        }
    }
}