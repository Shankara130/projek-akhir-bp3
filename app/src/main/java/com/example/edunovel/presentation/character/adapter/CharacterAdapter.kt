package com.example.edunovel.presentation.character.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edunovel.R
import com.example.edunovel.databinding.ItemCharacterBinding
import com.example.edunovel.domain.model.Character

class CharacterAdapter(
    private val onItemClick: (Character) -> Unit,
    private val onEditClick: (Character) -> Unit,
    private val onDeleteClick: (Character) -> Unit
) : ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(character: Character) {
            binding.apply {
                tvCharacterName.text = character.name
                tvPersonality.text = character.personality
                tvSubject.text = character.subject
                tvDescription.text = character.description
                
                // Show badge for default characters
                if (character.isDefault) {
                    tvDefaultBadge.visibility = android.view.View.VISIBLE
                } else {
                    tvDefaultBadge.visibility = android.view.View.GONE
                }
                
                // Load image
                character.imageUrl.takeIf { it.isNotEmpty() }?.let { uriString ->
                    Glide.with(itemView.context)
                        .load(Uri.parse(uriString))
                        .placeholder(R.drawable.ic_character_placeholder)
                        .error(R.drawable.ic_character_placeholder)
                        .centerCrop()
                        .into(ivCharacterImage)
                } ?: run {
                    ivCharacterImage.setImageResource(R.drawable.ic_character_placeholder)
                }
                
                // Click listeners
                root.setOnClickListener { onItemClick(character) }
                btnEdit.setOnClickListener { onEditClick(character) }
                btnDelete.setOnClickListener { onDeleteClick(character) }
            }
        }
    }
    
    class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}