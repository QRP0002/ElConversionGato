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

import java.util.ArrayList;

import pojo.convert.MassConversion;
import pojo.helpers.SpinnerHelp;

public class MassFragment extends Fragment implements View.OnKeyListener{
    private TextView mOutcomeTV;
    private EditText mInputET;
    private Spinner mSpinnerOne, mSpinnerTwo;

    private ArrayList<String> massTypes = new ArrayList<>();

    public static MassFragment newInstance() {
        MassFragment fragment = new MassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        massTypes.add("lb");
        massTypes.add("oz");
        massTypes.add("g");
        massTypes.add("kg");
        massTypes.add("mg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversions, container, false);

        //TextView
        mOutcomeTV = (TextView) view.findViewById(R.id.conversion_output_tv);
        //EditText
        mInputET = (EditText) view.findViewById(R.id.conversion_input_et);
        mInputET.setOnKeyListener(this);
        //Spinner
        mSpinnerOne = (Spinner) view.findViewById(R.id.first_spinner);
        mSpinnerTwo = (Spinner) view.findViewById(R.id.second_spinner);
        ArrayAdapter<CharSequence> adapterOne = ArrayAdapter.createFromResource(getActivity(),
                R.array.mass_spinner_types, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOne.setAdapter(adapterOne);

        final SpinnerHelp sh = new SpinnerHelp(massTypes);
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
                MassConversion mc = new MassConversion();
                mc.setConversionType(mSpinnerOne.getSelectedItem().toString());
                mc.setConversionTypeTwo(mSpinnerTwo.getSelectedItem().toString());
                mOutcomeTV.setText(mc.convert(Double.parseDouble(mInputET.getText().toString())));
                return true;
            }
        }
        return false;
    }

}
