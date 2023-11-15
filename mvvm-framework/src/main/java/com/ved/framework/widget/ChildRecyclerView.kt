package com.ved.framework.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.ved.framework.utils.KLog
import kotlin.math.abs

class ChildRecyclerView : RecyclerView {
    private var x1 = 0f
    private var y1 = 0f
    private var isScroll = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                KLog.i("lx_125","ChildRecyclerView MotionEvent.ACTION_DOWN")
                x1 = ev.x
                y1 = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                KLog.i("lx_125","ChildRecyclerView MotionEvent.ACTION_MOVE")
                val x2 = ev.x
                val y2 = ev.y
                if (abs(x1 - x2) > 50) {
                    //左右滑动
                    parent.requestDisallowInterceptTouchEvent(false)
                    isScroll = false
                } else if(abs(y1-y2) > 50){
                    //上下滑动
                    parent.requestDisallowInterceptTouchEvent(true)
                    isScroll = true
                }
                return if (isScroll){
                    false
                }else{
                    super.dispatchTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                KLog.i("lx_125","ChildRecyclerView MotionEvent.ACTION_UP isScroll : $isScroll")
                if (isScroll) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
                return if (isScroll){
                    false
                }else{
                    super.dispatchTouchEvent(ev)
                }
            }
        }
        return false
    }
}