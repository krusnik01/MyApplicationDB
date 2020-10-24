package com.example.myapplicationdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final String LOG_TAG = "myLogs";
    Button btnAdd, btnRead, btnClear, btnUpd, btnDel,btnGet;
    EditText etName, etEmail, etID;
    DBHelper dbHelper;
    TextView textView;
    private DBJob JobFromBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        textView = findViewById(R.id.textViewBD);
        btnUpd = findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);
        btnDel =  findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        etID =  findViewById(R.id.etID);
        btnGet = findViewById(R.id.buttonGetAlone);
        btnGet.setOnClickListener(this);

        // создаем объект для создания и управления версиями БД
        JobFromBD = new DBJob(getApplicationContext());
        //dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        switch (v.getId()) {
            case R.id.btnAdd:
                cv.put("name", name);
                cv.put("email", email);
                // вставляем запись и получаем ее ID
                JobFromBD.Insert(cv);
                break;
            case R.id.btnRead:
                textView.setText("");
                // делаем запрос всех данных из таблицы mytable, получаем
                textView.setText(JobFromBD.getData().toString());
                break;
            case R.id.btnClear:
                JobFromBD.DeleteAll();
                // удаляем все записи
                //      int clearCount = db.delete("mytable", null, null);
                //      Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {// проверяем совпадают ли строки из id и "" если
                    break;
                }
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("email", email);
                // обновляем по id
                 JobFromBD.Update(cv,id);
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                // удаляем по id
                JobFromBD.Delete(id);
                break;
            case R.id.buttonGetAlone:
                textView.setText(JobFromBD.getAlone(id).toString());
        }


    }


}