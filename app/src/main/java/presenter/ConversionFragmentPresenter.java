package presenter;

import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;

import fragments.tabs.ConversionFragment;
import pojo.convert.ConversionFormula;
import pojo.convert.ConversionNoFormula;
import pojo.convert.Conversions;
import pojo.database.ConversionDBHelper;

public class ConversionFragmentPresenter {

    private Context context;
    private ConversionFragment view;
    private ConversionDBHelper db;
    private ArrayList<String> typeSpinnerArray, fromSpinnerArray;

    public interface ConversionFragmentView {
        void fillTypeSpinner(ArrayList<String> types);

        void fillFromSpinner(ArrayList<String> types);

        void fillToSpinner(ArrayList<String> types);

        void fillOutcomeTextView(String text);
    }

    // Constructor
    public ConversionFragmentPresenter(Context context, ConversionFragment view) {
        this.context = context;
        this.view = view;
    }

    /**
     *  Spinner Methods.
     */

    public void assigningTypeSpinnerArray() {
        db = new ConversionDBHelper(context);
        view.fillTypeSpinner(db.getTypeSpinnerData());
    }

    public void assigningFromSpinnerArray(String conversionTypeSelected) {
        fromSpinnerArray = db.getFromSpinnerData(conversionTypeSelected);
        view.fillFromSpinner(fromSpinnerArray);
    }

    public void assigningToSpinnerArray(String conversionFromSelected, boolean firstTime) {
        if(firstTime) {
            view.fillToSpinner(parseToSpinnerData(fromSpinnerArray.get(0)));
        } else {
            view.fillToSpinner(parseToSpinnerData(conversionFromSelected));
        }
    }

    private ArrayList<String> parseToSpinnerData(String removeData) {
        ArrayList<String> returnArray = new ArrayList<>();

        if(!returnArray.isEmpty() && returnArray.size() > 0) {
            returnArray.clear();
        }

        for(String tempStr : fromSpinnerArray) {
            if(!tempStr.equals(removeData)) {
                returnArray.add(tempStr);
            }
        }
        return(returnArray);
    }

    /**
     *  Calculation Methods.
     */

    public void calculateOutcome(double inputValue, String from, String to) {
        db = new ConversionDBHelper(this.context);

        if(checkFormula(from, to, db).equals("yes")) {
            ConversionFormula cf = new ConversionFormula(getValuesFromDatabase(from, to, db, true));
            setConversionTypes(cf, from, to);
            view.fillOutcomeTextView(cf.convert(inputValue));
        } else {
            ConversionNoFormula cnf = new ConversionNoFormula(Double.parseDouble(
                    getValuesFromDatabase(from, to, db, false)));
            setConversionTypes(cnf, from, to);
            view.fillOutcomeTextView(cnf.convert(inputValue));
        }
    }

    private String checkFormula(String from, String to, ConversionDBHelper db) {
        return (db.isFormula(from, to));
    }

    private String getValuesFromDatabase(String from, String to, ConversionDBHelper db,
                                         boolean formula) {
        return (formula ? db.getFormulaValue(from, to) :
                Double.toString(db.getNoFormulaValue(from, to)));
    }

    private void setConversionTypes(Conversions obj, String fromType, String toType) {
        obj.setConversionType(fromType);
        obj.setConversionTypeTwo(toType);
    }
}