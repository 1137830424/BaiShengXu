package com.sven.my.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sven.my.test.custom.view.SendTimePicker;
import com.sven.my.test.custom.view.SendTimePickerDialog;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private EditText editText;
    private SendTimePickerDialog mSendTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        findViewById(R.id.button).setOnClickListener(this);


        mSendTimePickerDialog = new SendTimePickerDialog(this, new SendTimePickerDialog.OnReturnDateListener() {
            @Override
            public void onReturnDate(int date, int hour, int minute) {
                //选择后返回的数据
                String dateStr = SendTimePicker.dateToString(date); //把时间转化为 今天 或者 明天
                editText.setText(dateStr + " "+hour+" "+ minute);
            }
        });



    }


    @Override
    public void onClick(View v) {

        mSendTimePickerDialog.show();

    }
}
