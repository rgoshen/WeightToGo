package com.example.weighttogo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLite database helper for Weigh to Go application.
 *
 * Implements Singleton pattern for thread-safe single database instance.
 * Manages database creation, upgrades, and foreign key enforcement.
 *
 * Database Schema:
 * - users: User authentication and profile data
 * - weight_entries: Daily weight tracking with soft delete support
 * - goal_weights: User goal weights and achievement tracking
 *
 * Security:
 * - Uses foreign keys for referential integrity
 * - Passwords stored as salted SHA-256 hashes (never plain text)
 * - All user input should use parameterized queries (handled in DAOs)
 */
public class WeighToGoDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "WeighToGoDBHelper";

    // Database configuration
    private static final String DATABASE_NAME = "weigh_to_go.db";
    private static final int DATABASE_VERSION = 1;

    // Singleton instance
    private static WeighToGoDBHelper instance;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_WEIGHT_ENTRIES = "weight_entries";
    public static final String TABLE_GOAL_WEIGHTS = "goal_weights";

    // SQL: Create users table
    private static final String CREATE_TABLE_USERS =
        "CREATE TABLE " + TABLE_USERS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "password_hash TEXT NOT NULL, " +
            "salt TEXT NOT NULL, " +
            "created_at TEXT NOT NULL, " +
            "last_login TEXT" +
        ")";

    // SQL: Create weight_entries table
    private static final String CREATE_TABLE_WEIGHT_ENTRIES =
        "CREATE TABLE " + TABLE_WEIGHT_ENTRIES + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "weight_value REAL NOT NULL, " +
            "weight_unit TEXT NOT NULL, " +
            "weight_date TEXT NOT NULL, " +
            "notes TEXT, " +
            "created_at TEXT NOT NULL, " +
            "updated_at TEXT NOT NULL, " +
            "is_deleted INTEGER NOT NULL DEFAULT 0, " +
            "FOREIGN KEY (user_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE" +
        ")";

    // SQL: Create goal_weights table
    private static final String CREATE_TABLE_GOAL_WEIGHTS =
        "CREATE TABLE " + TABLE_GOAL_WEIGHTS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "goal_weight REAL NOT NULL, " +
            "goal_unit TEXT NOT NULL, " +
            "start_weight REAL NOT NULL, " +
            "target_date TEXT, " +
            "is_achieved INTEGER NOT NULL DEFAULT 0, " +
            "achieved_date TEXT, " +
            "created_at TEXT NOT NULL, " +
            "updated_at TEXT NOT NULL, " +
            "is_active INTEGER NOT NULL DEFAULT 1, " +
            "FOREIGN KEY (user_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE" +
        ")";

    /**
     * Private constructor to enforce Singleton pattern.
     *
     * @param context application context
     */
    private WeighToGoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "WeighToGoDBHelper constructor called");
    }

    /**
     * Get singleton instance of database helper (thread-safe).
     *
     * @param context application context
     * @return singleton WeighToGoDBHelper instance
     */
    public static synchronized WeighToGoDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new WeighToGoDBHelper(context.getApplicationContext());
            Log.i(TAG, "Created new WeighToGoDBHelper instance for database: " + DATABASE_NAME);
        }
        return instance;
    }

    /**
     * Configure database before opening.
     * Enables foreign key constraints for referential integrity.
     *
     * @param db the database
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        Log.d(TAG, "Foreign key constraints enabled");
    }

    /**
     * Called when database is created for the first time.
     * Creates all tables with proper schema and constraints.
     *
     * @param db the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database " + DATABASE_NAME + " version " + DATABASE_VERSION);

        try {
            // Create users table
            db.execSQL(CREATE_TABLE_USERS);
            Log.d(TAG, "Created table: " + TABLE_USERS);

            // Create weight_entries table
            db.execSQL(CREATE_TABLE_WEIGHT_ENTRIES);
            Log.d(TAG, "Created table: " + TABLE_WEIGHT_ENTRIES);

            // Create goal_weights table
            db.execSQL(CREATE_TABLE_GOAL_WEIGHTS);
            Log.d(TAG, "Created table: " + TABLE_GOAL_WEIGHTS);

            Log.i(TAG, "Database creation completed successfully");

        } catch (Exception e) {
            Log.e(TAG, "Error creating database tables: " + e.getMessage(), e);
            throw e;  // Re-throw to ensure app doesn't continue with broken database
        }
    }

    /**
     * Called when database needs to be upgraded (version increase).
     * Currently drops and recreates all tables (acceptable for development).
     *
     * Production apps should implement proper migration strategy.
     *
     * @param db the database
     * @param oldVersion the old database version
     * @param newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        try {
            // Drop existing tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL_WEIGHTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT_ENTRIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

            Log.w(TAG, "Dropped all tables for database upgrade");

            // Recreate tables with new schema
            onCreate(db);

            Log.w(TAG, "Database upgrade completed");

        } catch (Exception e) {
            Log.e(TAG, "Error upgrading database: " + e.getMessage(), e);
            throw e;
        }
    }
}
