package fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import pojo.convert.SpeedConversion;
import pojo.helpers.SpinnerHelp;

public class SpeedFragment extends Fragment implements View.OnKeyListener {
    private TextView mOutcomeTV;
    private EditText mInputET;
    private Spinner mSpinnerOne, mSpinnerTwo;
    private ArrayList<String> speedType = new ArrayList<>();

    public static SpeedFragment newInstance() {
        SpeedFragment fragment = new SpeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speedType.add("mph");
        speedType.add("kph");
        speedType.add("knots");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversions, container, false);

        //TextViews
        mOutcomeTV = (TextView) view.findViewById(R.id.conversion_output_tv);
        //EditText
        mInputET = (EditText) view.findViewById(R.id.conversion_input_et);
        mInputET.setOnKeyListener(this);
        //Spinner
        mSpinnerOne = (Spinner) view.findViewById(R.id.first_spinner);
        ArrayAdapter<CharSequence> adapterOne = ArrayAdapter.createFromResource(getActivity(),
                R.array.speed_spinner_types, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOne.setAdapter(adapterOne);

        mSpinnerTwo = (Spinner) view.findViewById(R.id.second_spinner);
        final SpinnerHelp sh = new SpinnerHelp(speedType);
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
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                SpeedConversion sc = new SpeedConversion();
                sc.setConversionType(mSpinnerOne.getSelectedItem().toString());
                sc.setConversionTypeTwo(mSpinnerTwo.getSelectedItem().toString());
                mOutcomeTV.setText(sc.convert(Double.parseDouble(mInputET.getText().toString())));
            }
        }
        return false;
    }
}
