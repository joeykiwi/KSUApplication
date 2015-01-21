package intracode.org.ksuapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongwookim on 1/20/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String

    DATABASE_NAME = "applicantDB",
    TABLE_APPLICANT = "applicants",
    KEY_ID = "id",
    KEY_NAME = "name;",
    KEY_PHONE = "phone",
    KEY_EMAIL = "email",
    KEY_SID = "sid",
    KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Joey", "Database handler");
        db.execSQL("CREATE TABLE " + TABLE_APPLICANT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                             KEY_NAME + " TEXT, " +
                                                             KEY_PHONE + " TEXT, " +
                                                             KEY_EMAIL + " TEXT, " +
                                                             KEY_SID +  " TEXT, " +
                                                             KEY_IMAGEURI + " TEXT)");
        Log.d("Joey", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICANT);

        onCreate(db);
    }

    public void createApplicant(Applicant _applicant) {
        Log.d("Joey", "In create applicant");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, _applicant.getName());
        values.put(KEY_PHONE, _applicant.getPhone());
        values.put(KEY_EMAIL, _applicant.getEmail());
        values.put(KEY_SID, _applicant.getSID());
        values.put(KEY_IMAGEURI, _applicant.getimageUri().toString());
//
        db.insert(TABLE_APPLICANT, null, values);
        db.close();

    }

    public Applicant getApplicant(int id) {
        SQLiteDatabase db = getReadableDatabase();
//
//        Cursor cursor1 = db.query(TABLE_APPLICANT, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_SID, KEY_IMAGEURI},
//                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        Cursor cursor = db.query(TABLE_APPLICANT, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL ,KEY_SID, KEY_IMAGEURI},
                                 KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Applicant applicant = new Applicant(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                                            cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));

        db.close();
        cursor.close();
        return applicant;
    }

    public void deleteApplicant(Applicant applicant) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_APPLICANT, KEY_ID + "=?", new String[] {String.valueOf(applicant.getId())});
        db.close();
    }

    public int getApplicantCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPLICANT, null );

        int count = cursor.getCount();

        db.close();
        cursor.close();

        return count;
    }

    public int updateApplicant(Applicant _applicant) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, _applicant.getName());
        values.put(KEY_PHONE, _applicant.getPhone());
        values.put(KEY_EMAIL, _applicant.getEmail());
        values.put(KEY_SID, _applicant.getSID());
        values.put(KEY_IMAGEURI, _applicant.getimageUri().toString());

        return db.update(TABLE_APPLICANT, values, KEY_ID + "=?", new String[] {String.valueOf(_applicant.getId())});
    }

    public List<Applicant> getAllApplicant() {

        List<Applicant> applicants = new ArrayList<Applicant>();
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPLICANT, null);

//        if (cursor.moveToFirst()) {
//            do {
//                Applicant applicant = new Applicant(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
//                        cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));
//                applicants.add(applicant);
//            } while (cursor.moveToNext());
//        }

        return applicants;
    }
}
