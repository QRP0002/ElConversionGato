package com.quinn.scitools.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.quinn.scitools.R;

import pojo.database.ConversionDBHelper;

public class SplashScreen extends AppCompatActivity {
    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new SplashBackground().execute();
    }

    private class SplashBackground extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            runDBScripts();
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void results) {
            super.onPostExecute(results);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        private void runDBScripts() {
            ConversionDBHelper db = new ConversionDBHelper(mContext);
            //TYPES TABLE
            if(db.getTypesRowCount() != 4) {
                if(db.getTypesRowCount() > 0) {
                    db.deleteRowsFromTypes();
                }
                String[] types = {"Length", "Mass", "Speed", "Temperature"};
                for (String temp : types) {
                    db.insertTypes(temp);
                }
            }

            //CONVERSION TABLE
            if(db.getConversionRowCount() != 62) {
                if(db.getConversionRowCount() > 0) {
                    db.deleteRowsFromConversion();
                }
                db.insertConversions("Length", "feet", "miles", "no", "0.000189394");
                db.insertConversions("Length", "feet", "inches", "no", "12.0");
                db.insertConversions("length", "feet", "meters", "no", "0.3048");
                db.insertConversions("length", "feet", "centimeters", "no", "30.48");
                db.insertConversions("length", "feet", "kilometers", "no", "0.0003048");
                db.insertConversions("length", "miles", "feet", "no", "5290.0");
                db.insertConversions("length", "miles", "inches", "no", "63360.0");
                db.insertConversions("length", "miles", "meters", "no", "1609.34");
                db.insertConversions("length", "miles", "centimeters", "no", "160934.0");
                db.insertConversions("length", "miles", "kilometers", "no", "1.60934");
                db.insertConversions("length", "meters", "feet", "no", "3.28084");
                db.insertConversions("length", "meters", "miles", "no", "0.000621371");
                db.insertConversions("length", "meters", "inches", "no", "39.3701");
                db.insertConversions("length", "meters", "centimeters", "no", "100.0");
                db.insertConversions("length", "meters", "kilometers", "no", "0.001");
                db.insertConversions("length", "inches", "feet", "no", "0.0833333");
                db.insertConversions("length", "inches", "miles", "no", "0.00001578");
                db.insertConversions("length", "inches", "meters", "no", "0.0254");
                db.insertConversions("length", "inches", "centimeters", "no", "2.54");
                db.insertConversions("length", "inches", "kilometers", "no", "0.0000254");
                db.insertConversions("length", "centimeters", "feet", "no", "0.032808");
                db.insertConversions("length", "centimeters", "miles", "no", "0.0000062137");
                db.insertConversions("length", "centimeters", "inches", "no", "0.393701");
                db.insertConversions("length", "centimeters", "meters", "no", "0.01");
                db.insertConversions("length", "centimeters", "kilometers", "no", "0.00001");
                db.insertConversions("length", "kilometers", "feet", "no", "3280.84");
                db.insertConversions("length", "kilometers", "miles", "no", "0.621371");
                db.insertConversions("length", "kilometers", "inches", "no", "39370.1");
                db.insertConversions("length", "kilometers", "meters", "no", "100000.0)");
                db.insertConversions("length", "kilometers", "centimeters", "no", "1000.0");
                db.insertConversions("mass", "pounds", "ounces", "no", "16.0");
                db.insertConversions("mass", "pounds", "grams", "no", "453.592");
                db.insertConversions("mass", "pounds", "milligrams", "no", "453592.0");
                db.insertConversions("mass", "pounds", "kilograms", "no", "0.454");
                db.insertConversions("mass", "ounces", "pounds", "no", "0.06252");
                db.insertConversions("mass", "ounces", "grams", "no", "28.3495");
                db.insertConversions("mass", "ounces", "milligrams", "no", "28349.5");
                db.insertConversions("mass", "ounces", "kilograms", "no", "0.0283495");
                db.insertConversions("mass", "grams", "pounds", "no", "0.00220462");
                db.insertConversions("mass", "grams", "ounces", "no", "0.035274");
                db.insertConversions("mass", "grams", "milligrams", "no", "1000.0");
                db.insertConversions("mass", "grams", "kilograms", "no", "0.001");
                db.insertConversions("mass", "milligrams", "pounds", "no", "0.00000202246");
                db.insertConversions("mass", "milligrams", "ounces", "no", "0.000035274");
                db.insertConversions("mass", "milligrams", "grams", "no", "0.001");
                db.insertConversions("mass", "milligrams", "kilograms", "no", "0.000001");
                db.insertConversions("mass", "kilograms", "pounds", "no", "2.20462");
                db.insertConversions("mass", "kilograms", "ounces", "no", "35.274");
                db.insertConversions("mass", "kilograms", "grams", "no", "1000.0");
                db.insertConversions("mass", "kilograms", "milligrams", "no", "100000.0");
                db.insertConversions("speed", "mph", "km/h", "no", "1.60934");
                db.insertConversions("speed", "mph", "knots", "no", "0.868976");
                db.insertConversions("speed", "knots", "mph", "no", "1.15078");
                db.insertConversions("speed", "knots", "km/h", "no", "1.852");
                db.insertConversions("speed", "km/h", "mph", "no", "0.621371");
                db.insertConversions("speed", "km/h", "knots", "no", "0.539957");
                db.insertConversions("temperature", "\u2109", "\u2103", "yes",
                        "(((x - 32.0) * 5) / 9)");
                db.insertConversions("temperature", "\u2109", "\u212A", "yes",
                        "((((x - 32.0) * 5) / 9) + 273.15)");
                db.insertConversions("temperature", "\u2103", "\u2109", "yes",
                        "(((x * 9) / 5) + 32.0)");
                db.insertConversions("temperature", "\u2103", "\u212A", "yes",
                        "(x + 273.15)");
                db.insertConversions("temperature", "\u212A", "\u2109", "yes",
                        "(((x * 9) / 5) - 459.67)");
                db.insertConversions("temperature", "\u212A", "\u2103", "yes",
                        "(x - 273.15)");
            }
        }
    }
}
