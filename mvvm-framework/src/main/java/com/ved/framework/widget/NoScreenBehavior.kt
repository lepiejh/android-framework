package com.ved.framework.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class NoScreenBehavior(context: Context?, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {
    var isCanMove = true

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        return if (!isCanMove && ev.action == MotionEvent.ACTION_DOWN) {
            false
        } else super.onInterceptTouchEvent(parent, child, ev)
    }
}