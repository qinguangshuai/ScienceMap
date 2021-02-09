package com.qgsstrive.sciencemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private DrawMap mDrawMap;
    private Integer n;
    List list = new ArrayList();
    int size = list.size();

    int j;
    private Object[][] mListgpspt;
    private ArrayList<ArrayList<Point3d>> mArrayLists = new ArrayList<ArrayList<Point3d>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setSystemUIVisible(false);
        mDrawMap = findViewById(R.id.drawmap);
        String titleCount = ConfigMgr.getInstance(this).readProperties("0", "Count");
        n = Integer.valueOf(titleCount);
        size = n;
        j = n - 1;
        if (n > 0) {
            List<Point3d> listp = new ArrayList<Point3d>();
            for (int j = 0; j < n; j++) {
                String s = ConfigMgr.getInstance(this).readProperties("0", j + "");
                String[] str = s.split(",");
                if (str.length >= 2) {
                    Point3d p = new Point3d();
                    p.X = Double.valueOf(str[1]);
                    p.Y = Double.valueOf(str[0]);
                    listp.add(p);
                }
                mListgpspt = new Object[size][2];
                mArrayLists.add(j, (ArrayList<Point3d>) listp);
                //mArrayLists.get(j).get(0).X = listp.get(j).X;
                //mArrayLists.get(j).get(1).Y = listp.get(j).Y;
                //mListgpspt[j][0] = listp.get(j).X;
                //mListgpspt[j][1] = listp.get(j).Y;
                int s1 = mArrayLists.size();

                Log.e("QGSX", mArrayLists.get(j).get(j).X + "");
                Log.e("QGSY", mArrayLists.get(j).get(j).Y + "");
            }
        }
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gudaoOfGpsPoint = GetGudaoOfGpsPoint(116.4638072,40.133960916666666);
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

        for (i = 0; i < mArrayLists.size(); i++) {
            for (j = 0; j < mArrayLists.get(i).size() - 1; j++) {
                dd = GetGpsDistanceOfPointToLine(p, mArrayLists.get(i).get(j), mArrayLists.get(i).get(j+1));

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

            p1 = (Point3d)listgpspt[gd][0];
            p2 = (Point3d)listgpspt[gd][listgpspt[gd].length - 1];

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
