package presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import fragments.tabs.ConversionFragment;
import pojo.ConversionController;
import pojo.database.ConversionDBHelper;
import pojo.helpers.SpinnerHelp;

public class ConversionFragmentPresenter {

    private Context context;
    private ConversionFragment view;
    private SpinnerHelp help = SpinnerHelp.getInstance();

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

    public void loadTypes() {
        ConversionDBHelper db = new ConversionDBHelper(context);
        help.setTypesSpinner(db.getTypes());
    }

    public void assigningTypeSpinnerArray() {
        view.fillTypeSpinner(help.getTypesSpinner());
    }

    public void assigningFromSpinnerArray(String conversionTypeSelected) {
        view.fillFromSpinner(help.updateFromSpinner(conversionTypeSelected, context));
    }

    public void assigningToSpinnerArray(String conversionFromSelected, boolean firstTime) {
        if(firstTime) {
            view.fillToSpinner(help.initialToSpinner());
        } else {
            view.fillToSpinner(help.updateToSpinner(conversionFromSelected));
        }
    }

    public void calculateOutcome(double inputValue, String conversionType, String from, String to) {
        ConversionController cc = new ConversionController(context);
        cc.directConversion(inputValue, conversionType, from, to);
        view.fillOutcomeTextView(cc.getOutput());
    }
}
