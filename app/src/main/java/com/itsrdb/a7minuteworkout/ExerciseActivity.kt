package com.itsrdb.a7minuteworkout

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import org.w3c.dom.Text
import java.lang.Exception
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
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

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
        setupExerciseStatusRecyclerView()
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
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()    //imp to update view
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
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
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

        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setupRestView(){
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        val llRest = findViewById<LinearLayout>(R.id.llRestView)
        val llExercise = findViewById<LinearLayout>(R.id.llExerciseView)
        llRest.visibility = View.VISIBLE
        llExercise.visibility = View.GONE

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        //val tvUpcoming = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition+1].getName()
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

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
        false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }
}