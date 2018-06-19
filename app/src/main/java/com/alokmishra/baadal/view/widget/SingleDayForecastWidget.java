package com.alokmishra.baadal.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alokmishra.baadal.R;
import com.alokmishra.baadal.module.util.CommonUtils;
import com.alokmishra.baadal.view.model.SingleDayForecastItemData;

public class SingleDayForecastWidget extends LinearLayout implements  BasicViewWidget<SingleDayForecastItemData> {

    private TextView mDay;
    private TextView mHigh;
    private TextView mLow;
    private ImageView mForecastIcon;
    
    public SingleDayForecastWidget(Context context) {
        super(context);
    }

    public SingleDayForecastWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleDayForecastWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDay = findViewById(R.id.day);
        mHigh = findViewById(R.id.high);
        mLow = findViewById(R.id.low);
        mForecastIcon = findViewById(R.id.forecast_icon);
    }

    @Override
    public void setData(SingleDayForecastItemData data) {
        mDay.setText(data.getDay());
        mHigh.setText(data.getHighTemp());
        mLow.setText(data.getLowTemp());
        mForecastIcon.setImageResource(CommonUtils.getWeatherDrawableResource(data.getText()));
    }
}
