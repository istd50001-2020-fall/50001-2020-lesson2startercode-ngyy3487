package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    Button buttonBackToCalculator;
    EditText editTextSubValueOfA;
    EditText editTextSubValueOfB;
    public final static String INTENT_EXCH_RATE = "Exchange Rate";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.subsharedprefs";
    public final static String HOME_KEY = "HOME_KEY";
    public final static String FOREIGN_KEY = "FOREIGN_KEY";
    public static final String SUB_ACT = "SUB_ACTIVITY_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        buttonBackToCalculator = findViewById(R.id.buttonBackToCalculator);
        editTextSubValueOfA = findViewById(R.id.editTextSubValueA);
        editTextSubValueOfB = findViewById(R.id.editTextSubValueB);

        buttonBackToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String home = editTextSubValueOfA.getText().toString();
                String foreign = editTextSubValueOfB.getText().toString();
                try{
                    Utils.checkInvalidInputs(home);
                    Utils.checkInvalidInputs(foreign);
                    Intent intent = new Intent(SubActivity.this, MainActivity.class);
                    intent.putExtra("home",home);
                    intent.putExtra("foreign",foreign);
                    startActivity(intent);
                }catch(NumberFormatException ex){
                    Toast.makeText(SubActivity.this, "empty string",Toast.LENGTH_LONG);
                    Log.e(SUB_ACT,"empty string");
                }catch(IllegalArgumentException ex){
                    Toast.makeText(SubActivity.this,"invalid input",Toast.LENGTH_LONG);
                    Log.e(SUB_ACT,"invalid input");
                }

            }
        });

    }

    //TODO 4.10 Don't forget to override onPause()

}
