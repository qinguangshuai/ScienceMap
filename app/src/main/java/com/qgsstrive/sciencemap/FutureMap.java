package com.qgsstrive.sciencemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @date 2021/1/13 14:36
 */
public class FutureMap extends View {

    private Paint mPaint, paint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;
    int i = 90;

    public FutureMap(Context context) {
        this(context, null);
    }

    public FutureMap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FutureMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#FAEBD6"));

        //创建一个画笔
        mPaint = new Paint(Paint.DITHER_FLAG);
        paint = new Paint(Paint.DITHER_FLAG);
        //设置位图的宽高
        mBitmap = Bitmap.createBitmap(1280, 800, Bitmap.Config.RGB_565);
        //绘制内容保存到Bitmap
        mCanvas1 = new Canvas(mBitmap);
        //设置非填充
        mPaint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        //笔宽5像素
        mPaint.setStrokeWidth(3);
        paint.setStrokeWidth(1);
        paint.setTextSize(18);
        //设置为红笔
        mPaint.setColor(Color.BLACK);
        paint.setColor(Color.RED);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        paint.setAntiAlias(true);
        //设置图像抖动处理
        mPaint.setDither(true);
        paint.setDither(true);
        //设置图像的结合方式
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Log.e("二宝", width + "  :  " + height);

        canvas.drawLine(100, 100, width - 100, 100, mPaint);
        canvas.drawLine(100, 100, 100, height - 100, mPaint);
        canvas.drawLine(width - 100, 100, width - 100, height - 100, mPaint);
        canvas.drawLine(100, height - 100, width - 100, height - 100, mPaint);
    }
}
