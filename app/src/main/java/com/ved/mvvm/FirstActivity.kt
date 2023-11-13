package com.ved.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ved.framework.bus.RxBus
import com.ved.framework.bus.RxSubscriptions
import io.reactivex.rxjava3.disposables.Disposable

class FirstActivity : AppCompatActivity() {
    private var mOrderSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

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