package com.example.codemaven3015.dialerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by CodeMaven3015 on 5/14/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RECORDING_DATABASE.db";
    public static final String TABLE_NAME = "RECORDING";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table "+ TABLE_NAME+" (ID INTEGER  PRIMARY KEY AUTOINCREMENT, PATH TEXT, UPLOADED INTEGER)");
        //uploaded variable 1 is for uploaded and 0 is for not yet uploaded.


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void updatetableRecording(String path , int flag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("PATH", path);
        contentValue.put("UPLOADED", flag);
        db.insert(TABLE_NAME, null, contentValue);
    }

    public void updateFlag(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET UPLOADED = 1 WHERE ID = "+id);
    }
    public void deleteTableEntries(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "UPLOADED =?", new String[]{"1"});
    }
    public JSONArray getAllPathsNonUploaded() throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE UPLOADED =? ";
        Cursor res = db.rawQuery(Query,new String[] {"0"}, null);
        res.moveToFirst();
        JSONArray dataAll = new JSONArray();
        do{
            JSONObject obj = new JSONObject();
            obj.put("id",res.getString(0));
            obj.put("path",res.getString(1));
            dataAll.put(obj);
        }while(res.moveToNext());

        res.close();
        return dataAll;
    }
}
