package com.example.dwsj.view;
/*
Created by xiaoyu on 2020/11/20

Describe:定义刷新布局的规则，进行反事件拦截，由于布局嵌套过多，会导致界面滑动时被拦截，
现设定规则为滑动到底部的时候要求不再拦截，允许界面往上滑动到顶部，到达顶部后不再响应滑动事件，让父类得刷新控件响应。

*/


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public static boolean DEBUG = false;
    private int touchSlop;
    private boolean isScrolledToTop;//可否这样设想，进来不操作就是在顶部，而不是在底部，赋值顶部true 底部false
    private boolean isScrolledToBottom;

    public MyScrollView(Context context) {
        super(context);
        getTouchSlop(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getTouchSlop(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTouchSlop(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getTouchSlop(context);
    }


    private void getTouchSlop(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
    }


    //反事件拦截

    float startX;
    float startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //  Log.d("myscroll", "onInterceptTouchEvent: action " + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                startX = ev.getX();
//                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                //Log.d("myscroll", "down,不拦截");

                break;
            case MotionEvent.ACTION_MOVE:

                float endX = ev.getX();
                float endY = ev.getY();
                View view = getChildAt(0);
                int measuredHeight = view.getMeasuredHeight();
                int viewHeight = view.getHeight();
                int distanceY = (int) Math.abs(endY - startY);
                if (DEBUG) {
                    Log.d("myscroll",
                            "measuredHeight: " + measuredHeight +
                                    "  getScrollY: " + getScrollY() +
                                    "getHeight:" + getHeight() +
                                    "  viewHeight:" + viewHeight);
                }

                if (distanceY > touchSlop) {
                     //顶部就不允许滑动了，要求父类响应下拉刷新事件
                    if (getScrollY() == 0) {
                        Log.d("myscroll", "onTouchEvent: 到顶了");
                        isScrolledToTop = true;
                        //isScrolledToBottom = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                        //到达底部了需要允许向上滑动
                    } else if (view != null && getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() >= viewHeight) {
                        isScrolledToBottom = true;
                        isScrolledToTop = false;
                           getParent().requestDisallowInterceptTouchEvent(true);
                        Log.d("myscroll", "onTouchEvent: 到底部了");
                    } else {
                        isScrolledToTop = false;
                        isScrolledToBottom = false;
                        getParent().requestDisallowInterceptTouchEvent(true);//滑动在中间的时候要求父类别拦截了
                    }

                    // notifyScrollChangedListeners();//有功能需求了再使用，暂时用不到

                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        View view = getChildAt(0);
//        int measuredHeight = view.getMeasuredHeight();
//        int viewHeight = view.getHeight();
//        if (getScrollY() == 0) {
//            Log.d("myscroll", "onScrollChanged: 到顶部了");
//        } else if (view != null && getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == view.getHeight()) {
//            Log.d("myscroll", "onScrollChanged: 滑动到底部了");
//            getParent().requestDisallowInterceptTouchEvent(true);
//
//        }

    }

    ISmartScrollChangedListener mSmartScrollChangedListener;

    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }
}
