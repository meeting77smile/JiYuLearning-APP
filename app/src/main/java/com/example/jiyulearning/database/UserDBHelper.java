package com.example.jiyulearning.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.jiyulearning.LoginActivity;
import com.example.jiyulearning.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

    private static  final String DB_NAME="jiyu.db";
    //账单信息表
    private static final String TABLE_BILLS_INFO = "user_info";
    private static final int DB_VERSION = 1;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;//用来读的
    private SQLiteDatabase mWDB = null;//用来写的
    private UserDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context){
        if(mHelper == null){
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink(){
        if(mRDB == null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
            Log.i("test", "db_read_open");
        }
        return mRDB;
    }

    //打开数据库的写连接
    public SQLiteDatabase openWriteLink(){
        if(mWDB == null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
            Log.i("test", "db_write_open");
        }
        return mWDB;
    }

    //关闭数据库连接
    public void closeLink(){
        if(mRDB != null && mRDB.isOpen()){
            mRDB.close();
            mRDB = null;
            Log.i("test", "db_read_close");
        }
        if(mWDB != null && mWDB.isOpen()){
            mWDB.close();
            mWDB = null;
            Log.i("test", "db_write_close");
        }
    }
    //创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_BILLS_INFO +"("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " account VARCHAR NOT NULL UNIQUE," +
                " username VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " level INTEGER DEFAULT 1);" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库的版本更新时会执行onUpgrade函数，如DB_VERSION由1变为2时
        //一次只能增加一条字段，故要执行两次
//        String sql = "ALTER TABLE " + TABLE_BILLS_INFO + " ADD COLUMN phone VARCHAR;";
//        db.execSQL(sql);
//        sql = "ALTER TABLE " + TABLE_BILLS_INFO + " ADD COLUMN password VARCHAR;";
//        db.execSQL(sql);
    }

    //用户注册：添加一个新用户
    public long save(UserInfo userInfo){
        ContentValues cv = new ContentValues();
        cv.put("account",userInfo.account);
        cv.put("level",userInfo.level);
        cv.put("username",userInfo.username);
        cv.put("password",userInfo.password);
        return  mWDB.insert(TABLE_BILLS_INFO,null,cv);
    }

    @SuppressLint("Range")
    public UserInfo queryByAccount(String account){//根据账号查询该用户
        UserInfo userInfo = new UserInfo();

        //%是模糊匹配，如用张%可以匹配到张三    而 = 是精准匹配，此时account要用' '包裹起来
        String sql ="select * from "+TABLE_BILLS_INFO + " where account = '" +account + "'";
        Log.d("userInfo",sql);
        Cursor cursor=mRDB.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            userInfo.id = cursor.getInt(cursor.getColumnIndex("_id"));
            userInfo.username = cursor.getString(cursor.getColumnIndex("username"));
            userInfo.password = cursor.getString(cursor.getColumnIndex("password"));
            userInfo.level = cursor.getInt(cursor.getColumnIndex("level"));
        }
        return userInfo;
    }

    //修改方法，返回大于0的值表示修改成功
    public long updata(UserInfo userInfo){
        ContentValues values = new ContentValues();
        values.put("username",userInfo.username);
        values.put("password",userInfo.password);
        values.put("level",userInfo.level);
        return mWDB.update(TABLE_BILLS_INFO,values,"account=?",new String[]{userInfo.account});
    }
    public boolean accountExist(String account){//账号已存在时返回true
        String sql = "select 1 from " + TABLE_BILLS_INFO + " where account = ? LIMIT 1";
        Cursor cursor = mRDB.rawQuery(sql, new String[]{account});
        boolean exists = cursor.moveToFirst();
        return exists;
    }
}
