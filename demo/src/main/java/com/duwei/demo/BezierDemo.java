package com.duwei.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 杜伟 on 2016/4/22.
 */
public class BezierDemo extends View {
    Path mPath;
    Paint mPaint;

    float downX;
    float downY;

    public BezierDemo(Context context) {
        super(context);
        init();
    }


    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(12);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.moveTo(100,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath,mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:

                mPath.reset();
                downX = event.getX();
                downY = event.getY();
                mPath.quadTo(downX,downY,100,700);
                break;

        }
        invalidate();
        return true;
    }
}
