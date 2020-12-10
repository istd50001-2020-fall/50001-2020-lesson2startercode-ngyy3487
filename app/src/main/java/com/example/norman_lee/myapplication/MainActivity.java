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
    ExchangeRate exchangeRateBD;
    double exchangeRate;
    public final String TAG = "Logcat";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String RATE_KEY = "Rate_Key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConvert = findViewById(R.id.buttonConvert);
        buttonSetExchangeRate = findViewById(R.id.buttonSetExchangeRate);
        editTextValue = findViewById(R.id.editTextValue);
        textViewResult = findViewById(R.id.textViewResult);
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);

        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        String rate_text = mPreferences.getString(RATE_KEY,"2.95");
        textViewExchangeRate.setText(rate_text);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tocalc = editTextValue.getText().toString();
                try {
                    Utils.checkInvalidInputs(tocalc);
                    ExchangeRate exrate = new ExchangeRate(textViewExchangeRate.getText().toString());
                    BigDecimal result = exrate.calculateAmount(tocalc);
                    textViewResult.setText(result.toString());
                } catch(NumberFormatException ex){
                    Toast.makeText(MainActivity.this,"no input given",Toast.LENGTH_LONG);
                    Log.e(TAG,"no input given");
                } catch (IllegalArgumentException ex){
                    Toast.makeText(MainActivity.this,"invalid input",Toast.LENGTH_LONG);
                    Log.e(TAG,"invalid input");
                }

            }
        });

        buttonSetExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Log.i(TAG,intent.toString());
        String home = intent.getStringExtra("home");
        String foreign = intent.getStringExtra("foreign");
        //if app opened for first time, home and foreign is null
        if (home == null && foreign == null){
            exchangeRateBD = new ExchangeRate();
        }
        else {
            exchangeRateBD = new ExchangeRate(home, foreign);
            String exchangeRateNumber = exchangeRateBD.getExchangeRate().toString();
            textViewExchangeRate.setText(exchangeRateNumber);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


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

        if(id == R.id.setexchangerate_option){
            Intent intent = new Intent(MainActivity.this,SubActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.openmap){
            String location = getString(R.string.default_location);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("geo").opaquePart("0.0").appendQueryParameter("q",location);
            Uri geoLocation = builder.build();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        Log.i(TAG,"restart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"destroyed");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(TAG,"stop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i(TAG,"pause");
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String exrate = textViewExchangeRate.getText().toString();
        preferencesEditor.putString(RATE_KEY,exrate);

        preferencesEditor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"resume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG,"start");
        super.onStart();
    }

}
