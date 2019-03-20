package com.wd.airdemo.module;

public class CarBusCallBack extends BaseCallBack {
    @Override
    protected void update(int updateCode, int[] ints, String[] strs) {
        if (updateCode > 0 && updateCode <= FinalCanbus.U_MAX) {
            if (updateCode == FinalCanbus.U_ManufactureSN) {
                if (strs != null && strs.length > 0) {
                    DataCarbus.sFactorySn = strs[0];
                    DataCarbus.NOTIFY[updateCode].onNotify();
                }
            } else if (updateCode == FinalCanbus.U_ReportStatus
                    || updateCode == FinalCanbus.U_TIME_DATE) { // 16个字节 太长了....
                //..
            } else if (ints != null && ints.length > 0) {
                System.out.println(getClass().getSimpleName() + ":update " + updateCode );
                if (DataCarbus.DATA[updateCode] != ints[0]) {
                    DataCarbus.DATA[updateCode] = ints[0];
                    DataCarbus.NOTIFY[updateCode].onNotify();
                }
            }
        }
    }
}
