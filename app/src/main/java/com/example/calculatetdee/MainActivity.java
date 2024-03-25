package com.example.calculatetdee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner spActivityLevel;
    private Switch swActivityFactor;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize spinner
        spActivityLevel = findViewById(R.id.spActivityLevel);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activity_levels, R.layout.spinner_item);
        spActivityLevel.setAdapter(adapter);


        // set listener to the Activity Factor switch
        swActivityFactor = findViewById(R.id.swActivityFactor);
        swActivityFactor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // if toggled on, enable, otherwise disable Activity Level spinner
                if (isChecked){
                    spActivityLevel.setEnabled(true);   // enable spinner
                    spActivityLevel.setAlpha(1f);   // reset transparency
                }else {
                    spActivityLevel.setEnabled(false);  // disable the spinner
                    spActivityLevel.setAlpha(0.2f); // change transparency to make it looked disabled
                }
            }
        });
    }

    public void calculateTDEE (View view){
        //read the inserted values
        // read gender
        String gender;
        RadioButton rbMale = findViewById(R.id.rbMale);

        if (rbMale.isChecked())
            gender = "Male";
        else
            gender = "Female";

        // read height
        EditText etHeight = findViewById(R.id.etHeight);
        int height = Integer.parseInt(etHeight.getText().toString());

        // read weight
        EditText etWeight = findViewById(R.id.etWeight);
        double weight = Double.parseDouble(etWeight.getText().toString());

        // read age
        EditText etAge = findViewById(R.id.etAge);
        int age = Integer.parseInt(etAge.getText().toString());

        // Calculate BMR
        double BMR = 0;
        if (gender.equals("Male"))
            BMR = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        else
            BMR = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);

        // check Activity Factor
        double TDEE = BMR;
        double factor = 1;

        if (swActivityFactor.isChecked()) {
            // read Spinner
            int selectedIdx = spActivityLevel.getSelectedItemPosition();

            switch (selectedIdx)
            {
                case 0:
                    factor = 1.2;
                    break;
                case 1:
                    factor = 1.375;
                    break;
                case 2:
                    factor = 1.55;
                    break;
                case 3:
                    factor = 1.725;
                    break;
                case 4:
                    factor = 1.9;
                    break;
            }
        }
        // Multiply by the factor
        TDEE = TDEE * factor;

        TextView tvResult = findViewById(R.id.tvResult);
        tvResult.setText("You need to consume " + (int)TDEE + " calories per day to maintain your current weight.");
    }
}