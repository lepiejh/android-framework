package com.ved.framework.utils

import java.util.*

class TimerTaskHeartBeat private constructor(){
    private var timerTask: TimerTask? = null
    private var heartbeatTimer: Timer? = null

    companion object {
        val INSTANCE: TimerTaskHeartBeat by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = TimerTaskHeartBeat()
    }

    fun startTimer(period: Int = 5,callBack: () -> Unit) {
        timerTask?.cancel()
        heartbeatTimer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                callBack.invoke()
            }
        }
        val i = period * 1000L
        heartbeatTimer?.schedule(timerTask, i, i)
    }

    fun stopTimer() {
        if (timerTask != null) {
            timerTask?.cancel()
            timerTask = null
        }
        if (heartbeatTimer != null) {
            heartbeatTimer?.cancel()
            heartbeatTimer = null
        }
    }
}