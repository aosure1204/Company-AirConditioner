package com.wd.airdemo.module;

public class FinalCanbus {

    private static final int U_Start = 0;
    public static final int U_ColdHeat = U_Start + 1;//冷/制热模式
    public static final int U_OutletMode			    = U_Start + 2;
    public static final int U_AutoMode                  = U_Start + 3;
    public static final int U_Onoff                     = U_Start + 4;
    public static final int U_AirLevel                  = U_Start + 5;
    public static final int U_AcMode                    = U_Start + 6;
    public static final int U_Eco                       = U_Start + 7;
    public static final int U_RunMode                   = U_Start + 8;
    public static final int U_Temp                      = U_Start + 9;
    public static final int U_Init                      = U_Start + 10;
    public static final int U_LoopMode                  = U_Start + 11;
    public static final int U_ReportStatus              = U_Start + 12; // useless
    public static final int U_SoftVersion               = U_Start + 13;
    public static final int U_HardVersion               = U_Start + 14;
    public static final int U_ManufactureSN             = U_Start + 15;
    public static final int U_CarSpeed                  = U_Start + 16;
    public static final int U_TIME_DATE                 = U_Start + 17;
    public static final int U_MANUEL_GEAR_STATE         = U_Start + 18;
    public static final int U_AutoGrear_BackCar         = U_Start + 19;
    public static final int U_TurnSignal                = U_Start + 20;
    public static final int U_VideoPlayReq              = U_Start + 21;
    public static final int U_AcKeyState                = U_Start + 22;
    public static final int U_SystemModeReq             = U_Start + 23;
    public static final int U_BatteryState              = U_Start + 24;
    public static final int U_MAX                       = U_BatteryState + 1;


    public static final int C_Start = 0;
    public static final int C_ColdOrHeatingMode= C_Start + 1;//冷/制热模式
    public static final int C_OutletMode			    = C_Start + 2;
    public static final int C_AutoMode                  = C_Start + 3;
    public static final int C_Onoff                     = C_Start + 4;
    public static final int C_AirLevel                  = C_Start + 5;
    public static final int C_AcMode                    = C_Start + 6;
    public static final int C_Eco                       = C_Start + 7;
    public static final int C_RunMode                   = C_Start + 8;
    public static final int C_Temp                      = C_Start + 9;
    public static final int C_Init                      = C_Start + 10;
    public static final int C_LoopMode                  = C_Start + 11;

    public static final int C_BCM_Horn                  = C_Start + 12; // 喇叭状态 // 0x37
    public static final int C_BCM_AEBS_FCWS             = C_Start + 13;
    public static final int C_BCM_LDWS                  = C_Start + 14;
    public static final int C_BCM_BSD                   = C_Start + 15;
    public static final int C_BCM_FatigueMonitor        = C_Start + 16; // 疲劳检测
    public static final int C_BCM_LeftWindow            = C_Start + 17;
    public static final int C_BCM_RightWindow           = C_Start + 18;
    public static final int C_BCM_SunRoof               = C_Start + 19;
    public static final int C_BCM_ScreenWindow          = C_Start + 20; // 纱窗
    public static final int C_BCM_RemoteAcceler         = C_Start + 21; // 远程油门状态

    public static final int C_TS_PressState             = C_Start + 22; // 触摸按压状态 // 0x35
    public static final int C_TS_CoordinateX            = C_Start + 23; // 触摸X
    public static final int C_TS_CoordinateY            = C_Start + 24; //
    public static final int C_TS_ScreenType             = C_Start + 25; // 屏幕类型
    public static final int C_TS_SystemModeAck          = C_Start + 26; // 系统模式请求应答


    public static final int C_MAX                       = C_TS_SystemModeAck + 1;



}
