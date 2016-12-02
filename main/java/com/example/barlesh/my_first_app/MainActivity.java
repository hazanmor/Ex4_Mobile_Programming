package com.example.barlesh.my_first_app;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Context;
import android.app.ListActivity;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;


class Joke{
    private String Joke_str;
    private String Author;
    private String date;
    private String Like ="dont_care";

    //constructor

    Joke(String joke, String author, String Date) { Joke_str = joke; Author = author; date = Date; }
    //getters
    public String get_date(){ return date;  }
    public String get_joke() { return Joke_str;}
    public String get_author() { return Author;}
    public String get_like() { return Like; }

    //setters
    public void set_joke(String joke){ Joke_str = joke; }
    public void set_author(String auth){ Author = auth; }
    public void set_like(String like){ Like = like; }
    public void set_date( String update) { date = update; }


}


public class MainActivity extends AppCompatActivity {
    //shared variables
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    // defines
    // inter activities deffines
    private static final String LAST_VIEW = "last_view";

    private static final String BG_MAIN = "background";
    private static final String BG_BLUE = "bg_blue";
    private static final String BG_GREEN = "bg_green";
    private static final String BG_RED = "bg_red";
    private static final String ACTIVITY_MAIN = "main_activity";
    private static final String ACTIVITY_ADD= "add_activity";
    private static final String ACTIVITY_VIEW = "view_activity";
    private static final String ACTADD_TO_ACTMAIN_JOKE = "add_to_main_joke";
    private static final String ACTADD_TO_ACTMAIN_AUTHOR = "add_to_main_author";
    private static final String ACTADD_TO_ACTMAIN_DATE = "add_to_main_date";
    private static final String ACTMAIN_TO_ACTVIEW_JOKE = "main_to_active_joke";
    private static final String ACTMAIN_TO_ACTVIEW_LIKE = "main_to_active_like";
    private static final String ACTMAIN_ACTVIEW_POS= "main_view_pos";
    private static final String ACTVIEW_TO_ACTMAIN_JOKE = "view_to_main_joke";
    private static final String ACTVIEW_TO_ACTMAIN_LIKE = "view_to_main_like";

    private static final String TAG = "Activity Main";

    private static final String SHUTDOWN = "shutdown";
    private static final String SHUTDOWN_REG_RUN = "shutdown_no";
    private static final String SHUTDOWN_FOLD = "shutdown_yes";


    private static final String JOKE = "joke";
    private static final String PUNCH_LINE = "punch_line";
    private static final String AUTHOR = "author";
    private static final String DATE = "date";
    private static final String LIKE = "like";
    private static final String L_DONTCARE = "dont_care";
    private static final String L_LIKE = "like";
    private static final String L_DISLIKE = "dislike";


    ListView lv;
    ArrayList<Joke> jokes;
    CustomAdapter mainAdapter;


    /**
     * Safe to hold on to this.
     */
    private Menu mMenu;



    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //public final static String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";



