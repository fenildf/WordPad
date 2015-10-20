package edu.uestc.yang.sqldemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    //确认输入数据库的按钮
    private Button btn1;
    //确认从数据库调出数据
    private Button btn2;
    //输入新单词的输入框
    private EditText word_in;
    //输入翻译的输入框
    private EditText translation;
    //输入搜索的单词的输入框
    private EditText word_sear;
    //显示搜索出的翻译
    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化各个控件
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        word_in = (EditText) findViewById(R.id.word_in);
        word_sear = (EditText) findViewById(R.id.word_sear);
        translation = (EditText) findViewById(R.id.trans);
        view = (TextView) findViewById(R.id.trans_sear);
        //view.setMovementMethod(ScrollingMovementMethod.getInstance());
//        DatabaseHelper helper = new DatabaseHelper(this);
//        final SQLiteDatabase db = helper.getReadableDatabase();
//        String s = "select * from word";
//        final Cursor cursor = db.rawQuery(s, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        word_in.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    return;
                } else {
                    System.out.println("aaaaaa");
                    String temp = s.toString();
                    String tem = temp.substring(temp.length() - 1, temp.length());
                    char[] temC = tem.toCharArray();
                    int mid = temC[0];
                    if (mid >= 65 && mid <= 90) {//大写字母
                        return;
                    } else if (mid >= 97 && mid <= 122) {//小写字母
                        return;
                    } else {
                        Toast.makeText(MainActivity.this, "请输入字母", Toast.LENGTH_SHORT).show();
                        s.delete(temp.length() - 1, temp.length());
                        return;
                    }
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
                final SQLiteDatabase db = helper.getReadableDatabase();
                String s = "select * from word";
                final Cursor cursor = db.rawQuery(s, null);
                final String word = word_in.getText().toString().trim();
                final String trans = translation.getText().toString().trim();
                final String sql = "insert into word(newword , translation) values(? , ?)";
                while (cursor.moveToNext()) {
                    String newword = cursor.getString(1);
                    String password = cursor.getString(2);
                    if (word.equals(newword) && (!password.equals(trans))) {
                        builder.setTitle("提示");
                        builder.setMessage("您所输入的单词：" + newword + "之前的翻译为：" +
                                password + ",您确定将其更替为：" + trans + "么？");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                word_in.setText("");
                                translation.setText("");
                                word_in.requestFocus();

                            }
                        });
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sql2 = "delete from word where newword = ?";
                                db.execSQL(sql2, new String[]{word});
                                db.execSQL(sql, new String[]{word, trans});
                                word_in.setText("");
                                translation.setText("");
                                word_in.requestFocus();
                                Toast.makeText(MainActivity.this, "此单词已成功保存！", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        cursor.moveToFirst();
                        break;
                    }
                }
                if (!cursor.isFirst()) {
                    db.execSQL(sql, new String[]{word, trans});
                    word_in.setText("");
                    translation.setText("");
                    word_in.requestFocus();
                    Toast.makeText(MainActivity.this, "此单词已成功保存！", Toast.LENGTH_SHORT).show();
                    cursor.moveToFirst();
                }
//                db.close();
//                cursor.close();
            }
        });

        word_sear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    return;
                } else {
                    System.out.println("aaaaaa");
                    String temp = s.toString();
                    String tem = temp.substring(temp.length() - 1, temp.length());
                    char[] temC = tem.toCharArray();
                    int mid = temC[0];
                    if (mid >= 65 && mid <= 90) {//大写字母
                        return;
                    } else if (mid >= 97 && mid <= 122) {//小写字母
                        return;
                    } else {
                        Toast.makeText(MainActivity.this, "请输入字母", Toast.LENGTH_SHORT).show();
                        s.delete(temp.length() - 1, temp.length());
                        return;
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
                final SQLiteDatabase db = helper.getReadableDatabase();
                String s = "select * from word";
                final Cursor cursor = db.rawQuery(s, null);
                String pre = view.getText().toString().trim();
                System.out.println("1111");
                String wordse = word_sear.getText().toString().trim();
                System.out.println("2222");

//                String newword = cursor.getString(1);
//                System.out.println("3333");
//                String password = cursor.getString(2);
//                System.out.println("4444");
                while(cursor.moveToNext()){
                    String newword = cursor.getString(1);
                   System.out.println("3333");
                    String password = cursor.getString(2);
                    System.out.println("4444");
                    if (newword.equals(wordse)) {
                        view.setText(password + "\n" + pre);
                        cursor.moveToFirst();
                        break;
                    }

                }
                if (!cursor.isFirst()) {
                    Toast.makeText(MainActivity.this, "您尚未存储这个单词！", Toast.LENGTH_SHORT).show();
                    word_sear.setText("");
                    word_sear.requestFocus();
                    cursor.moveToFirst();
                }
//                db.close();;
//                cursor.close();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
