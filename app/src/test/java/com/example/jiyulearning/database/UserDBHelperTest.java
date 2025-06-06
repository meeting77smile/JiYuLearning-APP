package com.example.jiyulearning.database;

import static org.junit.Assert.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jiyulearning.entity.UserInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UserDBHelperTest {
    private static final String TABLE_USER_INFO = "user_info";
    private UserDBHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

    @Before
    public void setUp() {
        // 使用Robolectric创建运行时环境
        Context context = RuntimeEnvironment.getApplication();

        // 获取数据库连接实例
        dbHelper = UserDBHelper.getInstance(context);

        // 为测试特定方法绕开dbHelper，直接操作数据库
        readableDatabase = dbHelper.openReadLink();
        writableDatabase = dbHelper.openWriteLink();

        clearData();
    }

    @After
    public void tearDown() {
        clearData();
        dbHelper.closeLink();
    }

    private void clearData() {
        writableDatabase.execSQL("DELETE FROM " + TABLE_USER_INFO);
        writableDatabase.delete(TABLE_USER_INFO, null, null);
    }

    @Test
    public void testSave() {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "testAccount";
        userInfo.username = "testUsername";
        userInfo.password = "testPassword";
        userInfo.level = 1;

        long result = dbHelper.save(userInfo);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_USER_INFO + " WHERE _id = ?", new String[]{String.valueOf(result)});
        cursor.moveToFirst();

        assertEquals("testAccount", cursor.getString(cursor.getColumnIndex("account")));
        assertEquals("testUsername", cursor.getString(cursor.getColumnIndex("username")));
        assertEquals("testPassword", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals(1, cursor.getInt(cursor.getColumnIndex("level")));

        cursor.close();
    }

    @Test
    public void testQueryByAccount() {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "testAccount";
        userInfo.username = "testUsername";
        userInfo.password = "testPassword";
        userInfo.level = 1;
        dbHelper.save(userInfo);

        UserInfo queriedUserInfo = dbHelper.queryByAccount("testAccount");

        assertNotNull(queriedUserInfo);
        assertEquals("testUsername", queriedUserInfo.username);
        assertEquals("testPassword", queriedUserInfo.password);
        assertEquals(1, queriedUserInfo.level);
    }

    @Test
    public void testUpdate() {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "testAccount";
        userInfo.username = "testUsername";
        userInfo.password = "testPassword";
        userInfo.level = 1;
        long id = dbHelper.save(userInfo);

        userInfo.id = (int) id;
        userInfo.username = "updatedUsername";
        userInfo.password = "updatedPassword";
        userInfo.level = 2;

        long result = dbHelper.updata(userInfo);

        assertEquals(1, result);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_USER_INFO + " WHERE _id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        assertEquals("updatedUsername", cursor.getString(cursor.getColumnIndex("username")));
        assertEquals("updatedPassword", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals(2, cursor.getInt(cursor.getColumnIndex("level")));

        cursor.close();
    }

    @Test
    public void testAccountExist() {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "testAccount";
        userInfo.username = "testUsername";
        userInfo.password = "testPassword";
        userInfo.level = 1;
        dbHelper.save(userInfo);

        assertTrue(dbHelper.accountExist("testAccount"));
        assertFalse(dbHelper.accountExist("nonExistentAccount"));
    }
}