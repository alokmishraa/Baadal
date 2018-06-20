package com.alokmishra.baadal.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokmishra.baadal.R;
import com.alokmishra.baadal.module.util.CommonUtils;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;

public class CurrentDayWeatherWidget extends RelativeLayout implements BasicViewWidget<CurrentWeatherItemData>{

    private TextView mCurrentTemp;
    private TextView mHighTemp;
    private TextView mLowTemp;
    private TextView mDesc;
    private TextView mWindSpeed;
    private TextView mSunRise;
    private TextView mSunSet;
    private TextView mCity;
    private TextView mHumid;
    private ImageView mForecastIcon;

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
        mDesc =  findViewById(R.id.desc);
        mHighTemp = findViewById(R.id.high);
        mLowTemp = findViewById(R.id.low);
        mCurrentTemp = findViewById(R.id.current_temp);
        mCity =  findViewById(R.id.city);
        mHumid =  findViewById(R.id.humid);
        mWindSpeed =  findViewById(R.id.wind);
        mForecastIcon = findViewById(R.id.forecast_icon);
    }

    @Override
    public void setData(CurrentWeatherItemData data) {
        mCity.setText(data.getCity());
        mDesc.setText(data.getText());
        mHighTemp.setText(data.getHighTemp());
        mLowTemp.setText(data.getLowTemp());
        mCurrentTemp.setText(data.getCurrentTemp());
        mCity.setText(data.getCity());
        mHumid.setText(data.getHumidity());
        mWindSpeed.setText(data.getWindSpeed());
        mForecastIcon.setImageResource(CommonUtils.getWeatherDrawableResource(data.getText()));
    }
}
