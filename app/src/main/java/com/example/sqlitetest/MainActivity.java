package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String NAME = "user.db";
    private static final int VERSION = 1;
    private Button mInsert;
    private Button mDelete;
    private Button mUpdate;
    private Button mQuery;
    private EditText mInsertEdit;
    private EditText mNewDataEdit;
    private EditText mOldDataEdit;
    private EditText mDeleteEdit;
    private SQLiteDatabase mDb;
    private TextView mTextView;
    private TextView mTextView1;
    private String mTextName;
    private Button mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 创建DBOpenHelper对象,当数据库不存在就新建一个数据库，
         * 当数据库存在就检查版本是否一直，不一致就更新数据库。
         */
        DBOpenHelper DBOpenHelper = new DBOpenHelper(this, NAME, null, 1);
        mDb = DBOpenHelper.getWritableDatabase();
        init();
    }

    /**
     * 初始化控件
     */
    public void init() {
        mInsert = findViewById(R.id.insert);
        mInsert.setOnClickListener(this);
        mInsertEdit = findViewById(R.id.insert_edit);
        mNewDataEdit = findViewById(R.id.new_data);
        mOldDataEdit = findViewById(R.id.old_data);
        mUpdate = findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mDeleteEdit = findViewById(R.id.delete_data);
        mDelete = findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
        mQuery = findViewById(R.id.query);
        mQuery.setOnClickListener(this);
        mTextView = findViewById(R.id.data);
        mTextView1 = findViewById(R.id.data_two);
        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(this);
        mTextName = "";
    }

    @Override
    public void onClick(View view) {
        if (view == mInsert) {
            //插入数据库
            ContentValues values = new ContentValues();
            values.put("name", mInsertEdit.getText().toString());
            mDb.insert("user", null, values);
        } else if (view == mUpdate) {
            //更新数据
            ContentValues values = new ContentValues();
            values.put("name", mNewDataEdit.getText().toString());
            mDb.update("user", values, "name = ?", new String[]{mOldDataEdit.getText().toString()});
        } else if (view == mDelete) {
            //删除数据
            mDb.delete("user", "name = ?", new String[]{mDeleteEdit.getText().toString()});
        } else if (view == mQuery) {
            //查询全部数据
            Cursor cursor = mDb.query("user", new String[]{"name"}, null, null, null, null, null);
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                mTextName =  mTextName  + name + "\n";
            }
            mTextView.setText(mTextName);
            mTextName = "";
            cursor.close();

            //查询某个数据
            Cursor cursor1 = mDb.query("user", new String[]{"name"}, "name = ?", new String[]{"小李"}, null,null, null);
            while (cursor1.moveToNext()){
                String name = cursor1.getString(cursor1.getColumnIndex("name"));
                mTextName = mTextName + name + "\n";
            }
            mTextView1.setText(mTextName);
            mTextName = "";
            cursor1.close();
        } else if(view == mClose){
            //清除当前显示的内容
            mTextView.setText("");
            mTextView1.setText("");
            mTextName = "";
        }
    }
}