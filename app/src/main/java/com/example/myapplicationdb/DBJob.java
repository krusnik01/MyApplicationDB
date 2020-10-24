package com.example.myapplicationdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBJob {
    private SQLiteDatabase db;
    public DBJob(Context context) {
        db = new DBHelper(context).getWritableDatabase();
    }

    public long Insert(ContentValues cv){
        long rowID = db.insert("mytable", null, cv);
        return rowID;
    }

    public void Update(ContentValues cv, String id){
        db.update("mytable", cv, "id = ?",
                new String[] { id });
    }
    public void Delete(String id){
        db.delete("mytable", "id = " + id, null);
    }

    public void DeleteAll(){
        db.delete("mytable", null, null);
    }

    public ArrayList<String> getAlone(String id){
        boolean i=false;
        ArrayList<String > arrayList = new ArrayList<>();
        Cursor cursor = db.query("mytable", null, null,
                null, null, null, null);
        if ((cursor != null)&&(cursor.getCount()>0)) {
            cursor.moveToFirst();

            // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("name");
            int emailColIndex = cursor.getColumnIndex("email");
            do {
                if (cursor.getInt(idColIndex)==(Integer.parseInt(id))){
                    arrayList.add("ID = " + cursor.getInt(idColIndex) +    // получаем значения по номерам столбцов
                            ", name = " + cursor.getString(nameColIndex) +
                            ", email = " + cursor.getString(emailColIndex)+'\n');
                    i= true;
                }
            }while (cursor.moveToNext());


        }
        if (!i)  {arrayList.add("Строка не найдена");}
            return arrayList;
    }
    public ArrayList<String> getData(){
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = db.query("mytable", null, null,// ставим позицию курсора на первую строку выборки
                null, null, null, null);
         if ((cursor != null)&&(cursor.getCount()>0)) { // если в выборке нет строк, вернется false
             cursor.moveToFirst();
            int idColIndex = cursor.getColumnIndex("id");  // определяем номера столбцов по имени в выборке
            int nameColIndex = cursor.getColumnIndex("name");
            int emailColIndex = cursor.getColumnIndex("email");
            do {
                arrayList.add("ID = " + cursor.getInt(idColIndex) +    // получаем значения по номерам столбцов
                        ", name = " + cursor.getString(nameColIndex) +
                        ", email = " + cursor.getString(emailColIndex)+'\n');
            } while (cursor.moveToNext());  // переход на следующую строку
             // а если следующей нет (текущая - последняя), то false - выходим из цикла
        }else
            arrayList.add("База пуста");
        return arrayList;
    }

}
