package com.example.coursemap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextText)
        editTextPassword = findViewById(R.id.editTextTextPassword)
        buttonLogin = findViewById(R.id.btn_login)
        buttonRegister = findViewById(R.id.registerPageButton)
        progressBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        UserManager.setCurrentUser(user)
        buttonRegister.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = task.exception?.message
                    Toast.makeText(this, "Login failed. Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Check if the user is already logged in
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            val intent = Intent(this, HomePage::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}
