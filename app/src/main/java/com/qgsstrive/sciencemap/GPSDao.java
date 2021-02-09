package com.qgsstrive.sciencemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/2/3 9:01
 */
public class GPSDao {

    private MyOpenHelper helper;

    public GPSDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    /**
     * 数据库的增加方法
     */
    public boolean add(String lat, String lon) {
        SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{NULL,phone});

        /**
         * table 表明
         * nullColumnHack  null
         */
        ContentValues values = new ContentValues();  //实际就是一个map    key:对应我们表的列名   value :值
        values.put("lat", lat);
        values.put("lon", lon);
        //实际底层原理 就是在组拼sql语句
        long result = db.insert("GPS", null, values);
        db.close();
        if (result == -1) {
            //说明插入失败
            return false;

        } else {
            return true;
        }

    }

    /**
     * 数据库的删除方法
     */
    public int del(String name){
        SQLiteDatabase db = helper.getReadableDatabase();
//		db.execSQL("delete from info where name=?", new Object[]{name});

        /**
         * table 表名
         * whereClause  删除条件
         */
        //代表 影响了多少行
        int delete = db.delete("GPS", "name=?", new String[]{name});
        db.close();
        return delete;

    }

    /**
     * 数据库的查询方法
     */
    public List<GPSUser> find() {
        List<GPSUser> personLists = new ArrayList<GPSUser>();
        SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.rawQuery("select * from info", null);

        /**
         * table 表名
         * columns  查询的列  具体查询的是哪一列
         * selection 根据什么条件去
         * selectionArgs
         */
//		Cursor cursor = db.query("info", new String[]{"phone"}, "name=?", new String[]{name}, null, null, null);

        Cursor cursor = db.query("GPS", null, null, null, null, null, null);
        //对cursor 判断一下cursor
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // 开始取 字段数据   sqliet 不区分数据类型  想要什么你就自己拿什么
                int anInt = cursor.getInt(0);
                String lat = cursor.getString(1);   //获取我们的phone
                String lon = cursor.getString(2);    //获取我们的name值
                GPSUser person = new GPSUser();

                System.out.println("name--" + lat + "time--" + lon);
                person.setId(anInt);
                person.setLat(lat);
                person.setLon(lon);
                // 把Person对象 加入到 personLists集合中.
                personLists.add(person);
            }
            cursor.close();
            db.close();
        }
        return personLists;
    }
}
