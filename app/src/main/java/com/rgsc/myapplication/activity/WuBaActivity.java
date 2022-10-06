package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.adapter.ListScreenMenuAdapter;
import com.rgsc.myapplication.outer.ListDataScreenView;
import com.rgsc.myapplication.widgetView.MessageBubbleView;
import com.rgsc.myapplication.widgetViewGroup.LoveLayout;


public class WuBaActivity extends AppCompatActivity implements MessageBubbleView.BubleDispearListener {
//    二手车之家的菜单控价
private ListDataScreenView listDataScreenView;
//直播喜欢点赞的控件
    private LoveLayout loveLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wu_ba);
//        listDataScreenView = findViewById(R.id.list_data_screen_view);
//        listDataScreenView.setAdapter( new ListScreenMenuAdapter(WuBaActivity.this));
//        MessageBubbleView.attach(findViewById(R.id.text),this);
        loveLayout = findViewById(R.id.love_layout);
    }

    @Override
    public void disminss() {

    }
    public void addlove(View view){
        loveLayout.addLove();
    }
}