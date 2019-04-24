package com.wd.airdemo.module;

public class DataUtil {
    public static final int TRANSFER_VALUE_00 = 0x00;
    public static final int TRANSFER_VALUE_01 = 0x01;
    public static final int TRANSFER_VALUE_02 = 0x02;
    public static final int TRANSFER_VALUE_03 = 0x03;
    public static final int TRANSFER_VALUE_04 = 0x04;
    public static final int TRANSFER_VALUE_05 = 0x05;
    public static final int TRANSFER_VALUE_06 = 0x06;
    public static final int TRANSFER_VALUE_07 = 0x07;
    public static final int TRANSFER_VALUE_08 = 0x08;
    public static final int TRANSFER_VALUE_09 = 0x09;
    public static final int TRANSFER_VALUE_10 = 0x10;

    //空调工作模式
    public static final int RUN_MODE_DRIVING = 1;
    public static final int RUN_MODE_PARKING_DAYTIME = 2;
    public static final int RUN_MODE_PARKING_NIGHT = 3;

    //空调内/外循环
    public static final int LOOP_MODE_IN = 0;
    public static final int LOOP_MODE_OUT = 1;
    public static final int LOOP_MODE_IN_OUT = 2;

    //空调制冷或制热模式
    public static final int ColdMode = 1;
    public static final int HeatMode = 2;

    //空调出风模式
    public static final int OutletModeFace = 1;
    public static final int OutletModeFaceFoot = 2;
    public static final int OutletModeFoot = 3;
    public static final int OutletModeFootDefrost = 4;
    public static final int OutletModeDefrost = 5;

    //空调风量最高档位，不同工作模式下值不同
    public static final int AIR_LEVEL_MAX_DRIVING = 8;
    public static final int AIR_LEVEL_MAX_PARKING_DAYTIME = 4;
    public static final int AIR_LEVEL_MAX_PARKING_NIGHT = 2;

    //空调温度最高档位，不同工作模式下值不同
    public static final int TEMP_LEVEL_MAX_DRIVING = 32;
    public static final int TEMP_LEVEL_MAX_PARKING_DAYTIME = 12;
    public static final int TEMP_LEVEL_MAX_PARKING_NIGHT = 12;

    //空调温度最低值，不同工作模式下值不同
    public static final int TEMP_MIN_VALUE_DRIVING = 165;
    public static final int TEMP_MIN_VALUE_PARKING_DAYTIME = 240;
    public static final int TEMP_MIN_VALUE_PARKING_NIGHT = 240;

    //空调温度步进，不同工作模式下值相同
    public static final int TEMP_STEP = 5;

}
