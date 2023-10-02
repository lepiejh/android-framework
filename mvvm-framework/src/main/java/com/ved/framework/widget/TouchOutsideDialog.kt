package com.ved.framework.widget

import android.app.Dialog
import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 *  点击阴影部分可取消的对话框
 */
open class TouchOutsideDialog : Dialog {
    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isOutOfBounds(context, event)) {
            dismiss()
        }
        return super.onTouchEvent(event)
    }

    private fun isOutOfBounds(context: Context, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        window?.let {
            it.decorView.let { decorView ->
                return (x < -slop || y < -slop || x > decorView.width + slop
                        || y > decorView.height + slop)
            }
        }
        return false
    }
}