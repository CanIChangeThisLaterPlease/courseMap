package com.example.coursemap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
@Suppress("SpellCheckingInspection")
class signup : AppCompatActivity() {
    private lateinit var editTextEmail : TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonRegister : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar : ProgressBar
    private lateinit var spinnerMajor : Spinner
    private lateinit var editTextUniversityYear :TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        editTextEmail = findViewById(R.id.editTextText)
        editTextPassword = findViewById(R.id.editTextTextPassword)
        buttonRegister = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)
        auth = Firebase.auth
        spinnerMajor = findViewById(R.id.spinner)
        editTextUniversityYear = findViewById(R.id.editTextNumber)
        val adapter = ArrayAdapter.createFromResource(this, R.array.major_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMajor.adapter = adapter
        buttonRegister.setOnClickListener(View.OnClickListener {
            FirebaseApp.initializeApp(this)
            progressBar.visibility = View.VISIBLE
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val userMajor = spinnerMajor.selectedItem.toString()
            val universityYear = editTextUniversityYear.text.toString()
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Please enter your E-mail", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(TextUtils.isEmpty(universityYear)) {
                Toast.makeText(this, "Please enter your University year", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val warning = isStrongPassword(password)
            if (warning != null) {
                Toast.makeText(this, warning, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val graph:GraphManager = GraphManager()
                        val profileData = hashMapOf(
                            "major" to userMajor,
                            "universityYear" to universityYear,
                            "graph" to graph.getGraphForMajor(userMajor)
                        )
                        val db = Firebase.firestore
                        db.collection("users")
                            .document(user.uid)
                            .set(profileData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Redirecting", Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.VISIBLE
                                intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                progressBar.visibility = View.VISIBLE
                                Log.e("Firestore Error", "Failed to store data", e)
                                intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    } else {
                        Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show()
                        Log.e("Authentication Error", "User is null")
                    }
                } else {
                    val errorMessage = task.exception?.message
                    Toast.makeText(this, "Registration failed. Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    Log.e("Authentication Error", "Registration failed. Error: $errorMessage")
                }
            }
        })
    }
    private fun isStrongPassword(password: String): String? {
        if (password.length < 8)
            return "Password should be at least 8 characters long."
        if (!password.matches(Regex(".*[a-z].*")))
            return "Password should contain at least one lowercase letter."
        if (!password.matches(Regex(".*[A-Z].*")))
            return "Password should contain at least one uppercase letter."
        if (!password.matches(Regex(".*\\d.*")))
            return "Password should contain at least one digit."
        return null
    }
}