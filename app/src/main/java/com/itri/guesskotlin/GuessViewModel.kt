package com.itri.guesskotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GuessViewModel : ViewModel() {
    private var count = 0
    private var secret = 0
    var counter = MutableLiveData<Int>()
    var result = MutableLiveData<GameResult>()
    init {
        /*counter.value = 0
        secret = Random().nextInt(10) +  1*/
        replay()
    }

    fun guess(num : Int){
        val n = num ?: 0
        count++
        counter.value = count
        val gameResult = when(n - secret){
            0 -> GameResult.BINGO
            in 1..Int.MAX_VALUE -> GameResult.SMALLER
            else -> GameResult.BIGGER
        }
        result.value = gameResult
    }

    fun replay(){
        secret = Random().nextInt(10) + 1
        count = 0
        counter.value = count
    }
}

enum class GameResult{
    BIGGER, SMALLER, BINGO
}

