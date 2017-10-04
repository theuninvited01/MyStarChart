package com.example.nathanielwarren.mystarchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class getBirthData extends AppCompatActivity {

    DatePicker birthDatePicker;
    TimePicker birthTimePicker;
    TextView birthDataDisplay;
    Button submitBirthDataBtn;
    Intent backToMain;
    Bundle birthData;
    String birthDate;
    String birthTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_birth_data);

        birthDatePicker = (DatePicker)findViewById(R.id.birthDatePicker);
        birthTimePicker = (TimePicker)findViewById(R.id.birthTimePicker);
        birthDataDisplay = (TextView)findViewById(R.id.birthDateTimeTextView);
        submitBirthDataBtn = (Button)findViewById(R.id.submitBirthDataBtn);
        birthTimePicker.setVisibility(View.INVISIBLE);
        backToMain = new Intent(this,MyStarChart.class);

        birthDatePicker.init(1970, 0, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int dom) {
                m++;
                birthDataDisplay.setText(m+"-"+dom+"-"+y);
            }
        });

        submitBirthDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(birthDate == null && birthTime == null) {
                    birthDate = birthDataDisplay.getText().toString();
                    birthDatePicker.setVisibility(View.INVISIBLE);
                    birthTimePicker.setVisibility(View.VISIBLE);
                    birthDataDisplay.setText("");
                }
                else if(birthDate != null && birthTime != null){
                    //birthDataDisplay.setText("You were born on: "+birthDate+"\n"+"at time: "+birthTime);
                    backToMain.putExtra("birthDate",birthDate);
                    backToMain.putExtra("birthTime",birthTime);
                    startActivity(backToMain);
                }

            }
        });

        birthTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int min) {
                birthDataDisplay.setText(h+":"+min);
                birthTime = birthDataDisplay.getText().toString();
            }
        });

    }

}

