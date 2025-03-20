package com.ved.framework.utils

import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class TimerTaskHeartBeat private constructor(){
    private var timerTask: TimerTask? = null
    private var heartbeatTimer: ScheduledExecutorService? = null

    companion object {
        val INSTANCE: TimerTaskHeartBeat by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = TimerTaskHeartBeat()
    }

    fun startTimer(period: Long = 5,callBack: () -> Unit) {
        heartbeatTimer = Executors.newScheduledThreadPool(1)
        timerTask = object : TimerTask() {
            override fun run() {
                KLog.i("Task executed!")
                try {
                    callBack.invoke()
                } catch (e: Exception) {
                    KLog.e(e.message)
                }
            }
        }
        heartbeatTimer?.scheduleAtFixedRate(timerTask, 0, period, TimeUnit.SECONDS)
    }

    fun stopTimer() {
        if (timerTask != null) {
            timerTask?.cancel()
            timerTask = null
        }
        if (heartbeatTimer != null) {
            heartbeatTimer?.shutdown()
            try {
                // 等待线程池终止
                if (heartbeatTimer?.awaitTermination(1, TimeUnit.SECONDS) == false) {
                    heartbeatTimer?.shutdownNow() // 强制终止
                }
            } catch (e: InterruptedException) {
                KLog.e( "Heartbeat shutdown interrupted :  ${e.message}")
                heartbeatTimer?.shutdownNow()
            }
            heartbeatTimer = null
        }
    }
}