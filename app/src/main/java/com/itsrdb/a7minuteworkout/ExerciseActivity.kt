package com.itsrdb.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0    //progress left

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0    //progress left

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

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

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
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
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        val llRest = findViewById<LinearLayout>(R.id.llRestView)
        val llExercise = findViewById<LinearLayout>(R.id.llExerciseView)
        llRest.visibility = View.GONE
        llExercise.visibility = View.VISIBLE
        val progressBarEx = findViewById<ProgressBar>(R.id.progressBarExercise)
        val tvTimerEx = findViewById<TextView>(R.id.tvExerciseTimer)
        progressBarEx.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) { //Every single tick
                exerciseProgress++
                progressBarEx.progress = 30-exerciseProgress
                tvTimerEx.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity, "Finished", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this@ExerciseActivity, "Moving ahead", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

    private fun setupRestView(){
        val llRest = findViewById<LinearLayout>(R.id.llRestView)
        val llExercise = findViewById<LinearLayout>(R.id.llExerciseView)
        llExercise.visibility = View.GONE
        llRest.visibility = View.VISIBLE

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        val tvUpcoming = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        tvUpcoming.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()

        val ivImage = findViewById<ImageView>(R.id.ivImage)
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        val tvExercise = findViewById<TextView>(R.id.tvExerciseName)
        tvExercise.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Language not supported")
            }
        }else{
            Log.e("TTS", "Initialization failed")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}