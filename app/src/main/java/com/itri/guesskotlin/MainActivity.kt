package com.itri.guesskotlin


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_CAMERA: Int = 100
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
        //spinner
        val colors = arrayOf("Red", "Green", "Blue")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "onItemClick: ${colors[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

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
            0 ->{
                val cameraPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                if (cameraPermission == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                }else{
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                }
                takePhoto()
            }
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            4 -> startActivity(Intent(this, NewsActivity::class.java))
            5 -> startActivity(Intent(this, SnookerActivity::class.java))
            else-> return
        }
    }

    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(cameraIntent)
    }

    class FunctionHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView : TextView = view.name
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_cache -> {
                Log.d(TAG, "onOptionsItemSelected: cache selected")
                val cacheIntent = Intent(this, CacheService::class.java)
                startService(cacheIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}