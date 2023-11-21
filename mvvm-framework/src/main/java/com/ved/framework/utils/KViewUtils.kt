package com.ved.framework.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object KViewUtils {
    /**
     * 是否滑动到顶部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2Top(recyclerView: RecyclerView): Boolean = !recyclerView.canScrollVertically(-1)

    /**
     * 是否滑动到底部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2End(recyclerView: RecyclerView): Boolean = !recyclerView.canScrollVertically(1)


    /**
     * 判断第一个item或者第二个item是否可见
     * */

    fun fristAndSecondIsVisibilly(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
            // 获取第一个完全可见项的位置
            val firstCompletelyVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()

            // 获取第二个完全可见项的位置
            val secondCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            // 判断第一个和第二个项是否可见
            return firstCompletelyVisibleItemPosition <= 1 || secondCompletelyVisibleItemPosition <= 1

        }

        return false
    }

    /**
     * 滑动到顶部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2Top2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItemPos = linearLayoutManager.findFirstVisibleItemPosition()
            val lastItemPos = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = linearLayoutManager.itemCount
            val lastChild: View = recyclerView.getChildAt(lastItemPos - firstItemPos)
            if (lastItemPos == itemCount - 1 && lastChild.bottom <= recyclerView.measuredHeight) {
                return true
            }
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val firstVisibleItems =
                (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            // 真实Position就是position - firstVisibleItems[0]
            val itemCount = layoutManager.itemCount
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(lastPositions)
            val lastPosition: Int = lastPositions.maxOf { it }
            val lastChild: View? = recyclerView.getChildAt(lastPosition - firstVisibleItems[0])
            if (lastPosition == itemCount - 1 && lastChild != null && lastChild.bottom <= recyclerView.getMeasuredHeight()) {
                return true
            }
        }
        return false
    }

    /**
     * 滑动到底部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2End2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
            return recyclerView.getChildAt(0).y == 0f && firstItem == 0
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val aa = (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            return recyclerView.getChildAt(0).y == 0f && aa[0] == 0
        }
        return false
    }

    /**
     * 是否滑动到边缘
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2VerticalEdge(recyclerView: RecyclerView): Boolean =
        isScroll2End(recyclerView) || isScroll2Top(recyclerView)

    /**
     * 是否滑动到边缘2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2VerticalEdge2(recyclerView: RecyclerView): Boolean =
        isScroll2End2(recyclerView) || isScroll2Top2(recyclerView)

    /**
     * 是否向上滚动
     * @param dy Int
     * @return Boolean
     */
    fun isScrollUp(dy: Int): Boolean = dy < 0

    /**
     * 是否向下滚动
     * @param dy Int
     * @return Boolean
     */
    fun isScrollDown(dy: Int): Boolean = dy > 0

}