    /********************************************************************************
     * Life Cycle function ( interface of AppCompactActivity)
     *******************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "BEFOR PRINT_BG");
        print_background();

        /**
         * set list of jikes
         */
        jokes = new ArrayList<Joke>();
        /*for (int i=0; i< 11; i++){
            jokes.add(new Joke("joke number" + i, "Bar", "12.12.12"));
        }*/
        //jokes.add(new Joke("joke number1", "Bar", "12.12.12"));
        /*jokes.add(new Joke("Q: Why was the math book sad? A: Because it had too many problems. \n" +
                "\n" , "anat", "12.12.12"));*/
        //jokes.add(new Joke("joke number3", "mor", "12.12.12"));
        lv = (ListView)findViewById(android.R.id.list);
        registerForContextMenu(lv);
        // b = (Button) findViewById(R.id.item_Exit);
        //registerForContextMenu( b );

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                ToViewJoke(position);
                //Toast.m akeText(MainActivity.this,jokes.get(position).get_joke() +" "+ jokes.get(position).get_author(), Toast.LENGTH_LONG).show();
            }
        });


       // print_jokes_to_screen();
        TextView  tv = (TextView)findViewById(R.id.main_act_text);
        tv.setText(R.string.no_jokes);
        Log.d(TAG, "after set text");

        mainAdapter = new CustomAdapter(MainActivity.this, R.layout.list_item_icon, jokes);
        Log.d(TAG, "BEFOR set adapter");
        lv.setAdapter(mainAdapter);

        // set current view at sharedreferances
        set_last_view();

    }



    @Override
    protected void onResume(){
        super.onResume();

        // get SHUTDOWN flag, if deffined FOLD, close mainActivity
        String shutown = sharedpreferences.getString(SHUTDOWN, null);
        if( shutown != null && ( shutown.compareTo(SHUTDOWN_FOLD) ==0 ) ){
            // reset flag
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(SHUTDOWN, null);
            editor.commit();

            finish();
        }


        String lastView = sharedpreferences.getString(LAST_VIEW, null);
        Log.d(TAG ,"onResume()");
        Log.d(TAG ,"last view was:" + lastView);
        // came from add joke activity
        if( lastView.compareTo(ACTIVITY_ADD) == 0){
            final String Joke = sharedpreferences.getString(ACTADD_TO_ACTMAIN_JOKE, null);
            final String Author = sharedpreferences.getString(ACTADD_TO_ACTMAIN_AUTHOR, null);
            final String Date = sharedpreferences.getString(ACTADD_TO_ACTMAIN_DATE, null);

            if( Joke == null || Author == null || Date == null){
                // bad joke, didnt get all info TODO

                // all strings were given
            }else{
                Log.d(TAG, "adding joke");
                jokes.add(new Joke(Joke, Author, Date));
                Log.d(TAG, "before printing to screeen");
                print_jokes_to_screen();
                reset_add_params();
            }

            // came from view/edit activity
        }else if(lastView.compareTo(ACTIVITY_VIEW) == 0){
            Log.d(TAG ,"inside dealing return from activity voew:" + lastView);
            final String J = sharedpreferences.getString(ACTVIEW_TO_ACTMAIN_JOKE, "none");
            final String L = sharedpreferences.getString(ACTVIEW_TO_ACTMAIN_LIKE, "none");
            final String POS = sharedpreferences.getString(ACTMAIN_ACTVIEW_POS, "none");
            int poss = Integer.parseInt(POS);

            // case activity view sent us edited data
            if( J.compareTo("none") !=0 && L.compareTo("none") !=0){
                Log.d(TAG ,"got Joke:" + J + "\nand Like:" + L);
                jokes.get(poss).set_joke(J);
                jokes.get(poss).set_like(L);


                reset_edit_params();
                print_jokes_to_screen();
            }

        }

        set_last_view();

        // set text at top of screen
        set_main_text();


    }



    /********************************************************************************
     * internal functions
     *******************************************************************************/


    /**
     * change background of activity.
     * set bg param at sharedpreferences
     * then call function to set bg according to param at sharedpreferences
     * @param BG_STR
     */
    public void change_background(String BG_STR){
        set_background(BG_STR);
        print_background();
    }


    /**
     * change view's bg color
     * get Prime LinearLayout, and set it bg color according to value set at sharedpreferences
     */
    public void print_background(){
        LinearLayout LL = (LinearLayout)findViewById(R.id.main_layout);;
        String bg = sharedpreferences.getString(BG_MAIN, null);
        if( bg== null) { return; }
        if( BG_BLUE.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppBlue) ); return; }
        if( BG_RED.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppRed) ); return; }
        if( BG_GREEN.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppGreen) ); return; }


    }


    /**
     * create dialog when user click exit butten.
     * exit app if dialog returned positive answer, continue if negative
     */
    void create_exit_dialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.edit_alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, "edit");
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


    /**
     * convert jokes array into
     */
    void print_jokes_to_screen(){
        TextView tv;
        if ( jokes.size() == 0 ){
            tv = (TextView)findViewById(R.id.main_act_text);
            tv.setText(R.string.no_jokes);
        }else {
            tv = (TextView)findViewById(R.id.main_act_text);
            Log.d(TAG,"jokes.size > 0");
            tv.setText(R.string.info);
        }
        mainAdapter.notifyDataSetChanged();
        //mainAdapter.setNotifyOnChange(true);
        //CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.list_item_icon, jokes);
        //lv.setAdapter(mainAdapter);
    }

    /**
     * set Activities-shared parameter last view to be current view (Activity)
     */
    void set_last_view(){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LAST_VIEW, ACTIVITY_MAIN);
        editor.commit();
    }

    /**
     *  set text at head of Activity View, according to jokes array size
     */
    void set_main_text(){
        TextView TV = (TextView) findViewById(R.id.main_act_text);
        if (jokes.size() == 0){
            TV.setText(getResources().getString(R.string.no_jokes));
        }else{
            TV.setText(R.string.info);
        }

    }

    /**
     * set Activity's parameter (at sharedpreferences) of bg color according to given string
     */
    public void set_background(String BG_STR){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BG_MAIN, BG_STR);
        editor.commit();
    }

    /**
     * reset parameters incoming from edit/view Activity, so next time we wont get irrelevant data
     */
    public void reset_edit_params(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTVIEW_TO_ACTMAIN_JOKE, null);
        editor.commit();
        editor.putString(ACTVIEW_TO_ACTMAIN_LIKE, null);
        editor.commit();

    }

    /**
     * reset parameters incoming from add Activity, so next time we wont get irrelevant data
     */
    public void reset_add_params(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTADD_TO_ACTMAIN_JOKE, null);
        editor.commit();
        editor.putString(ACTADD_TO_ACTMAIN_AUTHOR, null);
        editor.commit();
        editor.putString(ACTADD_TO_ACTMAIN_DATE, null);
        editor.commit();

    }

    /**
     * reset exit parameter
     */
    void reset_exit_flag(){
        // set sutdown flag to no
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SHUTDOWN, SHUTDOWN_REG_RUN);
        editor.commit();
    }

    /** exit app.
     *  reset exit flag of sharedpreferences so next time app will not exit after exemine flag
      */
    void exit_app(){
        reset_exit_flag();
        finish();

    }



    /********************************************************************************
     * Activities transition functions
     *******************************************************************************/

    public void ToViewJoke(int pos){
        Intent intent = new Intent(this, ActivityViewJoke.class);
        // get joke
        String Joke = jokes.get(pos).get_joke();
        String Like = jokes.get(pos).get_like();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTMAIN_TO_ACTVIEW_JOKE, Joke);
        editor.commit();
        editor.putString(ACTMAIN_TO_ACTVIEW_LIKE, Like);
        editor.commit();
        editor.putString(ACTMAIN_ACTVIEW_POS, new Integer(pos).toString());
        editor.commit();

        startActivity(intent);
    }

    public void TOaddNewJoke(){
        Intent intent = new Intent(this, ActivityAddJoke.class);
        startActivity(intent);
    }
    public void TOaddNewJoke(View view){
        Intent intent = new Intent(this, ActivityAddJoke.class);
        startActivity(intent);
    }


    public void TODeleteJoke(int pos)
    {
       jokes.remove(pos);
        print_jokes_to_screen();

    }


    /********************************************************************************
     * override functions, to handle menus, context menus, dialogs etc...
     *******************************************************************************/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //super.onOptionsItemSelected(item);
        LinearLayout LL = (LinearLayout)findViewById(R.id.main_layout);;
        switch (item.getItemId()) {

            case R.id.item_add_joke:
                Toast.makeText(this, "add new joke!!!!", Toast.LENGTH_SHORT).show();
                TOaddNewJoke();
                return true;
            case R.id.item_Exit:
                Log.d(TAG, "onOptionsItemSelected: item clicked:item_Exit");
                Toast.makeText(this, "Exit app!!!!", Toast.LENGTH_SHORT).show();
                create_exit_dialog();

                // pop exit verification dialog

                return true;
            // background color change
            case R.id.item_bg_blue:
                Log.d(TAG, "item_bg_blue");
                //LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppBlue) );
                change_background(BG_BLUE);
                return true;
            case R.id.item_bg_red:
                Log.d(TAG, "item_bg_red");
                //LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppRed) );
                change_background(BG_RED);
                return true;
            case R.id.item_bg_green:
                Log.d(TAG, "item_bg_green");
                //LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppGreen) );
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



    // take care of all context view registered for currrent activity
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
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




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        int index;
        Log.d(TAG, "onContextItemSelected:");

        Log.d(TAG, "onContextItemSelected:item's id:" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.edit:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                index = info.position;
                ToViewJoke(index);
                return true;
            case R.id.delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                index = info.position;
                TODeleteJoke(index);
                return true;
            case R.id.Exit_confirm:
                Log.d(TAG, "onContextItemSelected:Exit_confirm");
                exit_app();
                return true;
            case R.id.Exit_cancel:
                Log.d(TAG, "onContextItemSelected:Exit_cancel");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);

        // Hold on to this
        mMenu = menu;

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_full, mMenu);

        return true;
    }







    /********************************************************************************
     * Costume Adapter. a class to create adapter instance to pass to list
     *******************************************************************************/

    class CustomAdapter extends ArrayAdapter<Joke> {

        Context context;
        int layoutResourceId;
        ArrayList<Joke> data = null;
        private LayoutInflater mInflater;

        private static final String L_DONTCARE = "dont_care";
        private static final String L_LIKE = "like";
        private static final String L_DISLIKE = "dislike";

        public CustomAdapter(Context context, int layoutResourceId, ArrayList<Joke> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            this.mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                //item_list
                convertView = mInflater.inflate(R.layout.list_item_icon, null);

                holder = new ViewHolder();

                //fill the views
                holder.joke = (TextView) convertView.findViewById(R.id.ListTextView01);
                holder.icon = (ImageView) convertView.findViewById(R.id.ListLikeIcon);


                convertView.setTag(holder);
            }
            else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();//
            }

            int icon2 = 0;
            holder.joke.setText(data.get(position).get_joke());
            if(data.get(position).get_like().compareTo(L_DONTCARE) == 0 ){
                icon2 = R.drawable.imgdontcare;
            }else if(data.get(position).get_like().compareTo(L_LIKE) == 0 ){
                icon2 = R.drawable.imglike;
            }else if(data.get(position).get_like().compareTo(L_DISLIKE) == 0 ){
                icon2 = R.drawable.imgdislike;
            }else{
                icon2=0;
            }

            holder.icon.setImageResource(icon2);


            return convertView;
        }

        class ViewHolder {
            TextView joke;
            ImageView icon;

        }
    }



}









