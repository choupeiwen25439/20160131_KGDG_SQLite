package com.chou.polly.phonebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        // 取得 DB
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // 設定按鈕的監聽者
        Button createBtn = (Button) findViewById(R.id.button_add);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        Button readBtn = (Button) findViewById(R.id.button_read);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 讀取整張表
                SqlQuery("SELECT * FROM " + DBHelper.TABLE_NAME);
            }
        });

        Button clearBtn = (Button) findViewById(R.id.button_clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 刪除整張表
                db.delete(DBHelper.TABLE_NAME, null, null);
            }
        });
    }

    public void saveData() {
        EditText nameInput = (EditText)findViewById(R.id.et_name);
        EditText phoneInput = (EditText)findViewById(R.id.et_phone);

        ContentValues cv = new ContentValues();
        cv.put("phone", phoneInput.getText().toString());
        cv.put("name", nameInput.getText().toString());
    }

    public void SqlQuery(String sql) {
        String str = "";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            str += "姓名：" + cursor.getString(1) + "\n";
            str += "電話：" + cursor.getString(2) + "\n - - - - - - - - - - - - - - -\n";
            cursor.moveToNext();
        }

        TextView output = (TextView)findViewById(R.id.tv_detail);
        output.setText(str);
    }
}
