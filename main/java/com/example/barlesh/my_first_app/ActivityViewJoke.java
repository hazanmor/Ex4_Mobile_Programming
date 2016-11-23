package com.example.barlesh.my_first_app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;




public class ActivityViewJoke extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //shared variables
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";


    // deffines
    private static final String LAST_VIEW = "last_view";
    private static final String ACTIVITY_MAIN = "main_activity";
    private static final String ACTIVITY_ADD= "add_activity";
    private static final String ACTIVITY_VIEW = "view_activity";
    private static final String ACTADD_TO_ACTMAIN_JOKE = "add_to_main_joke";
    private static final String ACTADD_TO_ACTMAIN_AUTHOR = "add_to_main_author";
    private static final String ACTADD_TO_ACTMAIN_DATE = "add_to_main_date";
    private static final String ACTMAIN_TO_ACTVIEW_JOKE = "main_to_active_joke";
    private static final String ACTMAIN_TO_ACTVIEW_LIKE = "main_to_active_like";
    private static final String ACTVIEW_TO_ACTMAIN_JOKE = "view_to_main_joke";
    private static final String ACTVIEW_TO_ACTMAIN_LIKE = "view_to_main_like";

    private static final String L_DONTCARE = "dont_care";
    private static final String L_LIKE = "like";
    private static final String L_DISLIKE = "dislike";

    private String L_COND;

    private static final String TAG = "Activity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        setContentView(R.layout.activity_view_joke);



        // get joke from sharedpreferences
        final String Joke = sharedpreferences.getString(ACTMAIN_TO_ACTVIEW_JOKE, null);
        final String Like = sharedpreferences.getString(ACTMAIN_TO_ACTVIEW_LIKE, null);

        Log.d(TAG ,"onCreate Like is" + Like);

        // set text of joke and block editing
        EditText editText = (EditText) findViewById(R.id.ShowJoke);
        editText.setEnabled(false);
        editText.setText(Joke);


        set_like(Like);
        set_last_view();

        reset_shared_params();

    }


    public void EnableEditJoke(View view){
        EditText editText = (EditText) findViewById(R.id.ShowJoke);
        editText.setEnabled(true);
    }

    // once edit buttec clicked, send eddited data back to main activity
    public void EditJoke(View view){
        EditText editText = (EditText) findViewById(R.id.ShowJoke);
        String J = editText.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(ACTVIEW_TO_ACTMAIN_JOKE, J);
        editor.commit();
        Log.d(TAG ,"L_COND is:" + L_COND);
        editor.putString(ACTVIEW_TO_ACTMAIN_LIKE, L_COND);
        editor.commit();

        finish();


    }

    public void clickedDisLike(View view){
        set_like(L_DISLIKE);
        set_last_view();

    }

    public void clickedLike(View view){
        set_like(L_LIKE);
        set_last_view();
    }

    public void clickedDontCare(View view){
        set_like(L_DONTCARE);
        set_last_view();
    }

    // this function is called when one of Like/DisLIKE/DontCare button was clicked
    public void set_cond(View view) {

    }

    // paint bg of state fro user to know it is chosen
    public void set_like(String L){
        L_COND = L;
        set_like_bg();

    }

    public void set_like_bg(){
        LinearLayout ln_like  = (LinearLayout) this.findViewById(R.id.buttonLikeLayout);;
        LinearLayout ln_dislike = (LinearLayout) this.findViewById(R.id.buttonDisLikeLayout);;
        LinearLayout ln_dontcare = (LinearLayout) this.findViewById(R.id.buttonDontCareLayout);;

        ln_like.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ln_dislike.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ln_dontcare.setBackgroundColor(Color.parseColor("#FFFFFF"));
        if( L_COND.compareTo(L_LIKE) ==0 ){
            Log.d(TAG, "set like backgound to black");
            ln_like.setBackgroundColor(Color.parseColor("#000000"));
        }else  if( L_COND.compareTo(L_DISLIKE) ==0 ){
            Log.d(TAG, "set dislike backgound to black");
            ln_dislike.setBackgroundColor(Color.parseColor("#000000"));
        }else  if( L_COND.compareTo(L_DONTCARE) ==0 ){
            Log.d(TAG, "set dont care backgound to black");
            ln_dontcare.setBackgroundColor(Color.parseColor("#000000"));
        }else{
            Log.d(TAG, "shouldnt get here");
        }


    }

    public void set_last_view(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LAST_VIEW, ACTIVITY_VIEW);
        editor.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

       /* sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String str = sharedpreferences.getString(ShutDown, null);
        if (str != null && (str.compareTo("CloseApp") == 0)) {
            finish();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*Log.d(TAG, "onStop()");
        //save entered text at sharedPrefences
        EditText editText = (EditText) findViewById(R.id.edit_message2);
        String str = editText.getText().toString();
        Log.d(TAG, "str is:" + str);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("view_2_pass_string", str);
        editor.commit();*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    void reset_shared_params(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTMAIN_TO_ACTVIEW_JOKE, null);
        editor.commit();
        editor.putString(ACTMAIN_TO_ACTVIEW_LIKE, null);
        editor.commit();
    }


    //** Called when the user clicks the Send button */
   /* public void toActivity3(View view) {
        Intent intent = new Intent(this, Activity3.class);
        EditText editText = (EditText) findViewById(R.id.edit_message2);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}
