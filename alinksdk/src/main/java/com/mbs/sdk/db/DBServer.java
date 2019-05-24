package com.mbs.sdk.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mbs.sdk.db.listener.OnDBInitListener;

/**
 * ahtor: super_link
 * date: 2019/4/22 15:12
 * remark:
 */
public abstract class DBServer implements OnDBInitListener {

    private SQLiteDbHelper dbHelper;
    private SQLiteDatabase db;
    /**
     * 初始化
     * @param context 上下文
     * @param dbName 数据库名称
     * @param versionCode 版本号
     */
    public DBServer(Context context, String dbName, int versionCode) {
        this.dbHelper = new SQLiteDbHelper(context, dbName, versionCode, this);
        db = this.dbHelper.getWritableDatabase();
    }

    /**
     * 缓存数据
     * @param label 标签
     * @param key 键
     * @param value 值
     */
    public void save(String label, String key, String value){

        // 判断对应标签与键是否存在
        String val = get(label, key);

        db.beginTransaction();  //开始事务

        try{
            if(val != null){
                //数据库中存在该值，执行修改操作
                ContentValues cv = new ContentValues();
                cv.put("value" , value);
                cv.put("updateTime", System.currentTimeMillis());
                String where = "label = '" + label + "' AND key= '" + key + "'";

                db.update("table_cache", cv, where, null);
                db.setTransactionSuccessful();

            } else {
                // 数据库中不存在该值，执行新增操作
                ContentValues cv = new ContentValues();
                cv.put("label" , label);
                cv.put("key" , key);
                cv.put("value", value);
                cv.put("updateTime", System.currentTimeMillis());

                db.insert("table_cache",null, cv);
                db.setTransactionSuccessful();
            }
        }finally {
            db.endTransaction();
        }
    }

    /**
     * 获取缓存值
     * @param label 标签
     * @param key 键
     * @return 值
     */
    public String get(String label, String key){

        // 判断对应标签与键是否存在
        Cursor cursor = db.query("table_cache", null, "label = ? AND key = ? ", new String[]{label, key}, null, null, null);

        String value = null;
        while (cursor.moveToNext()){
            value = cursor.getString(cursor.getColumnIndex("value"));
        }

        return value;
    }

    /**
     * 根据标签与键删除值
     * @param label 标签
     * @param key 键
     */
    public void remove(String label, String key){
        db.beginTransaction();

        try {
            String where = "label = " + label + " AND key = " + key;
            db.delete("table_cache", where, null);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    /**
     * 根据标签与键删除值
     * @param label 标签
     */
    public void remove(String label){
        db.beginTransaction();

        try {
            String where = "label = " + label;
            db.delete("table_cache", where, null);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    /**
     * 删除缓存所有数据
     */
    public void removeAll(){
        db.beginTransaction();

        try {
            db.delete("table_cache", null, null);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
}
