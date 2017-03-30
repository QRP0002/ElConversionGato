package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quinn.scitools.R;

import pojo.convert.SpeedConversion;

public class SpeedFragment extends Fragment implements View.OnKeyListener {
    private TextView mFormulaTV, mOutcomeTV;
    private EditText mInputET;
    private Spinner mSpeedSpinner;
    private static final String TAG = "SpeedFragTAG";

    public static SpeedFragment newInstance() {
        SpeedFragment fragment = new SpeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speed, container, false);

        //TextViews
        mFormulaTV = (TextView) view.findViewById(R.id.speed_formula_tv);
        mOutcomeTV = (TextView) view.findViewById(R.id.speed_output_tv);
        //EditText
        mInputET = (EditText) view.findViewById(R.id.speed_input_et);
        mInputET.setOnKeyListener(this);
        //Spinner
        mSpeedSpinner = (Spinner) view.findViewById(R.id.speed_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.speed_spinner_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpeedSpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                SpeedConversion sc = new SpeedConversion();
                sc.setConversionType(mSpeedSpinner.getSelectedItem().toString());
                mOutcomeTV.setText(sc.convert(Double.parseDouble(mInputET.getText().toString())));
                mFormulaTV.setText(mSpeedSpinner.getSelectedItem().toString().equals("MPH") ?
                        R.string.speed_formula_mph : R.string.speed_formula_kph);
            }
        }
        return false;
    }
}
