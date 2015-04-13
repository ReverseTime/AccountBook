package com.Michael;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created with IntelliJ IDEA
 * Created by MaDianYong
 * Date: 2015/4/10
 */


public class ArcChartView extends View {
    private MySQLiteOpenHelper dbHelper;
    private int[] data;


    //构造方法
    public ArcChartView(Context context) {
        this(context, null);
    }

    public ArcChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private Paint arcPaint;

    //弧线的尺寸
    private RectF arcRect;

    //通用的初始化方法
    private void init(Context context, AttributeSet attrs) {
        arcPaint = new Paint();
        arcPaint.setColor(Color.BLACK);

        arcPaint.setStyle(Paint.Style.FILL);
        //反锯齿
        arcPaint.setAntiAlias(true);

        arcRect = new RectF(100, 100, 400, 400);


        //获取数据
        dbHelper = new MySQLiteOpenHelper(context);

    }

    public void setData(int[] data) {
        this.data = data;
        if (data != null) {
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画半圆
        arcPaint.setColor(Color.BLACK);
        canvas.drawArc(arcRect, 0, 360, true, arcPaint);

        //画蓝色的半圆
        //  arcPaint.setColor(Color.parseColor("#FF0E6AB8"));
        //   canvas.drawArc(arcRect, gongzi, 360-gongzi, true, arcPaint);


        arcPaint.setColor(Color.BLACK);
        canvas.drawCircle(250, 250, 70, arcPaint);

        if (data != null) {

            float a = data[0];
            float b = data[1];

            arcPaint.setColor(Color.RED);
            canvas.drawArc(arcRect, 0, a / (a + b) * 360, true, arcPaint);

            arcPaint.setColor(Color.BLUE);
            canvas.drawArc(arcRect, a / (a + b) * 360, 360 - (a / (a + b) * 360), true, arcPaint);
        }
    }
}
