package com.itri.guesskotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val functions = arrayListOf<String>(
            "Camera",
            "Guess game",
            "Record list",
            "Download coupons",
            "News",
            "Snooker",
            "Maps")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //json read
        Thread{
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
//            Log.d(TAG, "onCreate: ${data}")
//            println(data)
            val array = JSONArray(data)
            val lists = Gson().fromJson(data, EventResult::class.java)
            lists.forEach{
                print(it.ID)
            }
        }.start()


        //recycler

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()

    }

    inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            //inner class style
            val view = layoutInflater.inflate(R.layout.row_function, parent, false)
            // out class style
//            val view = LayoutInflater.from(this@MainActivity)
//                    .inflate(R.layout.row_function, parent, false)
            return FunctionHolder(view)

        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.textView.text = functions.get(position)
            holder.itemView.setOnClickListener {
                FunctionClicked(position)
            }
        }

        override fun getItemCount(): Int {
            return functions.size
        }

    }

    private fun FunctionClicked(position: Int) {
        when (position){
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            5 -> startActivity(Intent(this, SnookerActivity::class.java))
            else-> return
        }
    }

    class FunctionHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView : TextView = view.name
    }
}