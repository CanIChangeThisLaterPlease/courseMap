package com.example.coursemap

import com.google.firebase.auth.FirebaseUser

object UserManager {
    private var currentUser: FirebaseUser? = null

    fun getCurrentUser(): FirebaseUser? {
        return currentUser
    }

    fun setCurrentUser(user: FirebaseUser?) {
        currentUser = user
    }
}
