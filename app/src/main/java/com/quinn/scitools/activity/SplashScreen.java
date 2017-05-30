package com.quinn.scitools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;
import com.quinn.scitools.R;

import presenter.SplashActivityPresenter;

public class SplashScreen extends AppCompatActivity implements
        SplashActivityPresenter.SplashActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SplashActivityPresenter presenter = SplashActivityPresenter.getInstance();
        presenter.setView(this);
        presenter.startBackgroundTasks();
    }

    @Override
    public void startMainActivity() {
        Stetho.initializeWithDefaults(this);
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}