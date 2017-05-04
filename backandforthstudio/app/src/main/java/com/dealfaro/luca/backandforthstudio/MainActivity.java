package com.dealfaro.luca.backandforthstudio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_STRING_1 = "string_1";

    //public boolean entered = false;

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
        //String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        EditText edv = (EditText) findViewById(R.id.editText1);
        if (appInfo.s1 != null) {
            edv.setText(appInfo.s1);
        }

        TextView edv2 = (TextView) findViewById(R.id.textACTIVITY2);
        if (appInfo.s2 != null) {
            edv2.setText(appInfo.s2);
        }

        TextView edv3 = (TextView) findViewById(R.id.textACTIVITY3);
        if (appInfo.s3 != null) {
            edv3.setText(appInfo.s3);
        }


    }

    public void goOther(View V) {
        // Grab the text, and store it in a preference.
        EditText edv = (EditText) findViewById(R.id.editText1);
        String text1 = edv.getText().toString();
        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_STRING_1, text1);
        editor.commit();

        // The second string we store it in the singleton class.
        /*
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2);
        */

        // Go to second activity
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void clickEnter1(View v){
        EditText edv = (EditText) findViewById(R.id.editText1);
        String text1 = edv.getText().toString();
        appInfo.setString1(text1);
        //entered = true;
    }

    public void clickGoSecond(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void clickGoThird(View v){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
