package com.example.coursemap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class HomePage : AppCompatActivity() {
    lateinit var updatePlanCard: CardView
    lateinit var viewPlanCard: CardView
    lateinit var enterCompletedCard: CardView
    lateinit var viewSuitablePlanCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        updatePlanCard = findViewById(R.id.updatePlanCard)
        viewPlanCard = findViewById(R.id.viewPlanCard)
        enterCompletedCard = findViewById(R.id.enterCompletedCard)
        viewSuitablePlanCard = findViewById(R.id.viewSuitablePlanCard)

        updatePlanCard.setOnClickListener {
            val intent = Intent(this, UpdateStudyPlanActivity::class.java)
            startActivity(intent)
        }
        viewPlanCard.setOnClickListener {
            val intent = Intent(this, ViewStudyPlanActivity::class.java)
            startActivity(intent)
        }
        enterCompletedCard.setOnClickListener {
            val intent = Intent(this, EnterCompletedCoursesActivity::class.java)
            startActivity(intent)
        }
        viewSuitablePlanCard.setOnClickListener {
            val intent = Intent(this, ViewStudyPlanActivity::class.java)
            startActivity(intent)
        }
    }
}
