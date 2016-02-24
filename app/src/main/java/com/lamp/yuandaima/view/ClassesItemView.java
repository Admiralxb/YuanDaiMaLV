package com.lamp.yuandaima.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.lamp.yuandaima.R;

import org.xutils.x;

/**
 * 视频列表的Item 自定义布局
 * Created by baishixin on 16/2/23.
 */
public class ClassesItemView extends LinearLayout {
    private Context mContext;

    public ClassesItemView(Context context) {
        this(context, null);
    }

    public ClassesItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassesItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View.inflate(mContext, R.layout.view_listview_item, null);
        x.view().inject(this);
    }

    /**
     * 初始化自定义View之后设置数据调用
     */
    public void setData() {

    }
}
