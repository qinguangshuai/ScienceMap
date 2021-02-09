package com.qgsstrive.sciencemap.xining;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import com.qgsstrive.sciencemap.AssetsDatabaseManager;
import com.qgsstrive.sciencemap.GPSUser;
import com.qgsstrive.sciencemap.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/2/3 9:35
 */
public class OneAsynTask extends AsyncTask<String, Void, List<GPSUser>> {
    private static final String TAG = "GetWordInfoAsynTask";
    private static final String EXCEPTION = "exception";
    private Context mContext = null;
    private AssetsDatabaseManager dbManager = null;

    public OneAsynTask(Context context) {
        this.mContext = context;
        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(context);
    }

    @Override
    protected void onPostExecute(List<GPSUser> vocabularyInfos) {
        super.onPostExecute(vocabularyInfos);
        if (ListUtils.isEmpty(vocabularyInfos)){
            Log.d("zsj_getwordinfo","vocabularyInfos is null");
        }else {
            Log.d("zsj_getwordinfo","vocabularyInfos :" + vocabularyInfos.toString());
        }
    }

    @Override
    protected List<GPSUser> doInBackground(String... params) {
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        dbManager = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        SQLiteDatabase db = dbManager.getDatabase("xining.db");
        List<GPSUser> wordList = getWordInfo(db);
        return wordList;
    }

    public List<GPSUser> getWordInfo(SQLiteDatabase db) {
        List<GPSUser> wordList = new ArrayList<GPSUser>();
		/*if (db == null || TextUtils.isEmpty(bookNameStr)) {
			return wordList;
		}*/

        GPSUser info = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from one", new String[0]);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getColumnIndex("id");
                    String lat = cursor.getString(cursor.getColumnIndex("lat"));
                    String lon = cursor.getString(cursor.getColumnIndex("lon"));

                    info = new GPSUser(id,lat,lon);
                    if (info != null) {
                        wordList.add(info);
                    }
                } while (cursor.moveToNext());
            }

        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e) {
            Log.e(TAG, EXCEPTION, e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                //db.close();
            }
        }

        return wordList;
    }
}
