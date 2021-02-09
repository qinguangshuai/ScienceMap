package com.qgsstrive.sciencemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2020/8/1 13:35
 */
public class CustomMap extends View {

    private Paint mPaint, paint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;
    private Integer n;
    int j;

    public CustomMap(Context context) {
        this(context, null);
    }

    public CustomMap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.train);
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

        GPSAsynTask aaAsynTask = new GPSAsynTask(getContext());
        AssetsDatabaseManager db = AssetsDatabaseManager.getManager();
        SQLiteDatabase mDatabase = db.getDatabase("GPS_tr.db");
        List<GPSUser> wordInfo = aaAsynTask.getWordInfo(mDatabase);
        int size = wordInfo.size();
        for (int i = 0; i < size; i++) {
            String lat = wordInfo.get(i).getLat();
            String lon = wordInfo.get(i).getLon();
            String a = lat.substring(lat.length() - 4, lat.length());
            String b = lon.substring(lon.length() - 4, lon.length());
            //Log.e("QGS",a+"  "+b);
            float value1 = Float.valueOf(a);
            float value2 = Float.valueOf(b);

            if (i == size - 1) {

                String lat0 = wordInfo.get(0).getLat();
                String lon0 = wordInfo.get(0).getLon();
                String a0 = lat0.substring(lat0.length() - 4, lat0.length());
                String b0 = lon0.substring(lon0.length() - 4, lon0.length());
                //Log.e("QGS",a+"  "+b);
                float qvalue1 = Float.valueOf(a0);
                float qvalue2 = Float.valueOf(b0);

                String lat00 = wordInfo.get(size - 1).getLat();
                String lon00 = wordInfo.get(size - 1).getLon();
                String a00 = lat00.substring(lat00.length() - 4, lat00.length());
                String b00 = lon00.substring(lon00.length() - 4, lon00.length());
                float qgvalue1 = Float.valueOf(a0);
                float qgvalue2 = Float.valueOf(b0);

                canvas.drawLine(qgvalue1 / 6, qgvalue2 / 9, qvalue1 / 6, qvalue2 / 9, mPaint);
            } else {
                String mLat = wordInfo.get(i + 1).getLat();
                String mLon = wordInfo.get(i + 1).getLon();
                String ma = mLat.substring(mLat.length() - 4, mLat.length());
                String mb = mLon.substring(mLon.length() - 4, mLon.length());
                float mvalue1 = Float.valueOf(ma);
                float mvalue2 = Float.valueOf(mb);

                String lat1 = wordInfo.get(size - 1).getLat();
                String lon1 = wordInfo.get(size - 1).getLon();
                String a1 = lat1.substring(lat1.length() - 4, lat1.length());
                String b1 = lon1.substring(lon1.length() - 4, lon1.length());
                float lat11 = Float.valueOf(a1);
                float lon11 = Float.valueOf(b1);
                canvas.drawLine(value1 / 6, value2 / 9, mvalue1 / 6, mvalue2 / 9, mPaint);
            }
        }
    }
}
