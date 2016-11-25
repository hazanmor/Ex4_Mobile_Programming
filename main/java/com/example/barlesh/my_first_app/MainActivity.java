package com.example.barlesh.my_first_app;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
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


    /**
     * Safe to hold on to this.
     */
    private Menu mMenu;



    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //public final static String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        print_background();

        /**
         * set list of jikes
         */
        jokes = new ArrayList<Joke>();
        jokes.add(new Joke("joke number1", "Bar", "12.12.12"));
        jokes.add(new Joke("joke number2", "anat", "12.12.12"));
        jokes.add(new Joke("joke number3", "mor", "12.12.12"));
        lv = (ListView)findViewById(android.R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                ToViewJoke(position);
                //Toast.m akeText(MainActivity.this,jokes.get(position).get_joke() +" "+ jokes.get(position).get_author(), Toast.LENGTH_LONG).show();
            }
        });
        print_jokes_to_screen();


        // set current view at sharedreferances
        set_last_view();




        // Add Joke Butten
        // take us to add joke activity
        // no information provided
        /*Button customList = (Button)findViewById(R.id.buttonToAddJoke);
        customList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAddJoke.class);

                startActivity(intent);
            }
        });*/


    }

    public void change_background(String BG_STR){
        set_background(BG_STR);
        print_background();
    }

    public void set_background(String BG_STR){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BG_MAIN, BG_STR);
        editor.commit();
    }


    public void print_background(){
        LinearLayout LL = (LinearLayout)findViewById(R.id.main_layout);;
        String bg = sharedpreferences.getString(BG_MAIN, null);
        if( bg== null) { return; }
        if( BG_BLUE.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppBlue) ); return; }
        if( BG_RED.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppRed) ); return; }
        if( BG_GREEN.compareTo(bg) == 0 ) { LL.setBackgroundColor( ContextCompat.getColor(this, R.color.AppGreen) ); return; }


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
                Toast.makeText(this, "Exit app!!!!", Toast.LENGTH_SHORT).show();
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





    public void print_jokes_to_screen(){
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.list_item_icon, jokes);
        lv.setAdapter(adapter);






    }



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

    public void set_last_view(){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LAST_VIEW, ACTIVITY_MAIN);
        editor.commit();
    }

    /** Called when the user clicks the Send button */
    /*public void toActivity2(View view) {
        Intent intent = new Intent(this, Activity2.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    /*public void toActivity3(String str) {
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }*/


    @Override
    protected void onResume(){
        super.onResume();

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
                jokes.add(new Joke(Joke, Author, Date));
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

    void set_main_text(){
        TextView TV = (TextView) findViewById(R.id.main_act_text);
        if (jokes.size() == 0){
            TV.setText(getResources().getString(R.string.no_jokes));
        }else{
            TV.setText("");
        }

    }

    public void reset_edit_params(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTVIEW_TO_ACTMAIN_JOKE, null);
        editor.commit();
        editor.putString(ACTVIEW_TO_ACTMAIN_LIKE, null);
        editor.commit();

    }

    public void reset_add_params(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ACTADD_TO_ACTMAIN_JOKE, null);
        editor.commit();
        editor.putString(ACTADD_TO_ACTMAIN_AUTHOR, null);
        editor.commit();
        editor.putString(ACTADD_TO_ACTMAIN_DATE, null);
        editor.commit();
    }




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

            MainActivity.CustomAdapter.ViewHolder holder = null;

            if (convertView == null) {

                //item_list
                convertView = mInflater.inflate(R.layout.list_item_icon, null);

                holder = new MainActivity.CustomAdapter.ViewHolder();

                //fill the views
                holder.joke = (TextView) convertView.findViewById(R.id.ListTextView01);
                holder.icon = (ImageView) convertView.findViewById(R.id.ListLikeIcon);


                convertView.setTag(holder);
            }
            else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (MainActivity.CustomAdapter.ViewHolder) convertView.getTag();//
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









