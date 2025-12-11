package com.example.weighttogo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

/**
 * Unit tests for WeighToGoDBHelper database helper class.
 * Uses Robolectric for Android framework components (SQLite).
 *
 * Following strict TDD: one failing test at a time.
 */
@RunWith(RobolectricTestRunner.class)
public class WeighToGoDBHelperTest {

    private Context context;
    private WeighToGoDBHelper dbHelper;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        // Get fresh instance for each test
        dbHelper = WeighToGoDBHelper.getInstance(context);
    }

    @After
    public void tearDown() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        // Clean up database file
        context.deleteDatabase("weigh_to_go.db");
    }

    /**
     * Test 1: getInstance returns a non-null instance
     */
    @Test
    public void test_getInstance_returnsSingletonInstance() {
        // ACT
        WeighToGoDBHelper instance = WeighToGoDBHelper.getInstance(context);

        // ASSERT
        assertNotNull("getInstance should return non-null WeighToGoDBHelper", instance);
    }

    /**
     * Test 2: getInstance called twice returns the same instance (Singleton pattern)
     */
    @Test
    public void test_getInstance_calledTwice_returnsSameInstance() {
        // ACT
        WeighToGoDBHelper instance1 = WeighToGoDBHelper.getInstance(context);
        WeighToGoDBHelper instance2 = WeighToGoDBHelper.getInstance(context);

        // ASSERT
        assertSame("getInstance should return the same instance (Singleton)", instance1, instance2);
    }

    /**
     * Test 3: onCreate creates users table with correct schema
     */
    @Test
    public void test_onCreate_createsUsersTable() {
        // ACT
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ASSERT - Check table exists
        Cursor cursor = db.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='users'",
            null
        );
        assertTrue("users table should exist", cursor.moveToFirst());
        cursor.close();

        // ASSERT - Check table schema
        cursor = db.rawQuery("PRAGMA table_info(users)", null);
        int columnCount = cursor.getCount();
        assertEquals("users table should have 6 columns", 6, columnCount);

        // Verify column names (order matters in PRAGMA table_info)
        String[] expectedColumns = {"id", "username", "password_hash", "salt", "created_at", "last_login"};
        int index = 0;
        while (cursor.moveToNext()) {
            String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            assertEquals("Column " + index + " should be " + expectedColumns[index],
                expectedColumns[index], columnName);
            index++;
        }
        cursor.close();
    }

    /**
     * Test 4: onCreate creates weight_entries table with correct schema
     */
    @Test
    public void test_onCreate_createsWeightEntriesTable() {
        // ACT
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ASSERT - Check table exists
        Cursor cursor = db.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='weight_entries'",
            null
        );
        assertTrue("weight_entries table should exist", cursor.moveToFirst());
        cursor.close();

        // ASSERT - Check table schema
        cursor = db.rawQuery("PRAGMA table_info(weight_entries)", null);
        int columnCount = cursor.getCount();
        assertEquals("weight_entries table should have 9 columns", 9, columnCount);

        // Verify required columns exist
        boolean hasWeightId = false;
        boolean hasUserId = false;
        boolean hasWeightValue = false;
        boolean hasWeightDate = false;
        boolean hasIsDeleted = false;

        while (cursor.moveToNext()) {
            String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            if (columnName.equals("id")) hasWeightId = true;
            if (columnName.equals("user_id")) hasUserId = true;
            if (columnName.equals("weight_value")) hasWeightValue = true;
            if (columnName.equals("weight_date")) hasWeightDate = true;
            if (columnName.equals("is_deleted")) hasIsDeleted = true;
        }
        cursor.close();

        assertTrue("weight_entries should have id column", hasWeightId);
        assertTrue("weight_entries should have user_id column", hasUserId);
        assertTrue("weight_entries should have weight_value column", hasWeightValue);
        assertTrue("weight_entries should have weight_date column", hasWeightDate);
        assertTrue("weight_entries should have is_deleted column", hasIsDeleted);
    }

    /**
     * Test 5: onCreate creates goal_weights table with correct schema
     */
    @Test
    public void test_onCreate_createsGoalWeightsTable() {
        // ACT
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ASSERT - Check table exists
        Cursor cursor = db.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='goal_weights'",
            null
        );
        assertTrue("goal_weights table should exist", cursor.moveToFirst());
        cursor.close();

        // ASSERT - Check table schema
        cursor = db.rawQuery("PRAGMA table_info(goal_weights)", null);
        int columnCount = cursor.getCount();
        assertEquals("goal_weights table should have 11 columns", 11, columnCount);

        // Verify required columns exist
        boolean hasGoalId = false;
        boolean hasUserId = false;
        boolean hasGoalWeight = false;
        boolean hasIsActive = false;

        while (cursor.moveToNext()) {
            String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            if (columnName.equals("id")) hasGoalId = true;
            if (columnName.equals("user_id")) hasUserId = true;
            if (columnName.equals("goal_weight")) hasGoalWeight = true;
            if (columnName.equals("is_active")) hasIsActive = true;
        }
        cursor.close();

        assertTrue("goal_weights should have id column", hasGoalId);
        assertTrue("goal_weights should have user_id column", hasUserId);
        assertTrue("goal_weights should have goal_weight column", hasGoalWeight);
        assertTrue("goal_weights should have is_active column", hasIsActive);
    }

    /**
     * Test 6: onConfigure enables foreign keys
     */
    @Test
    public void test_onConfigure_enablesForeignKeys() {
        // ACT
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ASSERT - Check foreign_keys pragma is ON
        Cursor cursor = db.rawQuery("PRAGMA foreign_keys", null);
        assertTrue("PRAGMA foreign_keys should return a result", cursor.moveToFirst());
        int foreignKeysEnabled = cursor.getInt(0);
        cursor.close();

        assertEquals("Foreign keys should be enabled (1)", 1, foreignKeysEnabled);
    }
}
