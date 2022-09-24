package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class QiCheZhiJiaActivity extends AppCompatActivity {
    private ListView ls;
    private List<String> mItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_che_zhi_jia);
        ls = findViewById(R.id.ls);
        mItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mItems.add("Item"+i);
        }
        ListAdapter adapter = new ListAdapter(QiCheZhiJiaActivity.this,R.layout.list_item,mItems);
        ls.setAdapter(adapter);
    }
}