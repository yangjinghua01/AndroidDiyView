package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.adapter.ListScreenMenuAdapter;
import com.rgsc.myapplication.outer.ListDataScreenView;
import com.rgsc.myapplication.widgetView.MessageBubbleView;


public class WuBaActivity extends AppCompatActivity implements MessageBubbleView.BubleDispearListener {
private ListDataScreenView listDataScreenView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wu_ba);
//        listDataScreenView = findViewById(R.id.list_data_screen_view);
//        listDataScreenView.setAdapter( new ListScreenMenuAdapter(WuBaActivity.this));
        MessageBubbleView.attach(findViewById(R.id.text),this);
    }

    @Override
    public void disminss() {

    }
}