package me.alexeygusev.curve.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Simple model
 */
public class Fields extends BaseObservable {

    private int mField01;
    private int mField02;
    private int mField03;
    private int mField04;
    private int mField05;
    private int mField06;

    @Bindable
    public String getField01() {
        return getString(mField01);
    }

    public void setField01(String field1) {
        mField01 = getInt(field1);
        notifyPropertyChanged(BR.field01);
        notifyPropertyChanged(BR.totalSum);
    }

    @Bindable
    public String getField02() {
        return getString(mField02);
    }

    public void setField02(String field2) {
        mField02 = getInt(field2);
        notifyPropertyChanged(BR.field02);
        notifyPropertyChanged(BR.totalSum);
    }

    @Bindable
    public String getField03() {
        return getString(mField03);
    }

    public void setField03(String field3) {
        mField03 = getInt(field3);
        notifyPropertyChanged(BR.field03);
        notifyPropertyChanged(BR.totalSum);
    }

    @Bindable
    public String getField04() {
        return getString(mField04);
    }

    public void setField04(String field4) {
        mField04 = getInt(field4);
        notifyPropertyChanged(BR.field04);
        notifyPropertyChanged(BR.totalSum);
    }

    @Bindable
    public String getField05() {
        return getString(mField05);
    }

    public void setField05(String field5) {
        mField05 = getInt(field5);
        notifyPropertyChanged(BR.field05);
        notifyPropertyChanged(BR.totalSum);
    }

    @Bindable
    public String getField06() {
        return getString(mField06);
    }

    public void setField06(String field6) {
        mField06 = getInt(field6);
        notifyPropertyChanged(BR.field06);
        notifyPropertyChanged(BR.totalSum);
    }
    
    @Bindable
    public String getTotalSum() {
        return String.valueOf(mField01 + mField02 + mField03 + mField04 + mField05 + mField06);
    }

    private int getInt(String value) {
        return TextUtils.isEmpty(value) ? 0 : Integer.parseInt(value);
    }

    private String getString(int value) {
        return value > 0 ? String.valueOf(value) : "";
    }
}
