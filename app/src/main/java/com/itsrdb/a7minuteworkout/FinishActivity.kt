package com.itsrdb.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)   //back button
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }


    }

    private fun addDateToDatabase() {
        val calender = Calendar.getInstance()
        val dateTime = calender.time
        Log.i("DATE", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime) //good enough conversion

        val dbHander = SqliteOpenHelper(this, null)
        dbHander.addDate(date)
        Log.i("date", "" + date)
    }


}