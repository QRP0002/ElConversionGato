package presenter;
import android.util.Log;

import com.quinn.scitools.activity.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.HTTPRequest;
import pojo.database.ConversionDBHelper;

public class SplashActivityPresenter {

    private final String TAG_SUCCESS = "success";
    private final String TAG_DATA = "data";
    private final String TAG_COUNT = "count";
    private final String TAG_ITEMS = "items";
    private final String TAG_TYPE_NAME = "conversion_name";
    private final String TAG_CONVERT_TYPE = "convert_type";
    private final String TAG_CONVERT_FROM = "convert_from";
    private final String TAG_CONVERT_TO  = "convert_to";
    private final String TAG_CONVERT_FORMULA = "convert_formula";
    private final String TAG_CONVERT_VALUE = "convert_value";

    private ArrayList<HashMap<String, String>> parsedItems    = new ArrayList<>();
    private ArrayList<HashMap<String, String>> parsedConverts = new ArrayList<>();

    private String countType;
    private String countConvert;
    private String typeData;
    private String convertData;

    //Splash Activity View Interface
    public interface SplashActivityView {
        void startMainActivity();
    }

    //View used for the SQLite Database.
    private SplashScreen view;

    private static SplashActivityPresenter instance = null;

    private SplashActivityPresenter() {}

    public static SplashActivityPresenter getInstance() {
        if(instance == null) {
            instance = new SplashActivityPresenter();
        }
        return instance;
    }

    public void startBackgroundTasks() {
        HTTPRequest model = new HTTPRequest();
        model.callDatabase();
    }

    public void settingUpDatabaseData() throws JSONException {
        ConversionDBHelper db = new ConversionDBHelper(view);
        if(countType.length() > 0 && countConvert.length() > 0) {
            int localCountType     = db.getTypesRowCount();
            int localCountConvert  = db.getConversionRowCount();

            if(localCountType != parseJSONCounts(countType)) {
                if(localCountType > 0) {
                    db.deleteRowsFromTypes();
                }
                parseJSONData(typeData, true);
                insertRemoteDBToLocalDB(db, true);
            }

            if(localCountConvert != parseJSONCounts(countConvert)) {
                if(localCountConvert > 0) {
                    db.deleteRowsFromConversion();
                }
                parseJSONData(convertData, false);
                insertRemoteDBToLocalDB(db, false);
            }
        }
        view.startMainActivity();
    }

    private int parseJSONCounts(String action) throws JSONException {
        JSONObject jObject = new JSONObject(action);
        JSONObject dataObject = jObject.getJSONObject(TAG_DATA);
        if(dataObject.getInt(TAG_SUCCESS) == 1) {
            return dataObject.getInt(TAG_COUNT);
        }
        return 0;
    }

    private void parseJSONData(String action, boolean queryType) throws JSONException {
        JSONObject jObject = new JSONObject(action);
        JSONObject dataObject = jObject.getJSONObject(TAG_DATA);
        JSONArray jsonItemArray = dataObject.getJSONArray(TAG_ITEMS);

        for(int i = 0; i < jsonItemArray.length(); i++) {
            JSONObject tempObj = jsonItemArray.getJSONObject(i);
            if(queryType) {
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

    private void insertRemoteDBToLocalDB(ConversionDBHelper db, boolean type) {
        if(type) {
            String tempTypeName = "";
            for (HashMap<String, String> hashMap : parsedItems) {
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    if(entry.getKey().equals(TAG_TYPE_NAME)) {
                        tempTypeName = entry.getValue();
                    }
                    db.insertTypes(tempTypeName);
                }
            }
        } else {
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
    }

    public void setConvertData(String convertData) {
        this.convertData = convertData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public void setCountConvert(String countConvert) {
        this.countConvert = countConvert;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public void setView(SplashScreen view) {
        this.view = view;
    }
}