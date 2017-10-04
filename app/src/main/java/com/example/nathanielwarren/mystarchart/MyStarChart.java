package com.example.nathanielwarren.mystarchart;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyStarChart extends AppCompatActivity {

    Button getBirthDataBtn;
    TextView birthDataTimeTextView;
    EditText birthPlaceText;
    Button getBirthPlaceBtn;
    String birthDate;
    String birthPlace;
    String birthState;
    List<LatLng> birthPlaceCoords;
    Spinner userStateSpin;

    Date bDate;
    Bundle birthData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_star_chart);

        final Intent getData = new Intent(this,getBirthData.class);

        getBirthDataBtn = (Button)findViewById(R.id.getBirthDataBtn);
        birthDataTimeTextView = (TextView)findViewById(R.id.birthDateTimeTextView);
        birthPlaceText = (EditText)findViewById(R.id.birthPlaceTxt);
        getBirthPlaceBtn = (Button)findViewById(R.id.submitBirthPlaceBtn);
        userStateSpin = (Spinner)findViewById(R.id.stateSpinner);

        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this,R.array.stateList,android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userStateSpin.setAdapter(adapt);
        birthPlaceText.setVisibility(View.INVISIBLE);
        getBirthPlaceBtn.setVisibility(View.INVISIBLE);
        userStateSpin.setVisibility(View.INVISIBLE);

        getBirthDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthData = getIntent().getExtras();
                if(birthData != null) {
                    birthDate = (birthData.getString("birthDate")+" at "+birthData.getString("birthTime"));
                    birthDataTimeTextView.setText("Born on "+birthDate);
                    bDate = convToDate(birthDate);
                    getBirthDataBtn.setVisibility(View.INVISIBLE);
                    birthPlaceText.setVisibility(View.VISIBLE);
                    birthPlaceText.setHint("City of Birth");
                    birthPlaceText.setText("");
                    getBirthPlaceBtn.setVisibility(View.VISIBLE);
                    userStateSpin.setVisibility(View.VISIBLE);
                } else {
                    startActivity(getData);
                }
            }
        });

        getBirthPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(birthPlaceText.getText().toString() != "" && birthState != "") {
                    birthPlace = birthPlaceText.getText().toString();
                    birthPlaceCoords = getUserBLoc(birthPlace,birthState);
                    birthPlaceText.setText(birthPlaceCoords.toString());
                }
            }
        });

        userStateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                birthState = userStateSpin.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                birthState = null;
            }
        });

    }

    public Date convToDate(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm");
        try {
            bDate = sdf1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bDate;
    }

    public List<LatLng> getUserBLoc(String city, String state) {
        if (Geocoder.isPresent()) {
            try {
                String location = (city+", "+state);
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                List<Address> adds = gc.getFromLocationName(location, 5);
                List<LatLng> ll = new ArrayList<LatLng>(adds.size());
                for (Address a : adds) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                }
                return ll;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
