package com.itri.guesskotlin

import android.app.IntentService
import android.content.Intent
import android.util.Log

class CacheService() : IntentService("CacheService") {
    val TAG = CacheService::class.java.simpleName
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: ")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }
}