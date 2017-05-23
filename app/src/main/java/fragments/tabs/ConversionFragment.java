package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quinn.scitools.R;

import pojo.ConversionController;
import pojo.database.ConversionDBHelper;
import pojo.helpers.SpinnerHelp;

public class ConversionFragment extends Fragment implements View.OnKeyListener{
    private TextView mOutcomeTV, mOutcomeHeaderTV;
    private EditText mInputET;
    private Spinner mSpinnerFrom, mSpinnerTo, mSpinnerType;

    public static ConversionFragment newInstance() {
        ConversionFragment fragment = new ConversionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConversionDBHelper db = new ConversionDBHelper(getActivity());
        SpinnerHelp help = SpinnerHelp.getInstance();
        help.setTopSpinnerTypes(db.getTypes());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversions, container, false);
        SpinnerHelp help = SpinnerHelp.getInstance();

        // EditText
        mInputET = (EditText) view.findViewById(R.id.conversion_input_et);
        mInputET.setOnKeyListener(this);
        // TextView
        mOutcomeTV = (TextView) view.findViewById(R.id.conversion_output_tv);
        mOutcomeHeaderTV = (TextView) view.findViewById(R.id.output_header_str_tv);
        //Spinners
        mSpinnerType = (Spinner) view.findViewById(R.id.top_type_spinner);
        mSpinnerFrom = (Spinner) view.findViewById(R.id.first_spinner);
        mSpinnerTo   = (Spinner) view.findViewById(R.id.second_spinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, help.getTopSpinnerTypes());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerType.setAdapter(typeAdapter);

        final ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item);
        mSpinnerFrom.setAdapter(fromAdapter);
        final ArrayAdapter<String> toAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item);
        mSpinnerTo.setAdapter(toAdapter);

        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerData(2, fromAdapter, mSpinnerFrom);
                setSpinnerData(0, toAdapter, mSpinnerTo);
                if(mInputET.getText().length() > 0) {
                    mInputET.setText("");
                }
                if(mOutcomeTV.getText().length() > 0) {
                    clearOutput(mOutcomeTV);
                    clearOutput(mOutcomeHeaderTV);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerData(1, toAdapter, mSpinnerTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mInputET.getText().length() > 0 && !mInputET.getText().toString().isEmpty()) {
                    ConversionController cc = new ConversionController(getActivity());
                    cc.directConversion(Double.parseDouble(mInputET.getText().toString()),
                            mSpinnerType.getSelectedItem().toString(),
                            mSpinnerFrom.getSelectedItem().toString(),
                            mSpinnerTo.getSelectedItem().toString());
                    mOutcomeTV.setText(cc.getOutput());
                    if(mOutcomeTV.getText().toString().length() > 0 ) {
                        mOutcomeHeaderTV.setText(R.string.conversion_tv);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setSpinnerData(int spinnerCase, ArrayAdapter<String> adapter, Spinner spinner) {
        SpinnerHelp help = SpinnerHelp.getInstance();
        adapter.clear();
        if(spinnerCase == 0) {
            adapter.addAll(help.initialSpinnerTwo());
        } else if(spinnerCase == 1) {
            adapter.addAll(help.updateSpinnerTwo(mSpinnerFrom.getSelectedItem().toString()));
        } else if(spinnerCase == 2){
            adapter.addAll(help.updateSpinnerOne(mSpinnerType.getSelectedItem().toString(),
                    getActivity()));
        }
        adapter.notifyDataSetChanged();
        spinner.setSelection(0);
    }

    private void clearOutput(TextView textView) {
        textView.setText(R.string.empty_str_tv);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                if(mInputET.getText().length() > 0) {
                    ConversionController cc = new ConversionController(getActivity());
                    cc.directConversion(Double.parseDouble(mInputET.getText().toString()),
                            mSpinnerType.getSelectedItem().toString(),
                            mSpinnerFrom.getSelectedItem().toString(),
                            mSpinnerTo.getSelectedItem().toString());
                    mOutcomeTV.setText(cc.getOutput());
                    if(mOutcomeTV.getText().toString().length() > 0 ) {
                        mOutcomeHeaderTV.setText(R.string.conversion_tv);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}