package com.itri.guesskotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.itri.guesskotlin.data.GameDatabse
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)
        //room getALl

        // ***AsyncTask version***
        /*AsyncTask.execute {
            val records = GameDatabse.getInstance(this).recordDao().getAll()
            runOnUiThread {
                //recycler
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.setHasFixedSize(true)
                recycler.adapter = RecordAdapter(records)
            }
        }*/

        //***Coroutines version***
        CoroutineScope(Dispatchers.IO).launch {
            val records = GameDatabse.getInstance(this@RecordListActivity).recordDao().getAll()
            recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
            recycler.setHasFixedSize(true)
            recycler.adapter = RecordAdapter(records)

        }
    }


}