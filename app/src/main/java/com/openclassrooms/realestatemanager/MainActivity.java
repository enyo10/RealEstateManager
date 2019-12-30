package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getName();


    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
        this.textViewMain=findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();

        // Must be uncomment
        lunchRealEstateActivity();
    }

    private void configureTextViewMain(){
      //  this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){

        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        //  this.textViewQuantity.setText(quantity);

        this.textViewQuantity.setText(String.format(Locale.FRANCE,"%d",quantity));
    }


    private void lunchRealEstateActivity(){
        Intent intent = new Intent(this, RealEstateMainActivity.class);
        startActivity(intent);

    }


}
