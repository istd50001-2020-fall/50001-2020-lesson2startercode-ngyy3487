package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Button buttonConvert;
    Button buttonSetExchangeRate;
    EditText editTextValue;
    TextView textViewResult;
    TextView textViewExchangeRate;
    double exchangeRate;
    public final String TAG = "Logcat";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String RATE_KEY = "Rate_Key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValue = findViewById(R.id.editTextValue);
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);
        buttonSetExchangeRate = findViewById(R.id.buttonSetExchangeRate);

        //TODO 4.5 Get a reference to the sharedPreferences object
        //TODO 4.6 Retrieve the value using the key, and set a default when there is none
        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        textViewExchangeRate.setText(mPreferences.getString(RATE_KEY,"2.95"));

        //TODO 3.13 Get the intent, retrieve the values passed to it, and instantiate the ExchangeRate class
        //TODO 3.13a See ExchangeRate class --->
        Intent subactivity = getIntent();
        String home = subactivity.getStringExtra("home");
        String foreign = subactivity.getStringExtra("foreign");
        if (home != null && foreign!= null) {
            ExchangeRate subexchangerate = new ExchangeRate(home, foreign);
            textViewExchangeRate.setText(subexchangerate.getExchangeRate().toString());
        }
        //TODO 2.1 Use findViewById to get references to the widgets in the layout
        //TODO 2.2 Assign a default exchange rate of 2.95 to the textView
        //TODO 2.3 Set up setOnClickListener for the Convert Button
        //TODO 2.4 Display a Toast & Logcat message if the editTextValue widget contains an empty string
        //TODO 2.5 If not, calculate the units of B with the exchange rate and display it
        //TODO 2.5a See ExchangeRate class --->

        exchangeRate = Double.valueOf(textViewExchangeRate.getText().toString());

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numbertoconvert = editTextValue.getText().toString();
                try{
                    ExchangeRate exchangecalc = new ExchangeRate(String.valueOf(exchangeRate));
                    BigDecimal result = exchangecalc.calculateAmount(numbertoconvert);
                    textViewResult.setText(String.valueOf(result));
                }
                catch(NumberFormatException ex){
                    Toast.makeText(MainActivity.this,R.string.emptyedittext,Toast.LENGTH_LONG).show();
                    Log.w("emptyedittext","Empty Edit Text");
                }
            }
        });


        //TODO 3.1 Modify the Android Manifest to specify that the parent of SubActivity is MainActivity
        //TODO 3.2 Get a reference to the Set Exchange Rate Button
        //TODO 3.3 Set up setOnClickListener for this
        //TODO 3.4 Write an Explicit Intent to get to SubActivity
        buttonSetExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //TODO 4.1 Go to res/menu/menu_main.xml and add a menu item Set Exchange Rate
    //TODO 4.2 In onOptionsItemSelected, add a new if-statement and code accordingly

    //TODO 5.1 Go to res/menu/menu_main.xml and add a menu item Open Map App
    //TODO 5.2 In onOptionsItemSelected, add a new if-statement
    //TODO 5.3 code the Uri object and set up the intent

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.setexchangerate_option){
            Intent intent = new Intent(MainActivity.this,SubActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.openmap){
            String location = getString(R.string.default_location);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("geo").opaquePart("0.0").appendQueryParameter("q",location);
            Uri geolocation = builder.build();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geolocation);
            if (intent.resolveActivity(getPackageManager())!= null){
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO 4.3 override the methods in the Android Activity Lifecycle here
    //TODO 4.4 for each of them, write a suitable string to display in the Logcat

    @Override
    protected void onRestart() {
        Log.i("lifecycle","restart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i("lifecycle","destroyed");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i("lifecycle","stop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i("lifecycle","pause");
        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        SharedPreferences.Editor prefeditor = mPreferences.edit();
        prefeditor.putString(RATE_KEY,textViewExchangeRate.getText().toString());
        prefeditor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("lifecycle","resume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("lifecycle","start");
        super.onStart();
    }
    //TODO 4.7 In onPause, get a reference to the SharedPreferences.Editor object
    //TODO 4.8 store the exchange rate using the putString method with a key

}
