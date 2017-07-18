package pojo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ConversionDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "scitools.sqlite";
    private static final int VERSION = 1;
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
        // CONVERSION TABLE
        db.execSQL("CREATE TABLE " + CONVERSION_TABLE_NAME + "(" +
        CONVERSION_COLUMN_ID + " INTEGER(35) PRIMARY KEY, " +
        CONVERSION_COLUMN_TYPE + " TEXT, " + CONVERSION_COLUMN_FROM + " TEXT, " +
        CONVERSION_COLUMN_TO + " TEXT, " + CONVERSION_COLUMN_FORM + " TEXT, " +
        CONVERSION_COLUMN_VAL + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST " + TYPES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + CONVERSION_TABLE_NAME);
        onCreate(db);
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //////////////////// THESE ARE THE SCRIPTS FOR CONVERT TABLE/////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    /*public boolean insertConversions(int convert_id, String convert_type, String convert_from, String convert_to,
                                     String convert_formula, String convert_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("convert_id", convert_id);
        cv.put("convert_type", convert_type);
        cv.put("convert_from", convert_from);
        cv.put("convert_to", convert_to);
        cv.put("convert_formula", convert_formula);
        cv.put("convert_value", convert_value);
        db.insert(CONVERSION_TABLE_NAME, null, cv);
        cv.clear();
        db.close();
        return true;
    }*/

    public boolean insertConversions(ArrayList<ConversionsDB> list) {
        final String sqlstmt = "INSERT INTO " + CONVERSION_TABLE_NAME + "("
                + "convert_id, "
                + "convert_type, "
                + "convert_from, "
                + "convert_to, "
                + "convert_formula, "
                + "convert_value"
                + ") VALUES (?1,?2,?3,?4,?5,?6)";
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sqlstmt);
        db.beginTransaction();

        for(ConversionsDB temp : list) {
            stmt.bindLong(1, temp.getId());
            stmt.bindString(2, temp.getType());
            stmt.bindString(3, temp.getFrom());
            stmt.bindString(4, temp.getTo());
            stmt.bindString(5, temp.getFormula());
            stmt.bindString(6, temp.getValue());
            stmt.execute();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        stmt.close();
        return true;
    }

    public int getConversionRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CONVERSION_TABLE_NAME, null);
        int count = res.getCount();
        res.close();
        return count;
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

    public ArrayList<String> getTypeSpinnerData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> tempArray = new ArrayList<>();

        Cursor res = db.rawQuery("SELECT DISTINCT " + CONVERSION_COLUMN_TYPE + " FROM " +
                CONVERSION_TABLE_NAME, null);
        res.moveToFirst();

        for(int i = 0; i < res.getCount(); i++) {
            tempArray.add(res.getString(res.getColumnIndex(CONVERSION_COLUMN_TYPE)));
            res.moveToNext();
        }

        res.close();
        return (tempArray.size() > 0 ? tempArray : null);
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
        return (tempArray.size() > 0 ? tempArray : null);
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

    public String largestID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(" + CONVERSION_COLUMN_ID + ") FROM " +
                CONVERSION_TABLE_NAME, null);
        res.moveToFirst();
        String resultStr = res.getString(res.getPosition());
        res.close();
        return resultStr;
    }
}