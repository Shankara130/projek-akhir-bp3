package com.example.edunovel.util

object Constants {
    const val PREFS_NAME = "edunovel_prefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USERNAME = "username"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    
    // Character personalities
    val PERSONALITIES = listOf(
        "Cheerful", "Serious", "Friendly", "Mysterious", 
        "Energetic", "Calm", "Wise", "Playful"
    )
    
    // Subjects
    val SUBJECTS = listOf(
        "Math", "Science", "English", "History", 
        "Geography", "Physics", "Chemistry", "Biology"
    )
    
    // Image picker
    const val PICK_IMAGE_REQUEST = 1001
    const val PERMISSION_REQUEST_CODE = 1002
}