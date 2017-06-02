package presenter;

import fragments.tabs.ContactFragment;

public class ContactFragmentPresenter {

    private final static String[] RECIPIENT = {"qpommer0@gmail.com"};
    private final static String SUBJECT = "El Conversion Gato Message";

    private ContactFragment view;

    public ContactFragmentPresenter(ContactFragment view) {
        this.view = view;
    }

    public interface ContactFragmentView {
        void emailIntent(String[] recipient, String subject);
    }

    public void createEmail() {
        view.emailIntent(RECIPIENT, SUBJECT);
    }
}
