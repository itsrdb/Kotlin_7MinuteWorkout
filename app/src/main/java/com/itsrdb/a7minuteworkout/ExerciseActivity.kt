package com.itsrdb.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0    //progress left


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val toolbarExercise = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExercise)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    private fun setRestProgressBar(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) { //Every single tick
                restProgress++
                progressBar.progress = 10-restProgress
                tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "We will start the exercise now", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setupRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
}