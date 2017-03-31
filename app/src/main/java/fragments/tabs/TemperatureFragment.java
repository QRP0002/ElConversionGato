package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quinn.scitools.R;

import java.util.ArrayList;
import java.util.HashMap;

import pojo.convert.LengthConversion;
import pojo.convert.SpeedConversion;
import pojo.convert.TemperatureConversion;
import pojo.helpers.SpinnerHelp;

public class TemperatureFragment extends Fragment implements View.OnKeyListener{
    private TextView mOutcomeTV;
    private EditText mInputET;
    private Spinner mSpinnerOne, mSpinnerTwo;
    private ArrayList<String> tempType = new ArrayList<>();

    public static TemperatureFragment newInstance() {
        TemperatureFragment fragment = new TemperatureFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempType.add("\u2103"); //Far
        tempType.add("\u2109"); //Cel
        tempType.add("\u212A"); //Kel
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_conversions, container, false);

        // TextView
        mOutcomeTV = (TextView) view.findViewById(R.id.conversion_output_tv);
        // EditText
        mInputET = (EditText) view.findViewById(R.id.conversion_input_et);
        mInputET.setOnKeyListener(this);
        // Spinner
        mSpinnerOne = (Spinner) view.findViewById(R.id.first_spinner);
        ArrayAdapter<CharSequence> adapterOne = ArrayAdapter.createFromResource(getActivity(),
                R.array.temp_spinner_types, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOne.setAdapter(adapterOne);

        mSpinnerTwo = (Spinner) view.findViewById(R.id.second_spinner);
        final SpinnerHelp sh = new SpinnerHelp(tempType);
        sh.update(mSpinnerOne.getSelectedItem().toString());
        final ArrayAdapter<String> adapterTwo = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, sh.getParsedTypes());
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTwo.setAdapter(adapterTwo);

        mSpinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapterTwo.clear();
                sh.update(mSpinnerOne.getSelectedItem().toString());
                adapterTwo.addAll(sh.getParsedTypes());
                adapterTwo.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                LengthConversion lc = new LengthConversion();
                lc.setConversionType(mSpinnerOne.getSelectedItem().toString());
                lc.setConversionTypeTwo(mSpinnerTwo.getSelectedItem().toString());
                mOutcomeTV.setText(lc.convert(Double.parseDouble(mInputET.getText().toString())));

                return true;
            }
        }
        return false;
    }
}
