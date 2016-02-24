package com.lamp.yuandaima.ui;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 所有Activity的基类
 * Created by baishixin on 16/2/23.
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
