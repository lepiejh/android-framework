package com.ved.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 类  描   述:重写GridView,屏蔽滑动，增加自适应高度，解决内容超过当前频幕时用ScrollView滑动时，把GridView列表挤压掉问题
 * 类  名   称:Custom_GridView
 * 所属包名 :com.yd.widget.Custom_GridView
 * 创   建  人:pchp
 * 创建时间 :2016年4月29日 下午2:36:47
 */
public class CGridView extends GridView
{

	public CGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public CGridView(Context context)
	{
		super(context);
	}

	public CGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_MOVE)
		{
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 重写该方法，达到使GridView适应ScrollView的效果
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
