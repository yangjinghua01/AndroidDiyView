package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rgsc.myapplication.widgetView.LetterSideBar;
import com.rgsc.myapplication.R;

public class CityPickActivity extends AppCompatActivity {
    private TextView letter_tv;
    private LetterSideBar sidebar;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pick);
        letter_tv = findViewById(R.id.letter_tv);
        sidebar = findViewById(R.id.sidebar);
        view = findViewById(R.id.bg);
        sidebar.setListener(new LetterSideBar.MoveListener() {
            @Override
            public void select(String str, boolean touch) {
                letter_tv.setText(str);
                if (touch){
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }
}