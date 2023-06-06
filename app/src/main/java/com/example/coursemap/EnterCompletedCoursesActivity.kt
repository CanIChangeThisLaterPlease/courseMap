package com.example.coursemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class EnterCompletedCoursesActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var graph: Graph
    private val userId = UserManager.getCurrentUser()
    lateinit var spinner: Spinner
    lateinit var finish: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_completed_courses)
        db = FirebaseFirestore.getInstance()
        spinner = findViewById(R.id.spinner)
        val userRef = userId?.let { db.collection("users").document(it.uid) }

        userRef?.get()?.addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val userData = document.data
                if (userData?.get("graph") != null) {
                    val graphData = userData["graph"] as Map<*, *>
                    val nodesData = graphData["nodes"] as List<Map<String, Any>>
                    val edgesData = graphData["edges"] as List<Map<String, Any>>

                    val nodeList = nodesData.map { nodeData ->
                        val courseId = nodeData["courseId"] as String
                        val courseName = nodeData["courseName"] as String
                        val isCompleted = nodeData["isCompleted"] as Boolean? ?: false
                        Node(courseId, courseName, isCompleted)
                    }

                    val incompleteNodes = nodeList.filter { !it.complete }
                    val courseNames = incompleteNodes.map { it.courseName }

                    // Update the spinner adapter with the course names
                    val adapter = ArrayAdapter(
                        this@EnterCompletedCoursesActivity,
                        android.R.layout.simple_spinner_item,
                        courseNames
                    )
                    spinner.adapter = adapter
                } else {
                    Toast.makeText(this, "User has no graph", Toast.LENGTH_SHORT).show()
                }
            }
        }?.addOnFailureListener {
            Toast.makeText(this, "Data retrieval failed", Toast.LENGTH_SHORT).show()
        }

        finish = findViewById(R.id.buttonSubmit)
        finish.setOnClickListener {
            val selectedCourseName = spinner.selectedItem as String

            // Retrieve the user's document reference
            val userRef = userId?.let { db.collection("users").document(it.uid) }

            // Get the user document and update the graph data
            userRef?.get()?.addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userData = document.data
                    if (userData?.get("graph") != null) {
                        val graphData = userData["graph"] as Map<String, Any>
                        val nodesData = graphData["nodes"] as List<Map<String, Any>>

                        val updatedNodesData = nodesData.map { nodeData ->
                            val courseId = nodeData["courseId"] as String
                            val courseName = nodeData["courseName"] as String
                            val isCompleted =
                                if (courseName == selectedCourseName) true else nodeData["isCompleted"] as Boolean
                            mapOf(
                                "courseId" to courseId,
                                "courseName" to courseName,
                                "isCompleted" to isCompleted
                            )
                        }

                        // Update the graph data
                        val updatedGraphData = mapOf("nodes" to updatedNodesData)
                        userRef.update("graph", updatedGraphData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Graph updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to update graph", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    } else {
                        Toast.makeText(this, "User has no graph", Toast.LENGTH_SHORT).show()
                    }
                }
            }?.addOnFailureListener {
                Toast.makeText(this, "Data retrieval failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}