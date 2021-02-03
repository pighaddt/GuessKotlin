package com.itri.guesskotlin

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itri.guesskotlin.data.GameDatabse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_material.*


class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD: Int = 100
    private lateinit var viewModel: GuessViewModel
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        viewModel.counter.observe(this, Observer { count->
            counter.text = count.toString()
        })
        AsyncTask.execute {
            val list = GameDatabse.getInstance(this).recordDao().getAll()
            list.forEach {
                Log.d(TAG, "onCreate: ${it.nickname} ${it.counter}")
            }
        }

        viewModel.result.observe(this, Observer { message ->
            AlertDialog.Builder(this)
                    .setTitle("Game Result")
                    .setMessage(message.toString())
                    .setNeutralButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        if (message == GameResult.BINGO) {
                            //room
                            /*Thread() {
                                val record = Record("Angus", 4)

                                GameDatabse.getInstance(this).recordDao().insert(record)
                                Log.d(TAG, "onCreate: ${GameDatabse.getInstance(this).recordDao().getAll()}")
                            }.start()*/

                            //intent
                            val intent = Intent(this, RecordActivity::class.java)
                            intent.putExtra("COUNTER", viewModel.counter.value?.toInt())
                            startActivityForResult(intent, REQUEST_RECORD)
                        }
                    })
                    .show()
        })
        //guess on click
        guess.setOnClickListener {
            if (!guessNumber.text.isEmpty()){
                var number  = guessNumber.text.toString().toInt()
                viewModel.guess(number)
            }else{
                AlertDialog.Builder(this)
                    .setTitle("Warning!!!!")
                    .setMessage("please enter integer number")
                    .setNeutralButton("OK", null)
                    .show()
            }
        }

        //replay on click
        replay.setOnClickListener {
            viewModel.replay()
            Toast.makeText(this, "secret number is changed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD){
            viewModel.replay()
        }
    }
}