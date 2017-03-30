package com.quinn.scitools.activity;

import android.support.v4.app.Fragment;
import fragments.MainFragment;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
