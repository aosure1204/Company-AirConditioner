package com.wd.airconditioner;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.airdemo.module.DataCarbus;
import com.wd.airdemo.module.DataUtil;
import com.wd.airdemo.module.FinalCanbus;
import com.wd.airdemo.module.RemoteTools;
import com.wd.airdemo.util.IUiNotify;


public class LauncherActivity extends AppCompatActivity implements View.OnClickListener ,ArcSeekBar.OnProgressChangedListener{
    private static final String TAG = "MainActivity";
    private static final String TAG_AirLevel = "MainActivity.AirLevel";
    private static final String TAG_Temperature = "MainActivity.Temperature";

    private int mRunMode;
    private boolean isParingDaytimeNightSelected;
    private boolean isDrivingACSelected;
    private boolean isDrivingECOSelected;
    private boolean isAutoMode;
    private int mLoopMode;
    private int mColdHeatMode; //冷风暖风模式
    private int mOutletMode;  // 出风模式：吹脸、吹脚、除霜
    private int mAirLevel;
    private int mTempMinValue;
    private int mAirLevelMax;
    private int mTempLevelMax;


    private int[] loopModeArray = {DataUtil.LOOP_MODE_IN, DataUtil.LOOP_MODE_OUT, DataUtil.LOOP_MODE_IN_OUT};
    private int[] loopModeResArray = {R.drawable.ic_loop_in, R.drawable.ic_loop_out, R.drawable.ic_loop_int_out};

    private TextView mTextActionBarTitle;
    private View mBtnActionBarBack;
    private ImageButton mBtnParkingDaytimeNight;
    private ImageButton mBtnDrivingAC;
    private ImageButton mBtnDrivingEco;
    private ImageButton mBtnLoopMode;
    private ArcSeekBar mArcSeekBarAirLevel;
    private ArcSeekBar mArcSeekBarTempLevel;
    private TextView mTextAirLevelValue;
    private TextView mTextTemperatureValue;
    private ImageView mImgWindDirectionFace;
    private ImageView mImgWindDirectionFoot;
    private ImageView mImgShowWindWindow;
    private ImageView mImgShowWindPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mBtnActionBarBack = findViewById(R.id.action_bar_back);
        mBtnActionBarBack.setOnClickListener(this);
        mTextActionBarTitle = (TextView) findViewById(R.id.action_bar_title);
        mBtnParkingDaytimeNight = (ImageButton) findViewById(R.id.btn_parking_daytime_night);
        mBtnParkingDaytimeNight.setOnClickListener(this);
        mBtnDrivingAC = (ImageButton) findViewById(R.id.btn_driving_ac);
        mBtnDrivingAC.setOnClickListener(this);
        mBtnDrivingEco = (ImageButton) findViewById(R.id.btn_driving_eco);
        mBtnDrivingEco.setOnClickListener(this);
        mBtnLoopMode = (ImageButton) findViewById(R.id.btn_loop_mode);
        mBtnLoopMode.setOnClickListener(this);
        mArcSeekBarAirLevel = (ArcSeekBar) findViewById(R.id.arc_seek_bar_air_level);
        mArcSeekBarAirLevel.setOnProgressChangedListener(this);
        mArcSeekBarTempLevel = (ArcSeekBar) findViewById(R.id.arc_seek_bar_temp);
        mArcSeekBarTempLevel.setOnProgressChangedListener(this);
        mTextAirLevelValue = (TextView) findViewById(R.id.text_air_level_value);
        mTextTemperatureValue = (TextView) findViewById(R.id.text_temperature_value);
        mImgWindDirectionFace = (ImageView) findViewById(R.id.img_wind_direction_face);
        mImgWindDirectionFoot = (ImageView) findViewById(R.id.img_wind_direction_foot);
        mImgShowWindWindow = (ImageView) findViewById(R.id.img_show_wind_window);
        mImgShowWindPeople = (ImageView) findViewById(R.id.img_show_wind_people);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerDataListener();

