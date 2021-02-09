package com.qgsstrive.sciencemap.xining;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qgsstrive.sciencemap.AssetsDatabaseManager;
import com.qgsstrive.sciencemap.GPSAsynTask;
import com.qgsstrive.sciencemap.GPSUser;

import java.util.List;

/**
 * @date 2021/2/3 13:48
 */
public class XiNingMap extends View {

    private Paint mPaint, paint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;
    private Integer n;
    int j;

    public XiNingMap(Context context) {
        this(context, null);
    }

    public XiNingMap(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public XiNingMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

        OneAsynTask aaAsynTask = new OneAsynTask(getContext());
        AssetsDatabaseManager db = AssetsDatabaseManager.getManager();
        SQLiteDatabase mDatabase = db.getDatabase("xining.db");
        List<GPSUser> wordInfo = aaAsynTask.getWordInfo(mDatabase);
        int size = wordInfo.size();
        Log.e("QGS", size + "");
        for (int i = 0; i < size; i++) {
            String lat = wordInfo.get(i).getLat();
            String lon = wordInfo.get(i).getLon();
            String a = lat.substring(lat.length() - 5, lat.length());
            String b = lon.substring(lon.length() - 5, lon.length());
            Log.e("QGS",a+"  "+b);
            float value1 = Float.valueOf(a);
            float value2 = Float.valueOf(b);
            if (i != size - 1) {
                String mLat = wordInfo.get(i + 1).getLat();
                String mLon = wordInfo.get(i + 1).getLon();
                String ma = mLat.substring(mLat.length() - 5, mLat.length());
                String mb = mLon.substring(mLon.length() - 5, mLon.length());
                float mvalue1 = Float.valueOf(ma);
                float mvalue2 = Float.valueOf(mb);

                String lat1 = wordInfo.get(size - 1).getLat();
                String lon1 = wordInfo.get(size - 1).getLon();
                String a1 = lat1.substring(lat1.length() - 5, lat1.length());
                String b1 = lon1.substring(lon1.length() - 5, lon1.length());
                float lat11 = Float.valueOf(a1);
                float lon11 = Float.valueOf(b1);
                canvas.drawLine(value2 / 70, value1 / 120, mvalue2 / 70, mvalue1 / 120, mPaint);
                //canvas.drawLine(value1 / 70, value2 / 120, mvalue1 / 70, mvalue2 / 120, mPaint);
            }
        }
    }
}
