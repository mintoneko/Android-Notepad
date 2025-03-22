package com.loliowo.notepadapp.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.loliowo.notepadapp.bean.Notepad;

import java.util.ArrayList;
import java.util.List;

// 数据库帮助类
public class MyDbHelper extends SQLiteOpenHelper {
  private static final int version = 1;
  private static final String name = "notepad.db";

  public MyDbHelper(@Nullable Context context) {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String sql = "create table notepad(id integer primary key autoincrement,content text,time text)";
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  // 插入数据
  public void insert(Notepad notepad) {
    SQLiteDatabase db = getWritableDatabase();
    String sql = "insert into notepad(content,time) values(?,?)";
    db.execSQL(sql, new Object[]{notepad.getContent(), notepad.getTime()});
  }

  // 从数据库查询所有记录保存到list，然后返回
  @SuppressLint("Range")
  public List<Notepad> findAll() {
    SQLiteDatabase db = getWritableDatabase();
    String sql = "select * from notepad";
    Cursor cursor = db.rawQuery(sql, null);
    List<Notepad> list = new ArrayList<>();
    while (cursor.moveToNext()) {
      Notepad notepad = new Notepad();

      int id = cursor.getInt(cursor.getColumnIndex("id"));
      String content = cursor.getString(cursor.getColumnIndex("content"));
      String time = cursor.getString(cursor.getColumnIndex("time"));

      notepad.setId(id);
      notepad.setContent(content);
      notepad.setTime(time);

      list.add(notepad);
      // 将所有数据查出来并且放到list中
      // 多利用调试查看各个变量的内容
    }
    cursor.close();
    return list;
  }
}
