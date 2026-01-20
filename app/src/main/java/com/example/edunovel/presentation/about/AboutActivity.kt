package com.example.edunovel.presentation.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.edunovel.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAboutBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupContent()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "About"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupContent() {
        // TODO: Replace with your actual data from PDF requirements
        binding.apply {
            tvName.text = "Muhamad Galbi Alayubi"
            tvEmail.text = "20230810113@uniku.ac.id"
            tvNim.text = "NIM: 20230810113"
            tvClass.text = "Class: TINFC-2023-03"
            tvDescription.text = """
                EduNovel adalah aplikasi pembelajaran interaktif yang menggabungkan 
                konsep visual novel dengan materi edukasi. Aplikasi ini dibuat sebagai 
                proyek akhir mata kuliah Bahasa Pemrograman 3.
                
                Fitur utama:
                • Story Mode - Belajar melalui cerita interaktif
                • Quiz Challenge - Uji pemahaman dengan kuis
                • Custom Characters - Buat karakter pembelajaran sendiri
                • Progress Tracking - Pantau perkembangan belajar
                • High Score - Leaderboard pencapaian
            """.trimIndent()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}