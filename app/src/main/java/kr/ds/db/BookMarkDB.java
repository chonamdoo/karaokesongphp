package kr.ds.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookMarkDB {
	private static final String TAG = "BOOKMARKDATABASE";
	
	public static final String ID = "_id";
	public static final String CONTENTS_ID = "contents_id";
	
	
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE = "create table karaokesongphp (_id integer primary key autoincrement, " +
    																"contents_id text not null "+
    																");";    
    private static final String DATABASE_NAME = "karaokesongphp.db";
    private static final String DATABASE_TABLE = "karaokesongphp";
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
    
    public BookMarkDB(Context ctx) {
    	this.mCtx = ctx;
    }

    public BookMarkDB open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this; 
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    // 북마크데이터 Insert Type1
    public long createNote(String contents_id) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CONTENTS_ID, contents_id);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
  
    
    // 북마크데이터 Delete
    public boolean deleteNote(String rowId) throws SQLException {    	
        return mDb.delete(DATABASE_TABLE, CONTENTS_ID + "= '" + rowId + "'", null) > 0;
    }
    
    // 북마크데이터 모든데이터 Select
    public Cursor fetchAllForType() throws SQLException {
        Cursor mCursor = mDb.query(DATABASE_TABLE, new String[] { ID, CONTENTS_ID}, null, null, null, null, ID +" DESC");
        return mCursor; 
    }
    
    // 북마크데이터 중복데이터 확인
    public Cursor BookMarkConfirm(String rowId) throws SQLException {
        Cursor mCursor = mDb.query(true,
        		DATABASE_TABLE,
        		new String[] {CONTENTS_ID },
        		CONTENTS_ID + "= '" + rowId + "'", null, null, null, null, null); 

        if (mCursor != null) { 
            mCursor.moveToFirst(); 
        }
        return mCursor; 
    }
} 

