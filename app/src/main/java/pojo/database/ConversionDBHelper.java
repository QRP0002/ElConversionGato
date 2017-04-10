package pojo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ConversionDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "scitools.sqlite";
    private static final int VERSION = 1;
    //TYPES TABLE
    private static final String TYPES_TABLE_NAME   = "conversion_types";
    private static final String TYPES_COLUMN_ID    = "type_id";
    private static final String TYPES_COLUMN_NAMES = "type_name";
    //CONVERSION TABLE
    private static final String CONVERSION_TABLE_NAME  = "conversion_information";
    private static final String CONVERSION_COLUMN_ID   = "convert_id";
    private static final String CONVERSION_COLUMN_TYPE = "convert_type";
    private static final String CONVERSION_COLUMN_FROM = "convert_from";
    private static final String CONVERSION_COLUMN_TO   = "convert_to";
    private static final String CONVERSION_COLUMN_FORM = "convert_formula";
    private static final String CONVERSION_COLUMN_VAL  = "convert_value";

    public ConversionDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TYPE TABLE
        db.execSQL("create table " + TYPES_TABLE_NAME + "(" +
         TYPES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
         TYPES_COLUMN_NAMES + " text " + ")");
        // CONVERSION TABLE
        db.execSQL("CREATE TABLE " + CONVERSION_TABLE_NAME + "(" +
        CONVERSION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        CONVERSION_COLUMN_TYPE + " TEXT, " + CONVERSION_COLUMN_FROM + " TEXT, " +
        CONVERSION_COLUMN_TO + " TEXT, " + CONVERSION_COLUMN_FORM + " TEXT, " +
        CONVERSION_COLUMN_VAL + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TYPES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + CONVERSION_TABLE_NAME);
        onCreate(db);
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////// THESE ARE THE SCRIPTS FOR TYPES TABLE//////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public void deleteRowsFromTypes() {
    SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE * from " + TYPES_TABLE_NAME);
    }

    public boolean insertTypes(String type_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type_name", type_name);
        db.insert(TYPES_TABLE_NAME, null, cv);
        return true;
    }

    public int getTypesRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TYPES_TABLE_NAME, null);
        int count = res.getCount();
        res.close();
        return count;
    }

    public ArrayList<String> getTypes() {
        ArrayList<String> typesArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TYPES_TABLE_NAME,
                null);
        res.moveToFirst();

        for(int i = 0; i < res.getCount(); i++) {
            typesArray.add(res.getString(res.getColumnIndex(TYPES_COLUMN_NAMES)));
            res.moveToNext();
        }

        res.close();
        return (typesArray);
    }


    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //////////////////// THESE ARE THE SCRIPTS FOR CONVERT TABLE/////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public boolean insertConversions(String convert_type, String convert_from, String convert_to,
                                     String convert_formula, String convert_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("convert_type", convert_type);
        cv.put("convert_from", convert_from);
        cv.put("convert_to", convert_to);
        cv.put("convert_formula", convert_formula);
        cv.put("convert_value", convert_value);
        db.insert(CONVERSION_TABLE_NAME, null, cv);
        return true;
    }

    public int getConversionRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CONVERSION_TABLE_NAME, null);
        int count = res.getCount();
        res.close();
        return count;
    }

    public void deleteRowsFromConversion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE * from " + CONVERSION_TABLE_NAME);
    }

    public double getNoFormulaValue(String fromIn, String toIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + CONVERSION_COLUMN_VAL + " FROM " +
                CONVERSION_TABLE_NAME + " WHERE " + CONVERSION_COLUMN_FROM + " = '" + fromIn + "'" +
                " AND " + CONVERSION_COLUMN_TO + " = '" + toIn + "'" , null);
        res.moveToFirst();
        double tempDouble = Double.parseDouble(res.getString(res.getPosition()));
        res.close();
        return tempDouble;
    }

    public String getFormulaValue(String fromIn, String toIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + CONVERSION_COLUMN_VAL + " FROM " +
                CONVERSION_TABLE_NAME + " WHERE " + CONVERSION_COLUMN_FROM + " = '" + fromIn + "'" +
                " AND " + CONVERSION_COLUMN_TO + " = '" + toIn + "'" , null);
        res.moveToFirst();
        String formula = res.getString(res.getPosition());
        res.close();
        return formula;
    }

    public ArrayList<String> getFromSpinnerData(String conversionType) {
        ArrayList<String> tempArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT DISTINCT " + CONVERSION_COLUMN_FROM +
                " FROM " + CONVERSION_TABLE_NAME + " WHERE " +
                CONVERSION_COLUMN_TYPE + " ='" + conversionType + "'", null);
        res.moveToFirst();

        for(int i = 0; i < res.getCount(); i++) {
            tempArray.add(res.getString(res.getColumnIndex(CONVERSION_COLUMN_FROM)));
            res.moveToNext();
        }

        res.close();
        return tempArray;
    }

    public String isFormula(String fromIn, String toIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + CONVERSION_COLUMN_FORM + " FROM " +
                CONVERSION_TABLE_NAME + " WHERE " + CONVERSION_COLUMN_FROM + " ='" + fromIn + "'" +
        "AND " + CONVERSION_COLUMN_TO + "='" + toIn + "'",null);
        res.moveToFirst();
        String resultStr = res.getString(res.getPosition());
        res.close();
        return resultStr;
    }
}