        // 注意以下代码是为了没有收到lib层数据时，应用依然能正常显示而写。正式发布时需要去掉这行代码。
        onRunModeChange(DataUtil.TRANSFER_VALUE_00);
        //****************************
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterDataListener();
    }

    /**
    * 添加数据监听
    * */
    private void registerDataListener(){
        for(int i = 0; i < FinalCanbus.U_MAX; i++) {
            DataCarbus.NOTIFY[i].addNotify(mNotify, 1);
        }
    }

    /**
     * 移除数据监听
     * */
    private void unregisterDataListener(){
        for(int i = 0; i < FinalCanbus.U_MAX; i++) {
            DataCarbus.NOTIFY[i].removeNotify(mNotify);
        }
    }

    private IUiNotify mNotify = new IUiNotify() {
        @Override
        public void onNotify(int updateCode, int[] ints, float[] flts, String[] strs) {
            int value = DataCarbus.DATA[updateCode];
            System.out.println("airdemo:onNotify  " + updateCode + ":" + value);
            switch (updateCode) {
                case FinalCanbus.U_RunMode:
                    onRunModeChange(value);
                    break;
                case FinalCanbus.U_AcMode:
                    onAcModeChange(value);
                    break;
                case FinalCanbus.U_Eco:
                    onEcoChange(value);
                    break;
                case FinalCanbus.U_AutoMode:
                    onAutoModeChange(value);
                    break;
                case FinalCanbus.U_LoopMode:
                    onLoopModeChange(value);
                    break;
                case FinalCanbus.U_ColdHeat:
                    onColdHeatModeChange(value);
                    break;
                case FinalCanbus.U_OutletMode:
                    onOutletModeChange(value);
                    break;
                case FinalCanbus.U_AirLevel:
                    onAirLevelChange(value, false);
                    break;
                case FinalCanbus.U_Temp:
                    onTemperatureChange(value, false);
                    break;
            }
        }
    };

    /**
    * 空调工作模式
    *
    * 返回值有以下几种：
    * 0x00：行车模式
    * 0x01: 驻车白天模式
    * 0x02: 驻车夜晚模式
    * 0x03：否定应答
    * */
    public void onRunModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_00:
                mRunMode = DataUtil.RUN_MODE_DRIVING;
                mBtnParkingDaytimeNight.setEnabled(false);
                mBtnDrivingAC.setEnabled(true);
                mBtnDrivingEco.setEnabled(true);
                mBtnLoopMode.setEnabled(true);
                mTextActionBarTitle.setText(R.string.driving_mode);
                mArcSeekBarAirLevel.setMaxProgress(DataUtil.AIR_LEVEL_MAX_DRIVING);
                mArcSeekBarTempLevel.setMaxProgress(DataUtil.TEMP_LEVEL_MAX_DRIVING);
                mAirLevelMax = DataUtil.AIR_LEVEL_MAX_DRIVING;
                mTempLevelMax = DataUtil.TEMP_LEVEL_MAX_DRIVING;
                mTempMinValue = DataUtil.TEMP_MIN_VALUE_DRIVING;
                break;
            case DataUtil.TRANSFER_VALUE_01:
                mRunMode = DataUtil.RUN_MODE_PARKING_DAYTIME;
                mBtnParkingDaytimeNight.setEnabled(true);
                mBtnParkingDaytimeNight.setSelected(true);
                mBtnDrivingAC.setEnabled(false);
                mBtnDrivingEco.setEnabled(false);
                mBtnLoopMode.setEnabled(true);
                mTextActionBarTitle.setText(R.string.parking_mode);
                mArcSeekBarAirLevel.setMaxProgress(DataUtil.AIR_LEVEL_MAX_PARKING_DAYTIME);
                mArcSeekBarTempLevel.setMaxProgress(DataUtil.TEMP_LEVEL_MAX_PARKING_DAYTIME);
                mAirLevelMax = DataUtil.AIR_LEVEL_MAX_PARKING_DAYTIME;
                mTempLevelMax = DataUtil.TEMP_LEVEL_MAX_PARKING_DAYTIME;
                mTempMinValue = DataUtil.TEMP_MIN_VALUE_PARKING_DAYTIME;
                break;
            case DataUtil.TRANSFER_VALUE_02:
                mRunMode = DataUtil.RUN_MODE_PARKING_NIGHT;
                mBtnParkingDaytimeNight.setEnabled(true);
                mBtnParkingDaytimeNight.setSelected(false);
                mBtnDrivingAC.setEnabled(false);
                mBtnDrivingEco.setEnabled(false);
                mBtnLoopMode.setEnabled(false);
                mTextActionBarTitle.setText(R.string.parking_mode);
                mArcSeekBarAirLevel.setMaxProgress(DataUtil.AIR_LEVEL_MAX_PARKING_NIGHT);
                mArcSeekBarTempLevel.setMaxProgress(DataUtil.TEMP_LEVEL_MAX_PARKING_NIGHT);
                mAirLevelMax = DataUtil.AIR_LEVEL_MAX_PARKING_NIGHT;
                mTempLevelMax = DataUtil.TEMP_LEVEL_MAX_PARKING_NIGHT;
                mTempMinValue = DataUtil.TEMP_MIN_VALUE_PARKING_NIGHT;
                break;
        }
    }

    /**
    * 空调AC
    *
    * 0x00：无动作
    * 0x01：AC功能
    * 0x10：否定应答
    * */
    private void onAcModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_00:
                isDrivingACSelected = false;
                break;
            case DataUtil.TRANSFER_VALUE_01:
                isDrivingACSelected = true;
                break;
        }
        mBtnDrivingAC.setSelected(isDrivingACSelected);
    }

    /**
     * 空调ECO
     *
     * 0x00：无动作
     * 0x01：ECO功能
     * 0x10：否定应答
     * */
     private  void onEcoChange(int data) {
         switch (data) {
             case DataUtil.TRANSFER_VALUE_00:
                 isDrivingECOSelected = false;
                 break;
             case DataUtil.TRANSFER_VALUE_01:
                 isDrivingECOSelected = true;
                 break;
         }
         mBtnDrivingAC.setSelected(isDrivingECOSelected);
     }

    /**
     * 空调Auto模式
     *
     * 0x00：非AUTO
     * 0x01：AUTO状态
     * 0x02：否定应答
     * */
    private void onAutoModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_00:
                isAutoMode = false;
                break;
            case DataUtil.TRANSFER_VALUE_01:
                isAutoMode = true;
                break;
        }
    }

    /**
     * 空调内外循环
     *
     * 0x00：内循环
     * 0x01：外循环
     * 0x02：自动内外循环（仅AUTO模式下）
     * 0x03：否定应答
     * */
    private  void onLoopModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_00:
                mLoopMode = DataUtil.LOOP_MODE_IN;
                mBtnLoopMode.setImageResource(R.drawable.ic_loop_in);
                break;
            case DataUtil.TRANSFER_VALUE_01:
                mLoopMode = DataUtil.LOOP_MODE_OUT;
                mBtnLoopMode.setImageResource(R.drawable.ic_loop_out);
                break;
            case DataUtil.TRANSFER_VALUE_02:
                mLoopMode = DataUtil.LOOP_MODE_IN_OUT;
                mBtnLoopMode.setImageResource(R.drawable.ic_loop_int_out);
                break;
        }
    }

    /**
    * 空调制冷或制热模式
    *
    * 0x00：制冷模式
    * 0x01：制热模式
    * */
    private void onColdHeatModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_00:
                mColdHeatMode = DataUtil.ColdMode;
                break;
            case DataUtil.TRANSFER_VALUE_01:
                mColdHeatMode = DataUtil.HeatMode;
                break;
        }
        updateLeftSeatImages();
        updateRightDriveImages();
        //... unfinished.   需更换风量进度条和温度进度条的颜色
    }

    /** 空调出风模式
    *
    * 0x01：吹面
    * 0x02：吹面吹脚
    * 0x03：吹脚
    * 0x04：吹脚除霜
    * 0x05：前除霜
    * 0x06：否定应答
    * */
    private void onOutletModeChange(int data) {
        switch (data) {
            case DataUtil.TRANSFER_VALUE_01:
                mOutletMode = DataUtil.OutletModeFace;
                break;
            case DataUtil.TRANSFER_VALUE_02:
                mOutletMode = DataUtil.OutletModeFaceFoot;
                break;
            case DataUtil.TRANSFER_VALUE_03:
                mOutletMode = DataUtil.OutletModeFoot;
                break;
            case DataUtil.TRANSFER_VALUE_04:
                mOutletMode = DataUtil.OutletModeFootDefrost;
                break;
            case DataUtil.TRANSFER_VALUE_05:
                mOutletMode = DataUtil.OutletModeDefrost;
                break;
        }
        updateLeftSeatImages();
        updateRightDriveImages();
    }

    /**
     * 空调风量
     *
     * 0x00：预留  0x01：1档  0x02：2档   0x03：3档   0x04：4档  0x05：5档   0x06：6档   0x07：7档 0x08：8档  0x09：否定应答
     *
     * @param isFromUser true表示来自用户，false表示来自空调控制器
     * */
    private void onAirLevelChange(int data, boolean isFromUser) {
        if(data < 0 || data > mAirLevelMax) {
            Toast.makeText(this, R.string.air_is_out_of_range, Toast.LENGTH_LONG).show();
            return;
        }
        mAirLevel = data;
        updateAirLevel(data, isFromUser);
        updateRightDriveImages();
    }

    /**
     * 更新风量进度条和风量文本
     *
     * @param isFromUser true表示来自用户，false表示来自空调控制器
     */
    private void updateAirLevel(int airLevel, boolean isFromUser) {
        mTextAirLevelValue.setText(Integer.toString(airLevel));
        if (isFromUser) {
            sendCmd(FinalCanbus.C_AirLevel, airLevel);
        } else {
            mArcSeekBarAirLevel.setProgress(airLevel);
        }
    }

    /**
     * 空调温度
     *
     * 范围:16.5~32.5℃
     * 例如，234表示23.4℃
     *
     * @param isFromUser true表示来自用户，false表示来自空调控制器
     * */
    private void onTemperatureChange(int data, boolean isFromUser) {
        if(data < mTempMinValue || data > (mTempMinValue + DataUtil.TEMP_STEP * mTempLevelMax)) {
            Toast.makeText(this, R.string.temperature_is_out_of_range, Toast.LENGTH_LONG).show();
            return;
        }

        String tempStr = data / 10 + "." + data % 10 + "°C";
        mTextTemperatureValue.setText(tempStr);
        if (isFromUser) {
            sendCmd(FinalCanbus.C_Temp, data);
        } else {
            int progress = (data - mTempMinValue) / DataUtil.TEMP_STEP;
            mArcSeekBarTempLevel.setProgress(progress);
        }
    }

    /**
     * 当空调出风模式发生改变，或者空调制冷或制热模式发生改变时调用。
     *
     * 更改左边座椅吹脸吹脚图片
     * */
    private void updateLeftSeatImages() {
        //左边座椅吹脸ImageView
        if(mOutletMode == DataUtil.OutletModeFace || mOutletMode == DataUtil.OutletModeFaceFoot) {
            if(mColdHeatMode == DataUtil.ColdMode) {
                mImgWindDirectionFace.setImageResource(R.drawable.ic_wind_direction_face_cold);
            } else if (mColdHeatMode == DataUtil.HeatMode) {
                mImgWindDirectionFace.setImageResource(R.drawable.ic_wind_direction_face_heat);
            }
        } else {
            mImgWindDirectionFace.setImageResource(R.drawable.ic_wind_direction_face_disable);
        }

        //左边座椅吹脚ImageView
        if(mOutletMode == DataUtil.OutletModeFaceFoot || mOutletMode == DataUtil.OutletModeFoot || mOutletMode == DataUtil.OutletModeFootDefrost) {
            if(mColdHeatMode == DataUtil.ColdMode) {
                mImgWindDirectionFoot.setImageResource(R.drawable.ic_wind_direction_foot_cold);
            } else if (mColdHeatMode == DataUtil.HeatMode) {
                mImgWindDirectionFoot.setImageResource(R.drawable.ic_wind_direction_foot_heat);
            }
        } else {
            mImgWindDirectionFoot.setImageResource(R.drawable.ic_wind_direction_foot_disable);
        }
    }

    /**
     * 当空调出风模式发生改变，或者空调制冷或制热模式发生改变，或者风量档位发生改变时调用。
     *
     * 更改右边驾驶位动画
     * */
    private void updateRightDriveImages() {
        // 当空调出风模式发生改变，影响ImageView的显示和隐藏。
        if(mOutletMode == DataUtil.OutletModeFace || mOutletMode == DataUtil.OutletModeFaceFoot || mOutletMode == DataUtil.OutletModeFoot) {
            mImgShowWindPeople.setVisibility(View.VISIBLE);
            mImgShowWindWindow.setVisibility(View.GONE);
        } else if (mOutletMode == DataUtil.OutletModeFootDefrost) {
            mImgShowWindPeople.setVisibility(View.VISIBLE);
            mImgShowWindWindow.setVisibility(View.VISIBLE);
        } else if (mOutletMode == DataUtil.OutletModeDefrost) {
            mImgShowWindPeople.setVisibility(View.GONE);
            mImgShowWindWindow.setVisibility(View.VISIBLE);
        }

        if(mColdHeatMode == DataUtil.ColdMode) {
            //空调制冷模式
            //...还需考虑风量档位
            mImgShowWindWindow.setImageResource(R.drawable.frame_anim_window_cold);
            AnimationDrawable drawable = (AnimationDrawable) mImgShowWindWindow.getDrawable();
            drawable.start();
            //...unfinished.
        } else if(mColdHeatMode == DataUtil.HeatMode) {
            //空调制热模式

            //...unfinished.
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_back:
                finish();
                break;
            case R.id.btn_parking_daytime_night:
                isParingDaytimeNightSelected = !isParingDaytimeNightSelected;
                // control air device
                mRunMode = isParingDaytimeNightSelected == true ? DataUtil.TRANSFER_VALUE_01 : DataUtil.TRANSFER_VALUE_02;
                sendCmd(FinalCanbus.C_RunMode, mRunMode);
                // update UI process logic
                onRunModeChange(mRunMode);
                break;
            case R.id.btn_driving_ac:
                // Switch AC state
                isDrivingACSelected = !isDrivingACSelected;
                mBtnDrivingAC.setSelected(isDrivingACSelected);
                int acValue = isDrivingACSelected == true ? DataUtil.TRANSFER_VALUE_01 : DataUtil.TRANSFER_VALUE_00;
                sendCmd(FinalCanbus.C_AcMode, acValue);
                // Turn off ECO when AC is on
                if(isDrivingACSelected) {
                    isDrivingECOSelected = false;
                    mBtnDrivingEco.setSelected(false);
                    sendCmd(FinalCanbus.C_Eco, DataUtil.TRANSFER_VALUE_00);
                }
                // Turn off AUTO when AC is off and ECO is off
                if(!isDrivingACSelected && !isDrivingECOSelected) {
                    isAutoMode = false;
                    sendCmd(FinalCanbus.C_AutoMode, DataUtil.TRANSFER_VALUE_00);
                }
                break;
            case R.id.btn_driving_eco:
                // Switch ECO state
                isDrivingECOSelected = !isDrivingECOSelected;
                mBtnDrivingEco.setSelected(isDrivingECOSelected);
                int ecoValue = isDrivingECOSelected == true ? DataUtil.TRANSFER_VALUE_01 : DataUtil.TRANSFER_VALUE_00;
                sendCmd(FinalCanbus.C_Eco, ecoValue);
                // Turn off AC when ECO is on
                if(isDrivingECOSelected) {
                    isDrivingACSelected = false;
                    mBtnDrivingAC.setSelected(false);
                    sendCmd(FinalCanbus.C_AcMode, DataUtil.TRANSFER_VALUE_00);
                }
                // Turn off AUTO when AC is off and ECO is off
                if(!isDrivingACSelected && !isDrivingECOSelected) {
                    isAutoMode = false;
                    sendCmd(FinalCanbus.C_AutoMode, DataUtil.TRANSFER_VALUE_00);
                }
                break;
            case R.id.btn_loop_mode:
                /*
                 * loop mode switch
                 *
                 * When AUTO is off, the two loop mode can be switched.
                 * When AUTO is on, the three loop mode can be switched.
                 * */
                int size = isAutoMode == true ? loopModeArray.length : (loopModeArray.length - 1);

                int index = 0;
                for(int i = 0; i < size; i++) {
                    if(loopModeArray[i] == mLoopMode) {
                        index = (i + 1) % size;
                        break;
                    }
                }

                mLoopMode = loopModeArray[index];
                mBtnLoopMode.setImageResource(loopModeResArray[index]);
                sendCmd(FinalCanbus.C_LoopMode, mLoopMode);
                break;
        }
    }

    private void sendCmd(int i, int id) {
        RemoteTools.cmd(i, id);
    }

    public void onProgressChanged(ArcSeekBar arcSeekBar, int progress, boolean isFinalProgress) {
        switch (arcSeekBar.getId()) {
            case R.id.arc_seek_bar_air_level:
                if(isFinalProgress) {
                    onAirLevelChange(progress, true);
                }
                break;
            case R.id.arc_seek_bar_temp:
                if(isFinalProgress) {
                    int tempValue = mTempMinValue + progress * DataUtil.TEMP_STEP;
                    onTemperatureChange(tempValue, true);
                }
                break;
        }
    }

}
