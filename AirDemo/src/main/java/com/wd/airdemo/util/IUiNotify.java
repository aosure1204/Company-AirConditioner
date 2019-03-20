package com.wd.airdemo.util;

import android.view.View;

public interface IUiNotify {
    void onNotify(int updateCode, int[] ints, float[] flts, String[] strs);

//    void onClick(View v);
}