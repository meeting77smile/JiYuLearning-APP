package com.example.jiyulearning.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jiyulearning.entity.ListInfo;

import java.util.ArrayList;
import java.util.List;

public class ListDBHelper extends SQLiteOpenHelper {

    private static  final String DB_NAME="list.db";
    //账单信息表
    private static final String TABLE_LIST_INFO = "list_info";
    private static final int DB_VERSION = 1;
    private static ListDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;//用来读的
    private SQLiteDatabase mWDB = null;//用来写的
    private ListDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //利用单例模式获取数据库帮助器的唯一实例
    public static ListDBHelper getInstance(Context context){
        if(mHelper == null){
            mHelper = new ListDBHelper(context);
        }
        return mHelper;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink(){
        if(mRDB == null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //打开数据库的写连接
    public SQLiteDatabase openWriteLink(){
        if(mWDB == null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //关闭数据库连接
    public void closeLink(){
        if(mRDB != null && mRDB.isOpen()){
            mRDB.close();
            mRDB = null;
        }
        if(mWDB != null && mWDB.isOpen()){
            mWDB.close();
            mWDB = null;
        }
    }
    //创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_LIST_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " time DOUBLE NOT NULL," +
                " description VARCHAR NOT NULL);" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        //数据库的版本更新时会执行onUpgrade函数，如DB_VERSION由1变为2时
//        //一次只能增加一条字段，故要执行两次
//        String sql = "ALTER TABLE " + TABLE_BILLS_INFO + " ADD COLUMN phone VARCHAR;";
//        db.execSQL(sql);
//        sql = "ALTER TABLE " + TABLE_BILLS_INFO + " ADD COLUMN password VARCHAR;";
//        db.execSQL(sql);
    }

    //保存一条记账记录
    public long save(ListInfo listinfo){
        ContentValues cv = new ContentValues();
        cv.put("date",listinfo.date);
        cv.put("time",listinfo.time);
        cv.put("description",listinfo.description);
        return  mWDB.insert(TABLE_LIST_INFO,null,cv);
    }

    @SuppressLint("Range")
    public List<ListInfo> queryByDay(String monthDay){//根据具体日期查询某一天的所有事项
        List<ListInfo> list = new ArrayList<>();

        //SQL语句：select * from bill_info where date like '2035-09%'
        String sql ="select * from "+TABLE_LIST_INFO + " where date like '"+monthDay+"%'";
        Log.d("list",sql);
        Cursor cursor=mRDB.rawQuery(sql,null);
        while (cursor.moveToNext()){
            ListInfo listInfo = new ListInfo();
            listInfo.id = cursor.getInt(cursor.getColumnIndex("_id"));
            listInfo.date = cursor.getString(cursor.getColumnIndex("date"));
            listInfo.time = cursor.getDouble(cursor.getColumnIndex("time"));
            listInfo.description = cursor.getString(cursor.getColumnIndex("description"));
            list.add(listInfo);
        }
        return list;
    }
public List<ListInfo> queryAll(){//查询所有数据
    List<ListInfo> list = new ArrayList<>();//可能查询到多个同名，故也要用集合保存
    //执行记录查询动作，该语句返回结果集的游标(select为查询条件）
    Cursor cursor = mRDB.query(TABLE_LIST_INFO,null,null,null,null,null,null);
    //循环取出游标指向每条记录
    while (cursor.moveToNext()){
        ListInfo listInfo = new ListInfo();
        listInfo.id = cursor.getInt(0);//0表示其在数据库表格里的第一个位置
        listInfo.date = cursor.getString(1);//1表示其在数据库表格里的第二个位置
        listInfo.time = cursor.getDouble(2);
        listInfo.description = cursor.getString(3);
        list.add(listInfo);
    }
    return list;
}
    public long deleteByDes(String des){
        //删除所有数据:
        //mWDB.delete(TABLE_NAME,"1=1",null);//1肯定=1,故每一行都满足条件，都可以删除
        return mWDB.delete(TABLE_LIST_INFO,"description=?",new String[]{des});//根据name来删除
    }
    public long deleteAll(){//删除所有数据
        //删除所有数据:
        return mWDB.delete(TABLE_LIST_INFO,"1=1",null);//1肯定=1,故每一行都满足条件，都可以删除
    }
    public long updata(ListInfo listInfo){
        ContentValues values = new ContentValues();
        values.put("date",listInfo.date);
        values.put("time",listInfo.time);
        values.put("description",listInfo.description);
        return mWDB.update(TABLE_LIST_INFO,values,"description=?",new String[]{listInfo.description});
    }

    public double getAllTime(String Day){
        List<ListInfo> list = queryByDay(Day);
        double sum = 0.0;
        for(int i=0;i<list.size();i++){
            ListInfo listInfo = list.get(i);
            sum+=listInfo.time;
        }
        return sum;
    }
    public int getListSize(String Day){
        List<ListInfo> list = queryByDay(Day);
        return list.size();
    }
}
