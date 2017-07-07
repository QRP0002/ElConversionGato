package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;

import presenter.ConversionFragmentPresenter;

public class ConversionFragment extends Fragment implements View.OnKeyListener,
        ConversionFragmentPresenter.ConversionFragmentView {

    private ConversionFragmentPresenter presenter;

    private TextView mOutcomeTV, mOutcomeHeaderTV;
    private EditText mInputET;
    private Spinner mSpinnerFrom, mSpinnerTo, mSpinnerType;
    private ArrayAdapter<String> typeAdapter, fromAdapter, toAdapter;

    public static ConversionFragment newInstance() {
        ConversionFragment fragment = new ConversionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ConversionFragmentPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversions, container, false);

        // EditText
        mInputET = (EditText) view.findViewById(R.id.conversion_input_et);
        mInputET.setOnKeyListener(this);
        //mInputET.addTextChangedListener(new MyTextWatcher(mInputET));
        // TextView
        mOutcomeTV = (TextView) view.findViewById(R.id.conversion_output_tv);
        mOutcomeHeaderTV = (TextView) view.findViewById(R.id.output_header_str_tv);
        //Spinners
        mSpinnerType = (Spinner) view.findViewById(R.id.top_type_spinner);
        mSpinnerFrom = (Spinner) view.findViewById(R.id.first_spinner);
        mSpinnerTo   = (Spinner) view.findViewById(R.id.second_spinner);

        presenter.assigningTypeSpinnerArray();

        //Type Spinner
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.assigningFromSpinnerArray(mSpinnerType.getSelectedItem().toString());
                presenter.assigningToSpinnerArray(mSpinnerFrom.getSelectedItem().toString(), true);

                //Clear the EditText section if the types spinner changes.
                if(mInputET.getText().length() > 0) {
                    mInputET.setText("");
                }
                //Clear the TextViews if the types spinner changes.
                if(mOutcomeTV.getText().length() > 0) {
                    clearOutputTextView(mOutcomeTV, mOutcomeHeaderTV);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.assigningToSpinnerArray(mSpinnerFrom.getSelectedItem().toString(), false);
                if(mInputET.getText().length() > 0 && !mInputET.getText().toString().isEmpty()) {
                    notifyCalculatePresenter(presenter,
                            Double.parseDouble(mInputET.getText().toString()),
                            mSpinnerFrom.getSelectedItem().toString(),
                            mSpinnerTo.getSelectedItem().toString());
                } else {
                    clearOutputTextView(mOutcomeTV, mOutcomeHeaderTV);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mInputET.getText().length() > 0 && !mInputET.getText().toString().isEmpty()) {
                    notifyCalculatePresenter(presenter,
                            Double.parseDouble(mInputET.getText().toString()),
                            mSpinnerFrom.getSelectedItem().toString(),
                            mSpinnerTo.getSelectedItem().toString());
                } else {
                    clearOutputTextView(mOutcomeTV, mOutcomeHeaderTV);
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                if(mInputET.getText().length() > 0) {
                    notifyCalculatePresenter(presenter,
                            Double.parseDouble(mInputET.getText().toString()),
                            mSpinnerFrom.getSelectedItem().toString(),
                            mSpinnerTo.getSelectedItem().toString());
                    return true;
                } else {
                    clearOutputTextView(mOutcomeTV, mOutcomeHeaderTV);
                    return false;
                }
            }
        }
        return false;
    }

    private void clearOutputTextView(TextView textViewOne, TextView textViewTwo) {
        textViewOne.setText("");
        textViewTwo.setText("");
    }

    private void notifyCalculatePresenter(ConversionFragmentPresenter presenter, double userInput,
                                          String from, String to) {
        presenter.calculateOutcome(userInput, from, to);
        if(Double.toString(userInput).length() > 0)
            mOutcomeHeaderTV.setText(R.string.conversion_tv);
    }

    //
    // Presenter Interface methods are below.
    //

    @Override
    public void fillTypeSpinner(ArrayList<String> types) {
        if(typeAdapter == null) {
            typeAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, types);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerType.setAdapter(typeAdapter);
        }
    }

    @Override
    public void fillFromSpinner(ArrayList<String> types) {
        if(fromAdapter == null) {
            fromAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
            fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerFrom.setAdapter(fromAdapter);
        }
        fromAdapter.clear();
        fromAdapter.addAll(types);
        mSpinnerFrom.setSelection(0);
    }

    @Override
    public void fillToSpinner(ArrayList<String> types) {
        if(toAdapter == null) {
            toAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
            toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerTo.setAdapter(toAdapter);
        }
        toAdapter.clear();
        toAdapter.addAll(types);
        mSpinnerTo.setSelection(0);
    }

    @Override
    public void fillOutcomeTextView(String text) {
        mOutcomeTV.setText(text);
    }
}