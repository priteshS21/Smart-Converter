package com.hexasoftwares.smartconverter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SelectionScreen extends AppCompatActivity {

    ListView list;
    String[] titles;
    String[] description;
    int[] imgs = {R.drawable.area,R.drawable.digital_storage,R.drawable.frequency, R.drawable.length,R.drawable.mass,R.drawable.speed,R.drawable.temperature,
            R.drawable.time};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

            /* Adview code starts here*/
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        AdView adView = findViewById(R.id.adView);

        if (ni == null) {
            adView.setVisibility(View.GONE);
        } else {
            adView.setVisibility(View.VISIBLE);
        }
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
            /* Adview code ends here*/

        Resources res = getResources();
        titles = res.getStringArray(R.array.titles);
        description = res.getStringArray(R.array.description);
        list = findViewById(R.id.list1);
        MyAdapter adapter = new MyAdapter(this, titles, imgs, description);
        list.setAdapter(adapter);
        list.setOnItemClickListener(mMessageClickedHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent i = new Intent(SelectionScreen.this, Splash.class);
            startActivity(i);}
        if (id == R.id.action_exit) {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK)  return super.onKeyDown(keyCode, event);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setPositiveButton("Ok", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();

        return super.onKeyDown(keyCode, event);
    }

    /* Adapter class for list view */
    class MyAdapter extends ArrayAdapter {
        Context context;
        int[] images;
        String[] mytitles;
        String[] myDescriptions;

        MyAdapter(Context c, String[] titles, int imgs[], String[] desc) {
            super(c, R.layout.row, R.id.text1, titles);
            this.context = c;
            this.images = imgs;
            this.mytitles = titles;
            this.myDescriptions = desc;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row, parent, false);
            ImageView myImage = row.findViewById(R.id.icon);
            TextView myTitle = row.findViewById(R.id.text1);
            TextView myDesc = row.findViewById(R.id.text2);
            myImage.setImageResource(images[position]);
            myTitle.setText(mytitles[position]);
            myDesc.setText(myDescriptions[position]);
            return row;
        }
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            switch (position) {
                case 0: {Intent i = new Intent(SelectionScreen.this, Area.class);
                    startActivity(i);break;}
                case 1: {Intent i = new Intent(SelectionScreen.this, DigitalStorage.class);
                    startActivity(i);break;}
                case 2: {Intent i = new Intent(SelectionScreen.this, Frequency.class);
                    startActivity(i);break;}
                case 3: {Intent i = new Intent(SelectionScreen.this, Length.class);
                    startActivity(i);break;}
                case 4: {Intent i = new Intent(SelectionScreen.this, Mass.class);
                    startActivity(i);break;}
                case 5: {Intent i = new Intent(SelectionScreen.this, Speed.class);
                    startActivity(i);break;}
                case 6: {Intent i = new Intent(SelectionScreen.this, Temperature.class);
                    startActivity(i);break;}
                case 7: {Intent i = new Intent(SelectionScreen.this, Time.class);
                    startActivity(i);break;}
            }
        }
    };
}
