package com.rgsc.myapplication.SwipeBackLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rgsc.myapplication.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class IntentTestMainActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test_main);
    }
}