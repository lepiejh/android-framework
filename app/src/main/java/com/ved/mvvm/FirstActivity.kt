package com.ved.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ved.framework.bus.RxBus
import com.ved.framework.bus.RxSubscriptions
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

class FirstActivity : AppCompatActivity() {
    private var mOrderSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val tv = findViewById<TextView>(R.id.tv_01)

        mOrderSubscription = RxBus.getDefault().toObservable(String::class.java).subscribe({

        },
            {

            })
        RxSubscriptions.add(mOrderSubscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxSubscriptions.remove(mOrderSubscription)
    }
}