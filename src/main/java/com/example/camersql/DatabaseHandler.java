package com.example.camersql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, "imageDb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table images(id integer primary key,img blob not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists images");
    }

    //insert a image
    // probably need an if statement
    public Boolean insertimage(/*Integer i,*/ ImageView picture)
    {
        SQLiteDatabase db = this.getWritableDatabase();

// from stack overflow
            Bitmap photo = ((BitmapDrawable)picture.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            // from youtube
            ContentValues contentValues = new ContentValues();
            /*contentValues.put("id", i);*/
            contentValues.put("img", bArray );
            db.insert("images", null, contentValues);
            return true;
    }

    //retrieve image from database ;
    public Bitmap getimage(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images where id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToNext())
        {
            byte[] imag = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);
        }
        return bt;
    }
}
