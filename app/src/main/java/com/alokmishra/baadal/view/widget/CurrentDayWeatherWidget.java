package com.alokmishra.baadal.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokmishra.baadal.R;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;

public class CurrentDayWeatherWidget extends RelativeLayout implements BasicViewWidget<CurrentWeatherItemData>{

    private TextView mCurrentTemp;
    private TextView mHighTemp;
    private TextView mLowTemp;
    private TextView mText;
    private TextView mDay;
    private TextView mWindSpeed;
    private TextView mSunRise;
    private TextView mSunSet;
    private TextView mCity;
    
    public CurrentDayWeatherWidget(Context context) {
        super(context);
    }

    public CurrentDayWeatherWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentDayWeatherWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCity = findViewById(R.id.city);
        mDay = findViewById(R.id.day);
        mText = findViewById(R.id.text);
        mHighTemp = findViewById(R.id.high);
        mLowTemp = findViewById(R.id.low);
        mCurrentTemp = findViewById(R.id.current_temp);
    }

    @Override
    public void setData(CurrentWeatherItemData data) {
        mCity.setText(data.getCity());
        mHighTemp.setText(data.getHighTemp());
        mLowTemp.setText(data.getLowTemp());
        mText.setText(data.getText());
        mCurrentTemp.setText(data.getCurrentTemp());
        mDay.setText(data.getDay());
    }
}
