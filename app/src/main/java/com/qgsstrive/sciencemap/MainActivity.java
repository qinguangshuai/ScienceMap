package com.qgsstrive.sciencemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.valueOf;

public class MainActivity extends AppCompatActivity {

    private DrawMap mDrawMap;
    private Integer n;
    List list = new ArrayList();
    int size = list.size();
    Double[][] listgpspt = new Double[size/2][2];

    List<Point3d> listp = new ArrayList<Point3d>();
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSystemUIVisible(false);
        mDrawMap = findViewById(R.id.drawmap);
        //ReadGPSPoints();
        //Log.e("list", list.toString());
        String titleCount = ConfigMgr.getInstance(MainActivity.this).readProperties("0", "Count");
        //Log.e("titleCount", titleCount + "");
        n = Integer.valueOf(titleCount);
        size = n;
        j = n - 1;
        if (n > 0) {

            for (int j = 0; j < n; j++) {
                String s = ConfigMgr.getInstance(MainActivity.this).readProperties("0", j + "");
                //Log.e("titleCount", "titleCount:" + s);
                String[] str = s.split(",");
                //Log.e("titleCount", "titleCount:" + str.toString());
                if (str.length >= 2) {
                    Point3d p = new Point3d();
                    //p.X = Convert.ToDouble(str[0]);
                    //p.Y = Convert.ToDouble(str[1]);
                    //p.Z = Convert.ToDouble(str[2]);

                    p.X = valueOf(str[1]);
                    p.Y = valueOf(str[0]);

                    listp.add(p);
                    //Log.e("QGS", listp.size() + "");
                }
                //Log.e("QGS", listp.get(j).X + "");
                //Log.e("QGS", listp.get(j).Y + "");
            }
                //Log.e("QGSQGS",listp.size()+" "+n);
                //Log.e("QGS", listp.get(i).X + "");
                //Log.e("QGS", listp.get(i).Y + "");
                //listgpspt[j][i] = listp.get(x).X;

                //Point3d point3d = listp.get(i);

        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gudaoOfGpsPoint = GetGudaoOfGpsPoint(40.133960916666666,116.4638072);
                Log.e("秦广帅",gudaoOfGpsPoint+"");
            }
        });
    }

    /**
     * 隐藏状态栏和导航栏
     *
     * @param show boolean类型，true:显示  false ：隐藏
     */
    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    @Override
    protected void onResume() {
        //设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    public void ReadGPSPoints() {
        int i, j, n;

        try {
            String titleCount = ConfigMgr.getInstance(MainActivity.this).readProperties("0", "Count");
            //Log.e("titleCount", titleCount + "");
            n = Integer.valueOf(titleCount);
            list.clear();


            if (n > 0) {
                List<Point3d> listp = new ArrayList<Point3d>();

                for (j = 0; j < n; j++) {
                    String s = ConfigMgr.getInstance(MainActivity.this).readProperties("0", j + "");
                    //Log.e("titleCount", "titleCount:" + s);
                    String[] str = s.split(",");
                    //Log.e("titleCount", "titleCount:" + str[j]);
                    if (str.length >= 2) {
                        Point3d p = new Point3d();
                        //p.X = Convert.ToDouble(str[0]);
                        //p.Y = Convert.ToDouble(str[1]);
                        //p.Z = Convert.ToDouble(str[2]);

                        p.X = valueOf(str[1]);
                        p.Y = valueOf(str[0]);

                        listp.add(p);
                    }
                    //listgpspt[j][j] = listp.get(j);
                    //Log.e("QGS,qgs,list",listp.get(j)+"");
                }
                //listgpspt.add(listp);
            }
        } catch (Exception ex) {
        }
    }


    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    /**
     * 根据两点计算方向角度
     *
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @return
     */
    private float CalulateXYAnagle(double startx, double starty, double endx,
                                   double endy) {
        float tan = (float) (Math.atan(Math.abs((endy - starty)
                / (endx - startx))) * 180 / Math.PI);
        if (endx > startx && endy > starty)// 第一象限
        {
            return -tan;
        } else if (endx > startx && endy < starty)// 第二象限
        {
            return tan;
        } else if (endx < startx && endy > starty)// 第三象限
        {
            return tan - 180;
        } else {
            return 180 - tan;
        }


    }

    private double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
        double d = 0;
        lat_a = lat_a * Math.PI / 180;
        lng_a = lng_a * Math.PI / 180;
        lat_b = lat_b * Math.PI / 180;
        lng_b = lng_b * Math.PI / 180;
        d = Math.sin(lat_a) * Math.sin(lat_b) + Math.cos(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
        d = Math.sqrt(1 - d * d);
        d = Math.cos(lat_b) * Math.sin(lng_b - lng_a) / d;
        d = Math.asin(d) * 180 / Math.PI;
        //d = Math.round(d*10000);
        return d;
    }


    public double DistanceOfPointToLine(double x0, double y0, double x1, double y1, double x2, double y2) {
        double jl = 0.0, A = 0.0, B = 0.0, C = 0.0, d = 0.0, d1 = 0.0, d2 = 0.0, x = 0.0, y = 0.0;
        A = y2 - y1;
        B = x1 - x2;
        C = x2 * y1 - x1 * y2;
        x = (B * B * x0 - A * B * y0 - A * C) / (A * A + B * B);         //垂足坐标
        y = (A * A * y0 - A * B * x0 - B * C) / (A * A + B * B);         //垂足坐标

        d = Math.abs((A * x0 + B * y0 + C) / Math.sqrt(A * A + B * B));  //点到直线距离
        d1 = Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));   //点到点距离
        d2 = Math.sqrt((x0 - x2) * (x0 - x2) + (y0 - y2) * (y0 - y2));   //点到点距离

        if ((x <= Math.max(x1, x2)) && (x >= Math.min(x1, x2))) {
            jl = d;
        } else {
            jl = Math.min(d1, d2);
        }
        return jl;
    }


    public double GetGpsDistanceOfPointToLine(Point3d p, Point3d p1, Point3d p2) {
        double d = 0.0;

        double c, x1, y1, x2, y2;
        double lat;    //纬度
        double lon;    //经度
        double R = 6371393.0;   //平均半径  数据来源:百度百科

        //平均纬度
        c = (p.X + p1.X + p2.X) / 3.0;

        //经纬度1度对应距离
        lat = 2 * Math.PI * R / 360.0;
        lon = 2 * Math.PI * R * Math.cos(c * Math.PI / 180.0) / 360.0;

        //以米为单位的新坐标
        x1 = (p1.X - p.X) * lat;
        y1 = (p1.Y - p.Y) * lon;
        x2 = (p2.X - p.X) * lat;
        y2 = (p2.Y - p.Y) * lon;

        d = DistanceOfPointToLine(0d, 0d, x1, y1, x2, y2);

        return d;
    }


    public int GetGudaoOfGpsPoint(double x, double y) {
        int i, j, n;
        double dis = 5.0, dd = 0d;
        n = -1;

        Point3d p = new Point3d();
        p.X = x;
        p.Y = y;
        p.Z = 0d;
        //Log.e("秦广帅",p.X+"");
        //Log.e("秦广帅",p.Y+"");

        for (i = 0; i < listgpspt.length; i++) {
            for (j = 0; j < listgpspt[i].length - 1; j++) {
                dd = GetGpsDistanceOfPointToLine(p,listp.get(j), listp.get(j+1));
                Log.e("秦广帅",dd+"");
                if (dd < dis) {
                    dis = dd;

                    n = i + 5;                              //GPS股道从9开始，UWB股道从4开始
                }
            }

            //rrzz = "点到股道距离:     gudao: " + i.ToString() + "     " + dis.ToString();
            //mytool.rizhi(rrzz);
        }

        if (dis > 5.0) {
            n = -1;
        }

        return n;
    }

    /*public double GetRatioOfGpsPoint(Point3d p, int gd) {
        double r = 0d;

        double A, B, C, x, y, x1, x2;

        if (gd >= 0) {
            Point3d p1 = new Point3d();
            Point3d p2 = new Point3d();

            gd = gd - 5;

            p1 = listgpspt2[gd][0];
            p2 = listgpspt2[gd][listp[gd].length - 1];

            A = p2.Y - p1.Y;
            B = p1.X - p2.X;
            C = p2.X * p1.Y - p1.X * p2.Y;
            x = (B * B * p.X - A * B * p.Y - A * C) / (A * A + B * B);         //垂足坐标
            y = (A * A * p.Y - A * B * p.X - B * C) / (A * A + B * B);         //垂足坐标

            x1 = listgpspt[gd][0].X;
            x2 = listgpspt[gd][listgpspt[gd].length - 1].X;

            //r = (x1 - x) / (x1 - x2);

            r = (x - x2) / (x1 - x2);

            //n = (int)Math.Round(r * 100);
        }

        return r;
    }*/
}
