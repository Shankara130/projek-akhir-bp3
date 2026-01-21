package com.example.edunovel.presentation.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edunovel.databinding.ItemStorySlideBinding

data class StorySlide(
    val id: Int,
    val characterName: String,
    val characterImageUrl: String,
    val backgroundImageUrl: String,
    val dialogue: String,
    val choices: List<String> = emptyList()
)

class StoryAdapter(
    private val onChoiceSelected: (Int, String) -> Unit
) : ListAdapter<StorySlide, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStorySlideBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryViewHolder(binding, onChoiceSelected)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StoryViewHolder(
        private val binding: ItemStorySlideBinding,
        private val onChoiceSelected: (Int, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(slide: StorySlide) {
            binding.apply {
                tvCharacterName.text = slide.characterName
                tvDialogue.text = slide.dialogue
                
                // Load character image (use Glide or Coil)
                // Glide.with(itemView.context)
                //     .load(slide.characterImageUrl)
                //     .into(ivCharacter)
                
                // Show choices if available
                if (slide.choices.isNotEmpty()) {
                    btnChoice1.visibility = android.view.View.VISIBLE
                    btnChoice2.visibility = android.view.View.VISIBLE
                    
                    btnChoice1.text = slide.choices.getOrNull(0)
                    btnChoice2.text = slide.choices.getOrNull(1)
                    
                    btnChoice1.setOnClickListener {
                        onChoiceSelected(slide.id, slide.choices[0])
                    }
                    
                    btnChoice2.setOnClickListener {
                        onChoiceSelected(slide.id, slide.choices[1])
                    }
                } else {
                    btnChoice1.visibility = android.view.View.GONE
                    btnChoice2.visibility = android.view.View.GONE
                }
            }
        }
    }

    private class StoryDiffCallback : DiffUtil.ItemCallback<StorySlide>() {
        override fun areItemsTheSame(oldItem: StorySlide, newItem: StorySlide): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StorySlide, newItem: StorySlide): Boolean {
            return oldItem == newItem
        }
    }
}