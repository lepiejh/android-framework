package com.ved.framework.utils

import java.util.*

class TimerTaskHelper private constructor(){
    private var timerTask: TimerTask? = null

    companion object {
        val INSTANCE: TimerTaskHelper by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = TimerTaskHelper()
    }

    fun startTimer(period: Int = 5,callBack: () -> Unit) {
        timerTask?.cancel()
        this.timerTask = object : TimerTask() {
            override fun run() {
                callBack.invoke()
            }
        }
        val i = period * 1000L
        Timer().schedule(timerTask, i, i)
    }

    fun stopTimer() {
        if (timerTask != null) {
            timerTask?.cancel()
            this.timerTask = null
        }
    }
}