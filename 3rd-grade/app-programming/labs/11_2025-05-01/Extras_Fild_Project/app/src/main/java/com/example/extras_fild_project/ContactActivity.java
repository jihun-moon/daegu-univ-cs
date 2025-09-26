package com.example.extras_fild_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mycontacts.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contacts (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT, name TEXT, tel TEST);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}


public class ContactActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    EditText j_edit_contact_name, j_edit_contact_number;
    TextView edit_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helper = new DBHelper(this);
        try{
            db = helper.getWritableDatabase();
        } catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }

        j_edit_contact_name = findViewById(R.id.edit_contact_name);
        j_edit_contact_number = findViewById(R.id.edit_contact_number);
        edit_result = findViewById(R.id.txt_result);
    }


    public void insert(View target) {
        String name = j_edit_contact_name.getText().toString();
        String number = j_edit_contact_number.getText().toString();

        db.execSQL("INSERT INTO contacts VALUES (null, '"+ name + "', '" + number + "');");
        Toast.makeText(getApplicationContext(), "추가완료", Toast.LENGTH_SHORT).show();

        j_edit_contact_name.setText(" ");
        j_edit_contact_number.setText(" ");

    }

    public void search(View target){
        String name = j_edit_contact_name.getText().toString();
        Cursor cursor;
        cursor = db.rawQuery("SELECT name, tel FROM contacts WHERE name='" + name + "';", null);
        while(cursor.moveToNext()){
            String tel = cursor.getString(1);
            j_edit_contact_number.setText(tel);
        }
    }

    public void select_all(View target){
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        String s = "ID      name        tel \r\n";
        while(cursor.moveToNext()){
            s += cursor.getString(0) + "        ";
            s += cursor.getString(1) + "        ";
            s += cursor.getString(2) + "        ";
        }
        edit_result.setText(s);
    }
}










