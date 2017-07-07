package fragments.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quinn.scitools.R;

import presenter.ContactFragmentPresenter;

public class ContactFragment extends Fragment implements View.OnClickListener,
        ContactFragmentPresenter.ContactFragmentView {

    private FloatingActionButton mFab;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab) {
            ContactFragmentPresenter presenter = new ContactFragmentPresenter(this);
            presenter.createEmail();
        }
    }

    @Override
    public void emailIntent(String[] recipient, String subject) {
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, recipient);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        try{
            startActivity(Intent.createChooser(email, "Choose an email client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No email client installed.", Toast.LENGTH_LONG).show();
        }
    }
}
