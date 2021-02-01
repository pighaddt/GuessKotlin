package com.itri.guesskotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Record::class), version = 1, exportSchema = false)
abstract class GameDatabse : RoomDatabase(){
    abstract fun recordDao() : RecordDao
    companion object{
        private var instance : GameDatabse? = null
        public fun getInstance(context : Context) : GameDatabse{
            if (instance == null){
                instance =
                    Room.databaseBuilder(context, GameDatabse::class.java, "game.db")
                        .build()
            }
            return instance!!
        }
    }
}