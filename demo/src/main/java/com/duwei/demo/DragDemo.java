package com.duwei.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 杜伟 on 2016/4/22.
 */
public class DragDemo extends View {

    /**两个圆的半径*/
    private float mRadius;

    /**第一个圆的圆心坐标*/
    private float x1;
    private float y1;


    /**第二个圆的圆心坐标*/
    private float x2;
    private float y2;


    /**画笔和路径*/
    private Paint mCirclePaint;
    private Paint mBezierPaint;
    private Path mPath1;
    private Path mPath2;

    /**控制点==两个圆心连线的中点*/
    private float mControlX;
    private float mControlY;

    /**圆边缘的四个点*/
    private float a1;
    private float b1;

    private float a2;
    private float b2;

    private float a3;
    private float b3;

    private float a4;
    private float b4;


    public DragDemo(Context context) {
        super(context);
        init();
    }

    private void init() {
        /**坐标先写死吧*/
        x1 = 200;
        y1 = 200;
        x2 = 200;
        y2 = 200;
        mRadius = 200;

        /**初始化圆画笔*/
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(15);
        mCirclePaint.setStyle(Paint.Style.FILL);
        /**线的画笔*/
        mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPaint.setColor(Color.RED);
        mBezierPaint.setStrokeWidth(5);
        mBezierPaint.setStyle(Paint.Style.STROKE);


        /**初始化路径*/
        mPath1 = new Path();
        mPath2 = new Path();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(x1, y1,mRadius, mCirclePaint);
        canvas.drawCircle(x2, y2,mRadius, mCirclePaint);

        canvas.drawCircle(mControlX,mControlY,10,mCirclePaint);

        canvas.drawPath(mPath1,mBezierPaint);
        canvas.drawPath(mPath2,mBezierPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                x2 = event.getX();
                y2 = event.getY();

                //计算控制点
//                mControlX = Math.abs(x2 - x1)/2+ x1;
//                mControlY = Math.abs(y2 - x2)/2+y1;
                mControlX = (x1+x2)/2;
                mControlY =(y1+y2)/2;

                break;

            case MotionEvent.ACTION_MOVE:
                float distance = (float) Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));//勾股定理求斜边
                mRadius = -distance/5+200;//-distance/变化率+初始值

                mPath1.reset();
                mPath2.reset();

                //移动后第二个圆的圆心坐标
                x2 = event.getX();
                y2 = event.getY();

                //计算控制点
//                mControlX = Math.abs(x2 - x1)/2+ x1;
//                mControlY = Math.abs(y2 - x2)/2+ y1;
                mControlX = (x1+x2)/2;
                mControlY =(y1+y2)/2;

                //画第一条贝塞尔曲线
                a1 = x1+deltX();
                b1 = y1-deltY();
                mPath1.moveTo(a1,b1);
                a3 = x2+deltX();
                b3 = y2-deltY();
                mPath1.quadTo(mControlX,mControlY,a3,b3);

                //画第二条贝塞尔曲线
                a2 = x1-deltX();
                b2 = y1+deltY();
                mPath2.moveTo(a2,b2);
                a4 = x2-deltX();
                b4 = y2+deltY();
                mPath2.quadTo(mControlX,mControlY,a4,b4);

                break;

        }
        invalidate();//刷新
        return true;
    }


    private float deltX(){
       // return (float)Math.sin(Math.atan(x2-x1/y2-y1))*mRadius;//开始用这个公式也是醉了
        return (float)Math.sin(Math.atan(y2-y1/x2-x1))*mRadius;
    }

    private float deltY(){
        return (float)Math.cos(Math.atan(y2-y1/x2-x1))*mRadius;
        //return (float)Math.cos(Math.atan(x2-x1/y2-y1))*mRadius;
    }
}
