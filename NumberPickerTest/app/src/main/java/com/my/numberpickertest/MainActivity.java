package com.my.numberpickertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyNumberPicker mMyNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyNumberPicker = (MyNumberPicker) findViewById(R.id.numberPicker);


        mMyNumberPicker.setMinValue(0); //设置最小值
        mMyNumberPicker.setMaxValue(10); //设置最大值
        mMyNumberPicker.setValue(0); //设置当前值


        //设置分割线的颜色 设置0可以让颜色变透明
        mMyNumberPicker.setNumberPickerDividerColor(0xffff0000);

    }
}
