package com.sven.my.test.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import com.sven.my.test.R;
import java.util.Calendar;


public class SendTimePicker extends FrameLayout {

    /**
     * 时间标志
     */
    public static final int TODAY_FLAG = 0x0;
    public static final int TOMORROW_FLAG = 0x1;

    private static final String TODAY = "今天";
    private static final String TOMORROW = "明天";

    /**
     * 布局里的三个数字选择器
     */
    private NumberPicker mDatePicker;
    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;

    private int mDate;
    private int mHour;
    private int mMinute;
    Calendar mCalendar;

    private String[] mDates = new String[]{
            TODAY
            ,TOMORROW
    };

    /**
     * 选择改变时的回调接口
     */
    private OnSelectChangedListener mOnSelectChangedListener;

    public SendTimePicker(Context context) {
        this(context,null);
    }

    public SendTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //初始化时间
        mCalendar = Calendar.getInstance(); //获取系统时间
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mDate = TODAY_FLAG; //默认为今天

        //初始化布局
        View mainView = LayoutInflater.from(context).inflate(R.layout.send_time_picker,null);
        mDatePicker = (NumberPicker) mainView.findViewById(R.id.datePicker);
        mHourPicker = (NumberPicker) mainView.findViewById(R.id.hourPicker);
        mMinutePicker = (NumberPicker) mainView.findViewById(R.id.minutePicker);

        //设置三个选择器的数值
        mDatePicker.setDisplayedValues(mDates);
        loadPicker(mDatePicker, 0, mDates.length - 1, 0);
        loadPicker(mHourPicker, mHour,23, mHour);
        loadPicker(mMinutePicker, mMinute,59, mMinute);

        //添加到布局中
        addView(mainView);

        //设置监听
        initListener();
    }


    /**
     * 加载选择框
     */
    private void loadPicker(NumberPicker numberPicker, int minValue, int maxValue, int value){
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(value);
    }


    /**
     * 设置监听
     */
    private void initListener() {
        mDatePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //为了让用户不能选择今天之前的时间
                mDate = newVal;
                int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = mCalendar.get(Calendar.MINUTE);
                if(mHour < currentHour) mHour = currentHour;
                if(mMinute < currentMinute) mMinute = currentMinute;

                if(mDate == TODAY_FLAG){
                    loadPicker(mHourPicker,currentHour,23,mHour);
                    loadPicker(mMinutePicker,currentMinute,59,mMinute);
                }else if(mDate == TOMORROW_FLAG){
                    loadPicker(mHourPicker,0,23,mHour);
                    loadPicker(mMinutePicker,0,59,mMinute);
                }

                callOnSelectListener();
            }
        });

        mHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mHour = newVal;
                callOnSelectListener();
            }
        });

        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mMinute = newVal;
                callOnSelectListener();
            }
        });
    }

    /**
     * 把时间转化为可视的
     */
    public static String dateToString(int date){
        return date==TODAY_FLAG ? TODAY : TOMORROW;
    }



    /**
     * 调用改变选择的回调接口
     */
    private void callOnSelectListener(){
        if(mOnSelectChangedListener != null){
            mOnSelectChangedListener.onSelectChanged(mDate, mHour, mMinute);
        }
    }


    public void setOnSelectChangedListener(OnSelectChangedListener mOnSelectChangedListener) {
        this.mOnSelectChangedListener = mOnSelectChangedListener;
    }

    /**
     * 选择发生改变的回调接口
     */
    public interface OnSelectChangedListener{
        void onSelectChanged(int newDate, int newHour, int newMinute);
    }



    public void setDate(int date) {
        this.mDate = date;
        mDatePicker.setValue(date);
    }

    public void setHour(int hour) {
        this.mHour = hour;
        mHourPicker.setValue(hour);
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
        mMinutePicker.setValue(minute);
    }

    public int getDate() {
        return mDate;
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute;
    }
}
