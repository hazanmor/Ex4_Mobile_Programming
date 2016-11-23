package com.example.barlesh.my_first_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Context;

/**
 * Created by barlesh on 09/11/16.
 */

public class ActivityAddJoke extends AppCompatActivity {
    //shared variables
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    // deffines
    private static final String LAST_VIEW = "last_view";
    private static final String ACTIVITY_MAIN = "main_activity";
    private static final String ACTIVITY_ADD= "add_activity";
    private static final String ACTIVITY_VIEW = "view_activity";
    private static final String ACTADD_TO_ACTMAIN_JOKE = "add_to_main_joke";
    private static final String ACTADD_TO_ACTMAIN_AUTHOR = "add_to_main_author";
    private static final String ACTADD_TO_ACTMAIN_DATE = "add_to_main_date";

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "Activity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // set current view at sharedreferances
        set_last_view();


        setContentView(R.layout.activity_add_joke);


    }

    // user clicked on add butten. send joke info to main anctivity
    public void sendJoke(View view){

        // add joke to sharedpreferences
        EditText editText = (EditText) findViewById(R.id.text_joke);
        String joke = editText.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTADD_TO_ACTMAIN_JOKE, joke);
        editor.commit();

        // add author to sharedpreferences
        editText = (EditText) findViewById(R.id.text_author);
        String author = editText.getText().toString();
        editor = sharedpreferences.edit();
        editor.putString(ACTADD_TO_ACTMAIN_AUTHOR, author);
        editor.commit();

        // add date to sharedpreferences
        editText = (EditText) findViewById(R.id.text_date);
        String date = editText.getText().toString();
        editor = sharedpreferences.edit();
        editor.putString(ACTADD_TO_ACTMAIN_DATE, date);
        editor.commit();

        finish();

    }


    public void set_last_view(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LAST_VIEW, ACTIVITY_ADD);
        editor.commit();
    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG ,"onStart()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG ,"onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG ,"onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG ,"onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG ,"onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG ,"onDestroy()");
    }
}