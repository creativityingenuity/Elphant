package com.gxs.elphant.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.gxs.elphant.R;
import com.gxs.elphant.util.WindowUtils;

import java.util.List;

/**
 * 自定义gridlayout用于拖拽标签
 * Call:vipggxs@163.com
 * Created by YT on 2016/12/22.
 */

public class ChannelGridLayout extends GridLayout {

    private int screenWidth;
    private boolean isDrag;
    private Rect[] rects;
    private View currentItem;

    public ChannelGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChannelGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ChannelGridLayout(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        //设置布局动画
        this.setLayoutTransition(new LayoutTransition());
        screenWidth = WindowUtils.getWindowSize(getContext()).widthPixels;
    }

    /**
     * 设置Item数据
     *
     * @param data
     */
    public void setItemData(List<String> data) {
        for (String s : data) {
            addItem(s);
        }
    }

    /**
     * 往gridlayout中添加TextView数据
     *
     * @param s
     */
    public void addItem(String s) {
        final Button tv = (Button) View.inflate(getContext(), R.layout.channel_tv,null);
        tv.setText(s);
        tv.setWidth(screenWidth / this.getColumnCount());
        this.addView(tv);

        //给每个条目设置点击事件
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(tv);
                }
                XLog.i("setOnClickListener");
            }
        });
        //设置长按事件 实现拖拽效果
        tv.setOnLongClickListener(isDrag?onLongClickListener:null);
    }
    //设置拖拽监听
    private OnDragListener dragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    //以item为蓝本创建矩形
                    createRect();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    int location = getLocation(event);
                    if(location>-1&&currentItem!=null&&currentItem!=ChannelGridLayout.this.getChildAt(location)){
                        ChannelGridLayout.this.removeView(currentItem);
                        ChannelGridLayout.this.addView(currentItem,location);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    };

    /**
     * 判断item移动的坐标是否在对应的矩形中
     * 返回矩形所代表得item的位置
     * @param event
     */
    private int getLocation(DragEvent event) {
        for (int i = 0; i < rects.length; i++) {
            if(rects[i].contains((int)event.getX(),(int)event.getY())){
                return i;
            }
        }
        return -1;
    }

    /**
     * 以item为蓝本创建矩形，得到其坐标
     */
    private void createRect() {
        int count = getChildCount();
        rects = new Rect[count];
        View child;
        for (int i = 0; i < count; i++) {
             child = getChildAt(i);
            rects[i] = new Rect(
                    child.getLeft(),
                    child.getTop(),
                    child.getRight(),
                    child.getBottom()
            );

        }

    }
    public void setIsDrag(boolean is){
        isDrag = is;
        this.setOnDragListener(isDrag?dragListener:null);
    }
    //给条目设置长按事件
    private OnLongClickListener onLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            /**
             * 开始拖拽
             *  1.拖拽所需要的数据，
             *  2.拖拽的阴影的效果
             *  3.拖拽的控件的状态
             *  4.拖拽控件执行的事件
             */
            v.startDrag(null,new DragShadowBuilder(v),null,0);
            currentItem = v;
            XLog.i("onLongClickListener");
            return true;
        }
    };

    private OnItemClickListener listener;

    /**
     * 设置条目点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(TextView tv);
    }
}
