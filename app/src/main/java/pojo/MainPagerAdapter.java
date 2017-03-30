package pojo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.tabs.LengthFragment;
import fragments.tabs.MassFragment;
import fragments.tabs.SpeedFragment;
import fragments.tabs.TemperatureFragment;

/**
 * Created by quinn on 3/10/17.
 */

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
                SpeedFragment sf = SpeedFragment.newInstance();
                return sf;
            case 1:
                TemperatureFragment tf = TemperatureFragment.newInstance();
                return tf;
            case 2:
                LengthFragment lf = LengthFragment.newInstance();
                return lf;
            case 3:
                MassFragment mf = MassFragment.newInstance();
                return mf;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabNumbers;
    }
}
