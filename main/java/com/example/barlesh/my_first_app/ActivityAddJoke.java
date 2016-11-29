package com.example.barlesh.my_first_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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

    private static final String SHUTDOWN = "shutdown";
    private static final String SHUTDOWN_REG_RUN = "shutdown_no";
    private static final String SHUTDOWN_FOLD = "shutdown_yes";

    private static final String BG_ADD = "background_add";
    private static final String BG_BLUE = "bg_blue";
    private static final String BG_GREEN = "bg_green";
    private static final String BG_RED = "bg_red";

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "Activity3";

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // set current view at sharedreferances
        set_last_view();


        setContentView(R.layout.activity_add_joke);

        Button button=(Button ) findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.text_joke);
                String joke = editText.getText().toString();

                if (joke.length() < 10) { create_edit_warning_dialog(); }
                else {create_edit_dialog();}
            }
        });

    }


    void create_edit_warning_dialog(){
        Dialog dialog = new Dialog(ActivityAddJoke.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(R.string.warning);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("The joke is too short!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.alert_dialog_icon);
        dialog.show();
    }

    void create_edit_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddJoke.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.edit_alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sendJoke();
					/* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, "do nothing");
                        finish();

					/* User clicked Cancel so do some stuff */
                    }
                });
        Dialog saveJokeDialog = builder.create();
        saveJokeDialog.show();
    }


    /**
     * TODOOOOO
     * @param menu
     * @param v
     * @param menuInfo
     */
    // take care of all context view registered for currrent activity
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG,"onCreateContextMenu");
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu, view is:" + v.toString());

        MenuInflater inflater = getMenuInflater();
        switch (v.getId() ){
            case R.id.fake_button:
                Log.d(TAG, "onCreateContextMenu: fake_button");
                inflater.inflate(R.menu.exit_confirm_context_menu, menu);
                break;
            case android.R.id.list:
                inflater.inflate(R.menu.context_menu, menu);
                break;
        }

    }




    // user clicked on add butten. send joke info to main anctivity
    public void sendJoke(){

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

    void set_exit_flag(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SHUTDOWN, SHUTDOWN_FOLD);
        editor.commit();
    }
    void exit_app(){
        set_exit_flag();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);

        // Hold on to this
        mMenu = menu;

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_partial, mMenu);

        return true;
    }


    public void change_background(String BG_STR){
        set_background(BG_STR);
        print_background();
    }

    public void set_background(String BG_STR){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BG_ADD, BG_STR);
        editor.commit();
    }


    public void print_background(){
        LinearLayout LL = (LinearLayout)findViewById(R.id.add_layout);;
        String bg = sharedpreferences.getString(BG_ADD, null);
        if( bg== null) { return; }
        if( BG_BLUE.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppBlue) ); Log.d(TAG,"print_bg:blue"); return; }
        if( BG_RED.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppRed) ); Log.d(TAG,"print_bg:red"); return; }
        if( BG_GREEN.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppGreen) ); Log.d(TAG,"print_bg:green"); return; }


    }

    void create_exit_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddJoke.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.edit_alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sendJoke();
					/* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, "do nothing");
                        finish();

					/* User clicked Cancel so do some stuff */
                    }
                });
        Dialog saveJokeDialog = builder.create();
        saveJokeDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.item_Exit2:
                Toast.makeText(this, "Exit app!!!!", Toast.LENGTH_SHORT).show();
                create_exit_dialog();
                return true;
            // background color change
            case R.id.item_bg_blue2:
                Log.d(TAG, "item_bg_blue");
                change_background(BG_BLUE);
                return true;
            case R.id.item_bg_red2:
                Log.d(TAG, "item_bg_red");
                change_background(BG_RED);
                return true;
            case R.id.item_bg_green2:
                Log.d(TAG, "item_bg_green");
                change_background(BG_GREEN);
                return true;
            // Generic catch all for all the other menu resources
            default:
                // Don't toast text when a submenu is clicked
                if (!item.hasSubMenu()) {
                    Log.d(TAG, "other");
                    Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }

        return false;
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