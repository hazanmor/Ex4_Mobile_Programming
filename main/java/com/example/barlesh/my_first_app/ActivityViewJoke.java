package com.example.barlesh.my_first_app;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityViewJoke extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //shared variables
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    private Menu mMenu;

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

    private static final String SHUTDOWN = "shutdown";
    private static final String SHUTDOWN_REG_RUN = "shutdown_no";
    private static final String SHUTDOWN_FOLD = "shutdown_yes";

    private static final String BG_VIEW = "background_view";
    private static final String BG_BLUE = "bg_blue";
    private static final String BG_GREEN = "bg_green";
    private static final String BG_RED = "bg_red";

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

        print_background();


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
        Button button=(Button ) findViewById(R.id.done_editing_joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.ShowJoke);
                String joke = editText.getText().toString();

                if (joke.length() < 10) { create_edit_warning_dialog(); }
                else {create_edit_dialog();}



            }
        });


    }

    void create_edit_warning_dialog(){
        Dialog dialog = new Dialog(ActivityViewJoke.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(R.string.warning);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("The joke is too short!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.alert_dialog_icon);
        dialog.show();
    }

    void create_edit_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityViewJoke.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.edit_alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditJoke();
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




    public void EnableEditJoke(View view){
        EditText editText = (EditText) findViewById(R.id.ShowJoke);
        editText.setEnabled(true);
    }

    // once edit buttec clicked, send eddited data back to main activity
    public void EditJoke(){
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



    public void change_background(String BG_STR){
        set_background(BG_STR);
        print_background();
    }

    public void set_background(String BG_STR){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BG_VIEW, BG_STR);
        editor.commit();
    }


    public void print_background(){
        LinearLayout LL = (LinearLayout)findViewById(R.id.view_layout);;
        String bg = sharedpreferences.getString(BG_VIEW, null);
        if( bg== null) { return; }
        if( BG_BLUE.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppBlue) ); Log.d(TAG,"print_bg:blue"); return; }
        if( BG_RED.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppRed) ); Log.d(TAG,"print_bg:red"); return; }
        if( BG_GREEN.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppGreen) ); Log.d(TAG,"print_bg:green"); return; }


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

    void create_exit_dialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityViewJoke.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.exit_alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, "exit");
                        exit_app();
					/* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, "do nothing");

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


    //** Called when the user clicks the Send button */
   /* public void toActivity3(View view) {
        Intent intent = new Intent(this, Activity3.class);
        EditText editText = (EditText) findViewById(R.id.edit_message2);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}
