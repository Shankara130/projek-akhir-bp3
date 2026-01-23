package com.example.edunovel.presentation.highscore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edunovel.databinding.ItemHighscoreBinding
import com.example.edunovel.domain.model.QuizSession
import java.text.SimpleDateFormat
import java.util.*

class HighScoreAdapter : ListAdapter<QuizSession, HighScoreAdapter.HighScoreViewHolder>(HighScoreDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val binding = ItemHighscoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HighScoreViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }
    
    class HighScoreViewHolder(
        private val binding: ItemHighscoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(quiz: QuizSession, rank: Int) {
            binding.apply {
                tvRank.text = "#$rank"
                tvSubject.text = quiz.subject
                tvScore.text = "${quiz.score}%"
                tvQuestions.text = "${quiz.totalQuestions} soal"
                
                // Format date
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
                tvDate.text = dateFormat.format(Date(quiz.completedAt))
                
                // Medal colors for top 3
                when (rank) {
                    1 -> {
                        tvRank.setBackgroundColor(itemView.context.getColor(android.R.color.holo_orange_dark))
                        tvRank.text = "🥇"
                    }
                    2 -> {
                        tvRank.setBackgroundColor(itemView.context.getColor(android.R.color.darker_gray))
                        tvRank.text = "🥈"
                    }
                    3 -> {
                        tvRank.setBackgroundColor(itemView.context.getColor(android.R.color.holo_orange_light))
                        tvRank.text = "🥉"
                    }
                }
            }
        }
    }
    
    class HighScoreDiffCallback : DiffUtil.ItemCallback<QuizSession>() {
        override fun areItemsTheSame(oldItem: QuizSession, newItem: QuizSession): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: QuizSession, newItem: QuizSession): Boolean {
            return oldItem == newItem
        }
    }
}