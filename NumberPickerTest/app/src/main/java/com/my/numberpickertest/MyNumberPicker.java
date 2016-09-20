package com.my.numberpickertest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;


public class MyNumberPicker extends NumberPicker {
    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * 修改字的大小和颜色
     */
    private void updateView(View view){
        if( view instanceof EditText){
            EditText editText = (EditText) view;
            editText.setTextColor(Color.parseColor("#00ff00")); //修改字的颜色
            editText.setTextSize(30);//修改字的大小
        }
    }

    /**
     * 修改分割线的颜色
     */
    public void setNumberPickerDividerColor(int color){
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivider")){ //找到mSelectionDivider
                pf.setAccessible(true);

                //设置分割线的颜色
                try {
                    pf.set(this, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
