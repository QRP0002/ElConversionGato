package pojo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.tabs.ConversionFragment;
import fragments.tabs.ContactFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    int mTabNumbers;

    public MainPagerAdapter(FragmentManager fm, int numberOfTabs, Context context) {
        super(fm);
        this.context = context;
        this.mTabNumbers = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ConversionFragment cf = ConversionFragment.newInstance();
                return cf;
            case 1:
                ContactFragment sf = ContactFragment.newInstance();
                return sf;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabNumbers;
    }
}
