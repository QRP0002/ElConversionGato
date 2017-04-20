package com.quinn.scitools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.quinn.scitools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pojo.database.ConversionDBHelper;

public class SplashScreen extends AppCompatActivity {
    private Context mContext = this;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_COUNT = "count";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_TYPE_NAME = "conversion_name";
    private static final String TAG_CONVERT_TYPE = "convert_type";
    private static final String TAG_CONVERT_FROM = "convert_from";
    private static final String TAG_CONVERT_TO  = "convert_to";
    private static final String TAG_CONVERT_FORMULA = "convert_formula";
    private static final String TAG_CONVERT_VALUE = "convert_value";
    private final int[] count = new int[2];
    private final Object lock = new Object();
    private ArrayList<HashMap<String, String>> parsedItems    = new ArrayList<>();
    private ArrayList<HashMap<String, String>> parsedConverts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runDBScripts();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }

    private void runDBScripts() throws InterruptedException {
        ConversionDBHelper db = new ConversionDBHelper(mContext);
        synchronized (lock) {
            remoteDBAccess("countType");
            while(count[0] == 0) {
                lock.wait();
            }
            if(db.getTypesRowCount() != count[0]) {
                if(db.getTypesRowCount() > 0) {
                    db.deleteRowsFromTypes();
                }
                remoteDBAccess("getTypeData");
                while (parsedItems.size() <= 0) {
                    lock.wait();
                }

                String tempTypeName = "";
                for (HashMap<String, String> hashMap : parsedItems) {
                    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                        if(entry.getKey().equals(TAG_TYPE_NAME)) {
                            tempTypeName = entry.getValue();
                        }
                        db.insertTypes(tempTypeName);
                    }
                }
            }

            remoteDBAccess("countConvert");
            while (count[1] == 0) {
                lock.wait();
            }
            if(db.getConversionRowCount() != count[1]) {
                remoteDBAccess("getConvertData");
                if(db.getConversionRowCount() > 0) {
                    db.deleteRowsFromConversion();
                }
                while(parsedConverts.size() <= 0) {
                    lock.wait();
                }

                String tempType = "", tempFrom = "", tempTo = "", tempFormula = "", tempValue = "";
                for (HashMap<String, String> hashMap : parsedConverts) {
                    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                        if(entry.getKey().equals(TAG_CONVERT_TYPE)) {
                            tempType = entry.getValue();
                        } else if(entry.getKey().equals(TAG_CONVERT_FROM)) {
                            tempFrom = entry.getValue();
                        } else if(entry.getKey().equals(TAG_CONVERT_TO)) {
                            tempTo = entry.getValue();
                        } else if(entry.getKey().equals(TAG_CONVERT_FORMULA)) {
                            tempFormula = entry.getValue();
                        } else if(entry.getKey().equals(TAG_CONVERT_VALUE)) {
                            tempValue = entry.getValue();
                        }
                    }
                    if(tempFrom.length() > 0 && tempType.length() > 0 &&
                            tempTo.length() > 0 && tempFormula .length() > 0 &&
                            tempValue .length() > 0) {
                        db.insertConversions(tempType, tempFrom, tempTo, tempFormula, tempValue);
                    }
                }
            }
            Stetho.initializeWithDefaults(this);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }

    private void parseJSONArray (JSONObject json, String action) throws JSONException {
        JSONArray jsonItemArray = json.getJSONArray(TAG_ITEMS);
        for(int i = 0; i < jsonItemArray.length(); i++) {
            JSONObject tempObj = jsonItemArray.getJSONObject(i);
            if(action.equals("getTypeData")) {
                String tempName = tempObj.getString(TAG_TYPE_NAME);
                HashMap<String, String> map = new HashMap<>();
                map.put(TAG_TYPE_NAME, tempName);
                parsedItems.add(map);
            } else {
                String tempType = tempObj.getString(TAG_CONVERT_TYPE);
                String tempFrom = tempObj.getString(TAG_CONVERT_FROM);
                String tempTo = tempObj.getString(TAG_CONVERT_TO);
                String tempFormula = tempObj.getString(TAG_CONVERT_FORMULA);
                String tempValue = tempObj.getString(TAG_CONVERT_VALUE);
                HashMap<String, String> map = new HashMap<>();
                map.put(TAG_CONVERT_TYPE, tempType);
                map.put(TAG_CONVERT_FROM, tempFrom);
                map.put(TAG_CONVERT_TO, tempTo);
                map.put(TAG_CONVERT_FORMULA, tempFormula);
                map.put(TAG_CONVERT_VALUE, tempValue);
                parsedConverts.add(map);
            }
        }

    }

    private void remoteDBAccess(final String action) {
        final RequestQueue queue = Volley.newRequestQueue(mContext);
        final String url = "http://ec2-52-90-8-139.compute-1.amazonaws.com/API.php";
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObject = new JSONObject(response);
                                        JSONObject dataObject = jObject.getJSONObject(TAG_DATA);
                                        int success = dataObject.getInt(TAG_SUCCESS);
                                        synchronized (lock) {
                                            if (success == 1) {
                                                if(action.equals("countType")) {
                                                    count[0] = dataObject.getInt(TAG_COUNT);
                                                } else if(action.equals("countConvert")) {
                                                    count[1] = dataObject.getInt(TAG_COUNT);
                                                } else if(action.equals("getTypeData")) {
                                                    parseJSONArray(dataObject, action);
                                                } else if(action.equals("getConvertData")) {
                                                    parseJSONArray(dataObject, action);
                                                }
                                                lock.notify();
                                            }
                                        }

                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("action", action);

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
        t2.start();
    }
}