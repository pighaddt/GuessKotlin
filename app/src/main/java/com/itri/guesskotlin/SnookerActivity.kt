package com.itri.guesskotlin

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

class SnookerActivity : AppCompatActivity() , CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var job: Job
    private val TAG = SnookerActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooker)

        job = Job()
        //MVVM JSON READ
        val viewModel = ViewModelProvider(this).get(SnookerViewModel::class.java)
        viewModel.events.observe(this, Observer {
            Log.d(TAG, "onCreate: ${it.size}")
        })
        
        //json read
        /*launch {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val lists = Gson().fromJson(data, EventResult::class.java)
            lists.forEach {
                Log.d(TAG, "onCreate: ${it.ID}")
            }
        }*/


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}