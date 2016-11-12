package com.nhancv.picker.dateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhancv.picker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by nhancao on 11/13/16.
 */

public class CompactDatePicker extends LinearLayout {

    private CompactCalendarView.CompactCalendarViewListener listener;

    public CompactDatePicker(Context context) {
        this(context, null);
    }

    public CompactDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_calendar_date_picker, this);
        setupView();
    }

    private void setListener(CompactCalendarView.CompactCalendarViewListener listener) {
        this.listener = listener;
    }

    private void setupView() {

        final View btCompactDatePickerLeft = findViewById(R.id.btCompactDatePickerLeft);
        final View btCompactDatePickerRight = findViewById(R.id.btCompactDatePickerRight);
        final TextView tvCompactDatePicker = (TextView) findViewById(R.id.tvCompactDatePicker);
        final CompactCalendarView vCompactDatePicker = (CompactCalendarView) findViewById(R.id.vCompactDatePicker);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(vCompactDatePicker.getFirstDayOfCurrentMonth());
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        updateHeaderDate(tvCompactDatePicker, calendar.getTime());

        btCompactDatePickerLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                vCompactDatePicker.showPreviousMonth();
            }
        });
        btCompactDatePickerRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                vCompactDatePicker.showNextMonth();
            }
        });
        tvCompactDatePicker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Date current = Calendar.getInstance().getTime();
                vCompactDatePicker.setCurrentDate(current);
                updateHeaderDate(tvCompactDatePicker, current);
            }
        });
        vCompactDatePicker.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (listener != null) listener.onDayClick(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(firstDayOfNewMonth);
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
                updateHeaderDate(tvCompactDatePicker, calendar.getTime());
                if (listener != null) listener.onMonthScroll(firstDayOfNewMonth);
            }
        });
    }

    private void updateHeaderDate(TextView tvCompactDatePicker, Date updateDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        tvCompactDatePicker.setText(simpleDateFormat.format(updateDate));
    }

}