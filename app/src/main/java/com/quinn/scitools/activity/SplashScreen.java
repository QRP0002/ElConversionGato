package com.quinn.scitools.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.facebook.stetho.Stetho;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.quinn.scitools.R;

import presenter.SplashActivityPresenter;

public class SplashScreen extends AppCompatActivity implements
        SplashActivityPresenter.SplashActivityView {

    AlertDialog mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firebase Messaging Subscription
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        SplashActivityPresenter presenter = new SplashActivityPresenter(this);
        presenter.networkFlow(isNetworkConnected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAlert != null) { mAlert.dismiss(); }
        SplashActivityPresenter presenter = new SplashActivityPresenter(this);
        presenter.networkFlow(isNetworkConnected());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAlert != null) { mAlert.dismiss(); }
    }

    @Override
    public void startMainActivity() {
        Stetho.initializeWithDefaults(this);
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void startConnectionDialog() {
        mAlert = new AlertDialog.Builder(this)
                .setTitle("No Network Connection")
                .setMessage("Please check that you have a data connection and try again.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}