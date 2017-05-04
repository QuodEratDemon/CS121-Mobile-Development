package com.dealfaro.luca.backandforthstudio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    AppInfo appInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        // Writes the string from main activity.
        SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
        String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(myText);

        // and the one from the singleton object
        TextView tv2 = (TextView) findViewById(R.id.textView3);
        tv2.setText(appInfo.sharedString);
        */
        SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
        //String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        EditText edv = (EditText) findViewById(R.id.editText1);
        if (appInfo.s3 != null) {
            edv.setText(appInfo.s3);
        }

        TextView edv1 = (TextView) findViewById(R.id.textACTIVITY2);
        if (appInfo.s1 != null) {
            edv1.setText(appInfo.s1);
        }

        TextView edv2 = (TextView) findViewById(R.id.textACTIVITY3);
        if (appInfo.s2 != null) {
            edv2.setText(appInfo.s2);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void clickBack(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void clickGoFirst(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void clickEnter3(View v){
        EditText edv = (EditText) findViewById(R.id.editText1);
        String text1 = edv.getText().toString();
        appInfo.setString3(text1);
        //entered = true;
    }

    public void clickGoSecond(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
