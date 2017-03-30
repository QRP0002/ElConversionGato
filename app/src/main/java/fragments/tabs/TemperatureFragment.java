package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quinn.scitools.R;

import pojo.convert.TemperatureConversion;

public class TemperatureFragment extends Fragment implements View.OnKeyListener{
    private TextView mFormulaTV, mOutcomeTV;
    private Button mConversionButton;
    private EditText mInputET;
    private Spinner mTempSpinner;
    private final static String TAG = "TempFragTAG";

    public static TemperatureFragment newInstance() {
        TemperatureFragment fragment = new TemperatureFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_speed, container, false);

        // TextView
        mFormulaTV = (TextView) view.findViewById(R.id.speed_formula_tv);
        mOutcomeTV = (TextView) view.findViewById(R.id.speed_output_tv);
        // EditText
        mInputET = (EditText) view.findViewById(R.id.speed_input_et);
        mInputET.setOnKeyListener(this);
        // Spinner
        mTempSpinner = (Spinner) view.findViewById(R.id.speed_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.temp_spinner_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTempSpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                String tempStr = mTempSpinner.getSelectedItem().toString();
                TemperatureConversion tc = new TemperatureConversion();
                tc.setConversionType(tempStr);

                mFormulaTV.setText(tempStr.equals("Fahr.") ?
                        R.string.temp_formula_f : R.string.temp_formula_c);
                mOutcomeTV.setText(tc.convert(Double.parseDouble(mInputET.getText().toString())));
                return true;
            }
        }
        return false;
    }
}
