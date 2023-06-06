package com.example.coursemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewStudyPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_study_plan)
        val nodeRecyclerView: RecyclerView = findViewById(R.id.nodeRecyclerView)
//        val adapter = NodeAdapter(nodes)

        nodeRecyclerView.layoutManager = LinearLayoutManager(this)
//        nodeRecyclerView.adapter = adapter
    }
}
class NodeAdapter(private val nodes: List<Node>) :
    RecyclerView.Adapter<NodeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_node, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val node = nodes[position]
        holder.bind(node)
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseIdTextView: TextView = itemView.findViewById(R.id.courseIdTextView)
        private val courseNameTextView: TextView = itemView.findViewById(R.id.courseNameTextView)
        private val completedTextView: TextView = itemView.findViewById(R.id.completedTextView)

        fun bind(node: Node) {
            courseIdTextView.text = node.courseId
            courseNameTextView.text = node.courseName
//            completedTextView.text = if (node.completed) "Course Completed" else "Course Not Completed"
        }
    }
}
