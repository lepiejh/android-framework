package com.ved.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ved.framework.bus.RxBus

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_01)
        btn.setOnClickListener {
            RxBus.getDefault().post("我是主页面带来的数据")
            startActivity(Intent(this,FirstActivity::class.java))
        }
    }
}