package com.example.edunovel.presentation.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edunovel.databinding.ItemDialogueBinding

data class DialogueItem(
    val id: Int,
    val speaker: String,
    val text: String,
    val isPlayer: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

class DialogueAdapter : ListAdapter<DialogueItem, DialogueAdapter.DialogueViewHolder>(DialogueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogueViewHolder {
        val binding = ItemDialogueBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DialogueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DialogueViewHolder(
        private val binding: ItemDialogueBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dialogue: DialogueItem) {
            binding.apply {
                tvSpeaker.text = dialogue.speaker
                tvDialogue.text = dialogue.text
                
                // Style differently for player vs NPC
                if (dialogue.isPlayer) {
                    cardDialogue.setCardBackgroundColor(
                        itemView.context.getColor(android.R.color.holo_blue_light)
                    )
                } else {
                    cardDialogue.setCardBackgroundColor(
                        itemView.context.getColor(android.R.color.white)
                    )
                }
            }
        }
    }

    private class DialogueDiffCallback : DiffUtil.ItemCallback<DialogueItem>() {
        override fun areItemsTheSame(oldItem: DialogueItem, newItem: DialogueItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DialogueItem, newItem: DialogueItem): Boolean {
            return oldItem == newItem
        }
    }
}