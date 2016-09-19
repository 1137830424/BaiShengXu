package com.sven.my.test.custom.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SendTimePickerDialog extends AlertDialog {

    /**
     * 回调接口
     */
    private OnReturnDateListener mOnReturnDateListener;


    /**
     * 选择器主布局
     */
    private SendTimePicker mSendTimePicker;


    public SendTimePickerDialog(Context context){
        this(context,null);
    }

    public SendTimePickerDialog(Context context, final OnReturnDateListener onReturnDateListener) {
        super(context);

        //设置回调监听
        mOnReturnDateListener = onReturnDateListener;

        //初始化布局
        mSendTimePicker = new SendTimePicker(getContext());
        setView(mSendTimePicker); //把选择器添加到提示框

        //设置标题
        setTimeTitle(); //设置标题

        //设置监听 数值选择改变监听
        mSendTimePicker.setOnSelectChangedListener(new SendTimePicker.OnSelectChangedListener() {
            @Override
            public void onSelectChanged(int newDate, int newHour, int newMinute) {
                setTimeTitle(SendTimePicker.dateToString(newDate)
                        , newHour, newMinute); //设置标题
            }
        });


        //点击确定的监听
        setButton(AlertDialog.BUTTON_POSITIVE, "确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onReturnDateListener != null){
                    onReturnDateListener.onReturnDate(mSendTimePicker.getDate()
                            ,mSendTimePicker.getHour(),mSendTimePicker.getMinute());
                }
            }
        });
    }


    /**
     * 设置标题
     */
    private void setTimeTitle(){
        //获取时间
        int date = mSendTimePicker.getDate();
        int hour = mSendTimePicker.getHour();
        int minute = mSendTimePicker.getMinute();

        this.setTimeTitle(SendTimePicker.dateToString(date),hour,minute);

    }

    /**
     * 设置标题
     */
    private void setTimeTitle(String date, int hour, int minute){
        String titleStr = String.format("%s    %d:%d",date,hour,minute);
        setTitle(titleStr);
    }


    public void setOnReturnDateListener(OnReturnDateListener mOnReturnDateListener) {
        this.mOnReturnDateListener = mOnReturnDateListener;
    }

    /**
     * 返回数据的接口
     */
    public interface OnReturnDateListener{
        void onReturnDate(int date, int hour, int minute);
    }

}
