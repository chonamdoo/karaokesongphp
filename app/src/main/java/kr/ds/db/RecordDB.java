package kr.ds.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecordDB {
	private static final String TAG = "BOOKMARKDATABASE";

	public static final String ID = "_id";
	public static final String CONTENTS_ID = "contents_id";
    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    public static final String VIDEO_ID = "video_id";
    public static final String URL_FILE = "url_file";
    public static final String REGDATE = "regdate";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE = "create table record (_id integer primary key autoincrement, " +
    																"contents_id text not null, "+
                                                                    "image text not null, "+
                                                                    "title text not null, "+
                                                                    "video_id text not null, "+
                                                                    "url_file text not null, "+
                                                                    "regdate text not null "+
    																");";
    private static final String DATABASE_NAME = "record.db";
    private static final String DATABASE_TABLE = "record";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS conf_data");
            onCreate(db);
        }
    }

    public RecordDB(Context ctx) {
    	this.mCtx = ctx;
    }

    public RecordDB open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this; 
    }
    
    public void close() {
        mDbHelper.close();
    }



    // 북마크데이터 Insert Type1
    public long createNote(String contents_id, String image, String title, String video_id, String url_file, String regdate) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CONTENTS_ID, contents_id);
        initialValues.put(IMAGE, image);
        initialValues.put(TITLE, title);
        initialValues.put(VIDEO_ID, video_id);
        initialValues.put(URL_FILE, url_file);
        initialValues.put(REGDATE, regdate);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
  
    
    // 북마크데이터 Delete
    public boolean deleteNote(String rowId) throws SQLException {    	
        return mDb.delete(DATABASE_TABLE, CONTENTS_ID + "= '" + rowId + "'", null) > 0;
    }


    // 북마크데이터 모든데이터 Select
    public Cursor fetchAllForType() throws SQLException {
        Cursor mCursor = mDb.query(DATABASE_TABLE, new String[] { ID, CONTENTS_ID, IMAGE, TITLE, VIDEO_ID, URL_FILE, REGDATE}, null, null, null, null, ID +" DESC");
        return mCursor; 
    }
}

