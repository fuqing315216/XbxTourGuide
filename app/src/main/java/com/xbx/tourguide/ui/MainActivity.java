package com.xbx.tourguide.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shuzhen on 2016/3/28.
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startIntent(LoginActivity.class, true);
    }


}
