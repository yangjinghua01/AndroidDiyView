package com.rgsc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String s = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        TextView TextView = convertView.findViewById(R.id.tx);
        TextView.setText(s);
        return convertView ;
    }
}
