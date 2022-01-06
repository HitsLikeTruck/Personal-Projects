package za.ac.cput.project3safeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SafeAppDatabase";

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_EMERGENCY_SERVICES = "emergency_services";
    private static final String TABLE_CIRCLE = "circle";
    private static final String TABLE_FAKE_CALL = "fake_call";
    private static final String TABLE_LIVE_LOCATION = "live_location";

    // User Table Column Names
    private static final String USER_ID = "ID";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_CELL_NUM = "number";

    // Emergency Services Table Column Names
    private static final String SERVICE_ID = "ID";
    private static final String SERVICE_NAME = "name";
    private static final String SERVICE_NUMBER = "number";

    // Circle Table Column Names
    private static final String MEMBER_ID = "ID";
    private static final String MEMBER_FIRST_NAME = "firstName";
    private static final String MEMBER_LAST_NAME= "lastName";
    private static final String MEMBER_CELL_NUM = "number";

    // Fake Call Table Column Names
    private static final String FAKE_CALL_ID = "ID";
    private static final String FAKE_CALL_RECORDING_LINK = "link";

    // Live Location Table Column Names
    private static final String LIVE_LOCATION_ID = "ID";
    private static final String LIVE_DEFAULT_MESSAGE= "defaultMessage";

    // Table CREATE Statements ==========================================================
    // User Table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + USER_ID + " INTEGER PRIMARY KEY, "
            + USER_FIRST_NAME + " TEXT,"
            + USER_LAST_NAME + " TEXT,"
            + USER_CELL_NUM + " TEXT)";

    // Emergency Services Table
    private static final String CREATE_TABLE_EMERGENCY_SERVICES = "CREATE TABLE "
            + TABLE_EMERGENCY_SERVICES + "(" + SERVICE_ID + " INTEGER PRIMARY KEY, "
            + SERVICE_NAME + " TEXT,"
            + SERVICE_NUMBER + " TEXT)";

    // Circle Table
    private static final String CREATE_TABLE_CIRCLE = "CREATE TABLE "
            + TABLE_CIRCLE + "(" + MEMBER_ID + " INTEGER PRIMARY KEY, "
            + MEMBER_FIRST_NAME + " TEXT,"
            + MEMBER_LAST_NAME + " TEXT,"
            + MEMBER_CELL_NUM + " TEXT)";

    // Fake Call Table
    private static final String CREATE_TABLE_FAKE_CALL = "CREATE TABLE "
            + TABLE_FAKE_CALL + "(" + FAKE_CALL_ID + " INTEGER PRIMARY KEY, "
            + FAKE_CALL_RECORDING_LINK + " TEXT)";

    // Live Location Table
    private static final String CREATE_TABLE_LIVE_LOCATION = "CREATE TABLE "
            + TABLE_LIVE_LOCATION + "(" + LIVE_LOCATION_ID + " INTEGER PRIMARY KEY, "
            + LIVE_DEFAULT_MESSAGE + " TEXT)";

    // ==================================================================================

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CIRCLE);
        db.execSQL(CREATE_TABLE_EMERGENCY_SERVICES);
        db.execSQL(CREATE_TABLE_FAKE_CALL);
        db.execSQL(CREATE_TABLE_LIVE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIRCLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMERGENCY_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAKE_CALL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVE_LOCATION);
        onCreate(db);
    }

    // =============== All Tables ==================================================================

    public void clearTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS,null, null);
        db.delete(TABLE_CIRCLE,null, null);
        db.delete(TABLE_EMERGENCY_SERVICES,null, null);
        db.delete(TABLE_FAKE_CALL,null, null);
        db.delete(TABLE_LIVE_LOCATION,null, null);
    }

    // =============== User Table ==================================================================

    public boolean addUser(String fName, String lName, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FIRST_NAME, fName);
        contentValues.put(USER_LAST_NAME, lName);
        contentValues.put(USER_CELL_NUM, number);

        Log.d(TAG, "adduser: Adding " + fName + " " + lName + " " + number + " to " + TABLE_USERS);
        long result = db.insert(TABLE_USERS, null, contentValues);

        // -1 if not inserted correctly
        return result != -1;
    }

    public boolean updateUser(String fName, String lName, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FIRST_NAME, fName);
        contentValues.put(USER_LAST_NAME, lName);
        contentValues.put(USER_CELL_NUM, number);

        Log.d(TAG, "updateUser: Updating " + fName + " " + lName + " " + number + " to " + TABLE_USERS);
        long result = db.update(TABLE_USERS, contentValues, "id = ? ", new String[] { Integer.toString(1) } );

        // -1 if not inserted correctly
        return result != -1;
    }



    public Cursor fetchUserDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where id = 1", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // =============== End User Table ==============================================================

    // =============== Emergency Services Table ====================================================

    public void fillEmergencyServices() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SERVICE_NAME, "AMBULANCE");
        contentValues.put(SERVICE_NUMBER, "10177");
        db.insert(TABLE_EMERGENCY_SERVICES, null, contentValues);

        contentValues.put(SERVICE_NAME, "POLICE");
        contentValues.put(SERVICE_NUMBER, "10111");
        db.insert(TABLE_EMERGENCY_SERVICES, null, contentValues);

        contentValues.put(SERVICE_NAME, "FIRE RESCUE");
        contentValues.put(SERVICE_NUMBER, "10177");
        db.insert(TABLE_EMERGENCY_SERVICES, null, contentValues);

        contentValues.put(SERVICE_NAME, "NEIGHBOURHOOD WATCH");
        contentValues.put(SERVICE_NUMBER, "");
        db.insert(TABLE_EMERGENCY_SERVICES, null, contentValues);
    }

    public Cursor fetchAmbulanceDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from emergency_services where id = 1", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchPoliceDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from emergency_services where id = 2", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchFireRescueDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from emergency_services where id = 3", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNeighbourhoodWatchDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from emergency_services where id = 4", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateNeighbourhoodWatch(String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICE_NUMBER, number);

        long result = db.update(TABLE_EMERGENCY_SERVICES, contentValues, "name = ? ", new String[] { String.valueOf(name) } );

        // -1 if not inserted correctly
        return result != -1;
    }

    // =============== End Emergency Services Table ================================================

    // =============== Circle Table ================================================================

    // =============== End Circle Table ============================================================

    // =============== Live Location Table =========================================================

    // =============== End Live Location Table =====================================================

    // =============== Fake Call Table =============================================================

    // =============== End Fake Call Table =========================================================
}
