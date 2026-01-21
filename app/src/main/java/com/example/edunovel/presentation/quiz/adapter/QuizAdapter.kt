package com.example.edunovel.presentation.quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edunovel.R
import com.example.edunovel.databinding.ItemQuizQuestionBinding
import com.example.edunovel.domain.model.QuizQuestion

class QuizAdapter(
    private val onAnswerSelected: (QuizQuestion, Int) -> Unit
) : ListAdapter<QuizQuestion, QuizAdapter.QuizViewHolder>(QuizDiffCallback()) {

    private var selectedAnswers = mutableMapOf<Long, Int>()
    private var showResults = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding, onAnswerSelected)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            position + 1,
            selectedAnswers[getItem(position).id],
            showResults
        )
    }

    fun setSelectedAnswer(questionId: Long, answerIndex: Int) {
        selectedAnswers[questionId] = answerIndex
        notifyDataSetChanged()
    }

    fun showResults(show: Boolean) {
        showResults = show
        notifyDataSetChanged()
    }

    class QuizViewHolder(
        private val binding: ItemQuizQuestionBinding,
        private val onAnswerSelected: (QuizQuestion, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            question: QuizQuestion,
            questionNumber: Int,
            selectedAnswer: Int?,
            showResults: Boolean
        ) {
            binding.apply {
                tvQuestionNumber.text = "Soal $questionNumber"
                tvQuestion.text = question.question

                val answerButtons = listOf(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4)

                question.options.forEachIndexed { index, option ->
                    if (index < answerButtons.size) {
                        answerButtons[index].apply {
                            text = option
                            visibility = android.view.View.VISIBLE

                            // Reset styling
                            setBackgroundColor(
                                ContextCompat.getColor(context, R.color.answer_default)
                            )

                            if (showResults) {
                                // Show correct/incorrect answers
                                isEnabled = false
                                when {
                                    index == question.correctAnswer -> {
                                        setBackgroundColor(
                                            ContextCompat.getColor(context, R.color.answer_correct)
                                        )
                                    }
                                    index == selectedAnswer && index != question.correctAnswer -> {
                                        setBackgroundColor(
                                            ContextCompat.getColor(context, R.color.answer_wrong)
                                        )
                                    }
                                }
                            } else {
                                // Normal quiz mode
                                isEnabled = true
                                if (index == selectedAnswer) {
                                    setBackgroundColor(
                                        ContextCompat.getColor(context, R.color.answer_selected)
                                    )
                                }

                                setOnClickListener {
                                    onAnswerSelected(question, index)
                                }
                            }
                        }
                    }
                }

                // Hide unused buttons
                for (i in question.options.size until answerButtons.size) {
                    answerButtons[i].visibility = android.view.View.GONE
                }

                // Show explanation if in results mode
                if (showResults && question.explanation.isNotBlank()) {
                    tvExplanation.visibility = android.view.View.VISIBLE
                    tvExplanation.text = "Penjelasan: ${question.explanation}"
                } else {
                    tvExplanation.visibility = android.view.View.GONE
                }
            }
        }
    }

    private class QuizDiffCallback : DiffUtil.ItemCallback<QuizQuestion>() {
        override fun areItemsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
            return oldItem == newItem
        }
    }
}