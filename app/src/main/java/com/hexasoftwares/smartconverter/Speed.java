package com.hexasoftwares.smartconverter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Speed extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int index_from, index_to;
    String item_from, item_to, stvalue;
    Double value;
    String[] CONV_OPTIONS_FROM = {"Unit from" ,"Kilometer per hour","Miles per hour","Meter per sec"};
    String[] CONV_OPTIONS_TO = {"Unit to" ,"Kilometer per hour","Miles per hour","Meter per sec"};
    String[] ITEM_SIGN = {"--","kmph","mph","mps"};
    EditText e1;
    TextView e2,tv_sign_from,tv_sign_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e1 = (EditText) findViewById(R.id.etv_from);
        e2 = (TextView) findViewById(R.id.etv_to);
        tv_sign_from = (TextView) findViewById(R.id.sign_from);
        tv_sign_to = (TextView) findViewById(R.id.sign_to);

        Spinner spinner_from = (Spinner) findViewById(R.id.spinner_from);
        ArrayAdapter<String> adapter_from = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CONV_OPTIONS_FROM);
        adapter_from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinner_from.setAdapter(adapter_from);
        spinner_from.setOnItemSelectedListener(this);// Apply the adapter to the spinner

        Spinner spinner_to = (Spinner) findViewById(R.id.spinner_to);
        ArrayAdapter<String> adapter_to = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CONV_OPTIONS_TO);
        adapter_to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinner_to.setAdapter(adapter_to);
        spinner_to.setOnItemSelectedListener(this); // Apply the adapter to the spinner

        /* Adview code starts here*/
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        AdView adView = (AdView) findViewById(R.id.adView);

        if (ni == null) {
            adView.setVisibility(View.GONE);
        } else {
            adView.setVisibility(View.VISIBLE);
        }
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
            /* Adview code ends here*/
    }

    /* Common functions starts here */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent i = new Intent(Speed.this, Splash.class);
            startActivity(i);}
        return super.onOptionsItemSelected(item);
    }

    /* Common functions ends here */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinner_from) {
            index_from = parent.getSelectedItemPosition();
            item_from = parent.getItemAtPosition(position).toString();
            tv_sign_from.setText(ITEM_SIGN[index_from]);
        } else if (spinner.getId() == R.id.spinner_to) {
            index_to = parent.getSelectedItemPosition();
            item_to = parent.getItemAtPosition(position).toString();
            tv_sign_to.setText(ITEM_SIGN[index_to]);
        }
    }

    public void RstAction(View view) {
        e1.setText("");
        e2.setText("0");
    }

    public void ConvAction(View view) {
        stvalue = e1.getText().toString();
        if (stvalue.equals("")) {
            value = 0.0;
        } else {
            value = Double.parseDouble(stvalue);
        }

        switch (index_from) {
            case 0: {Toast.makeText(this,"Please choose any option", Toast.LENGTH_LONG).show();break;}
            case 1: {switch (index_to) {
                case 0: {Toast.makeText(this,"Please choose any option", Toast.LENGTH_LONG).show();break;}
                case 2: {value = value*0.621371; /* kmph to mph*/ break;}
                case 3: {value = value*0.277778; /* Kmph to mtps*/ break;}
            }break;
            }

            case 2: {switch (index_to) {
                case 0: {Toast.makeText(this,"Please choose any option", Toast.LENGTH_LONG).show();break;}
                case 1: {value= value*1.60934;break;}
                case 3: {value =value*0.44704;break;}
            }break;
            }

            case 3: {switch (index_to) {
                case 0: {Toast.makeText(this,"Please choose any option", Toast.LENGTH_LONG).show();break;}
                case 1: {value = value* 3.6;break;}
                case 2: {value = value* 2.23694;break;}
            }break;
            }
        }

        if(index_from==0 && index_to==0) {e2.setText("0");}
        if(index_from==0 && index_to>0) {e2.setText("0");}
        if(index_from<0 && index_to==0) {e2.setText("0");}
        else if (value==0) {e2.setText("0");}
        else{e2.setText(Double.toString(value));}
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}