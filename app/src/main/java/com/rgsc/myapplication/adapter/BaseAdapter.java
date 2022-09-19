package com.rgsc.myapplication.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter {
//    1.有多少个条目
    public abstract int getCount();
//    2.getView 通过pisition
    public abstract View getView(int Position, ViewGroup parent);
//    3.观察者模式及时通知更新
    public void unregisterDataSetobserver(DataSetObserver observable){

    }
    public void registerDataSetobserver(DataSetObserver observable){

    }
//    刷新使用观察者模式
//    public abstract

}
