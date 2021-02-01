package com.itri.guesskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {
    companion object{
        private val TAG = RecordActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val n = intent.getIntExtra("COUNTER", -1)
        if (n > 0){
            rec_counter.text = n.toString()
            //save button on click
            rec_save.setOnClickListener {
                val nickname = rec_nickname.text.toString()
                setResult(RESULT_OK)
                Log.d(TAG, "onCreate: " + n +"\t"+ nickname)
                finish()
                //room database
            }
        }

    }
}