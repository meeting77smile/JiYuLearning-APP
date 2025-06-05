package com.example.jiyulearning.database;

import static org.junit.Assert.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jiyulearning.entity.ListInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ListDBHelperTest {
    private static final String TABLE_LIST_INFO = "list_info";
    private ListDBHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

    @Before
    public void setUp() {
        Context context = RuntimeEnvironment.getApplication();

        dbHelper = ListDBHelper.getInstance(context);

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
        writableDatabase.execSQL("DELETE FROM " + TABLE_LIST_INFO);
        writableDatabase.delete(TABLE_LIST_INFO, null, null);
    }

    @Test
    public void testSave() {
        ListInfo listInfo = new ListInfo();
        listInfo.date = "2023-10-01";
        listInfo.time = 100.0;
        listInfo.description = "Test Description";

        long result = dbHelper.save(listInfo);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_LIST_INFO + " WHERE _id = ?", new String[]{String.valueOf(result)});
        cursor.moveToFirst();

        assertEquals("2023-10-01", cursor.getString(cursor.getColumnIndex("date")));
        assertEquals(100.0, cursor.getDouble(cursor.getColumnIndex("time")), 0.001);
        assertEquals("Test Description", cursor.getString(cursor.getColumnIndex("description")));

        cursor.close();
    }

    @Test
    public void testQueryByDay() {
        List<ListInfo> expectedList = new ArrayList<>();

        ListInfo listInfo1 = new ListInfo();
        listInfo1.date = "2023-10-01";
        listInfo1.time = 100.0;
        listInfo1.description = "Test Description 1";
        dbHelper.save(listInfo1);

        ListInfo listInfo2 = new ListInfo();
        listInfo2.date = "2023-10-01";
        listInfo2.time = 200.0;
        listInfo2.description = "Test Description 2";
        dbHelper.save(listInfo2);

        expectedList.add(listInfo1);
        expectedList.add(listInfo2);

        List<ListInfo> resultList = dbHelper.queryByDay("2023-10-01");

        assertEquals(expectedList.size(), resultList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).date, resultList.get(i).date);
            assertEquals(expectedList.get(i).time, resultList.get(i).time, 0.001);
            assertEquals(expectedList.get(i).description, resultList.get(i).description);
        }
    }

    @Test
    public void testDeleteByDes() {
        ListInfo listInfo = new ListInfo();
        listInfo.date = "2023-10-01";
        listInfo.time = 100.0;
        listInfo.description = "Test Description";
        dbHelper.save(listInfo);

        long result = dbHelper.deleteByDes("Test Description");

        assertEquals(1, result);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_LIST_INFO + " WHERE description = ?", new String[]{"Test Description"});
        assertFalse(cursor.moveToFirst());

        cursor.close();
    }

    @Test
    public void testDeleteAll() {
        ListInfo listInfo1 = new ListInfo();
        listInfo1.date = "2023-10-01";
        listInfo1.time = 100.0;
        listInfo1.description = "Test Description 1";
        dbHelper.save(listInfo1);

        ListInfo listInfo2 = new ListInfo();
        listInfo2.date = "2023-10-01";
        listInfo2.time = 200.0;
        listInfo2.description = "Test Description 2";
        dbHelper.save(listInfo2);

        long result = dbHelper.deleteAll();

        assertEquals(2, result);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_LIST_INFO, null);
        assertFalse(cursor.moveToFirst());

        cursor.close();
    }

    @Test
    public void testUpdate() {
        ListInfo listInfo = new ListInfo();
        listInfo.date = "2023-10-01";
        listInfo.time = 100.0;
        listInfo.description = "Test Description";
        long id = dbHelper.save(listInfo);

        listInfo.id = (int) id;
        listInfo.date = "2023-10-02";
        listInfo.time = 150.0;
        listInfo.description = "Test Description";

        long result = dbHelper.updata(listInfo);

        assertEquals(1, result);

        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_LIST_INFO + " WHERE _id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        assertEquals("2023-10-02", cursor.getString(cursor.getColumnIndex("date")));
        assertEquals(150.0, cursor.getDouble(cursor.getColumnIndex("time")), 0.001);
        assertEquals("Test Description", cursor.getString(cursor.getColumnIndex("description")));

        cursor.close();
    }
}