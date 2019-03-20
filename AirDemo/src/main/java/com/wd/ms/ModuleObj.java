package com.wd.ms;

import android.os.Parcel;
import android.os.Parcelable;

public class ModuleObj implements Parcelable {
    private String[] strs;
    private int[] ints;
    private float[] floats;

    public String[] getStrs() {
        return strs;
    }

    public void setStrs(String[] strs) {
        this.strs = strs;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public float[] getFloats() {
        return floats;
    }

    public void setFloats(float[] floats) {
        this.floats = floats;
    }

    protected ModuleObj(Parcel in) {
        in.readIntArray(ints);
        in.readFloatArray(floats);
        in.readStringArray(strs);
    }

    public static final Creator<ModuleObj> CREATOR = new Creator<ModuleObj>() {
        @Override
        public ModuleObj createFromParcel(Parcel in) {
            return new ModuleObj(in);
        }

        @Override
        public ModuleObj[] newArray(int size) {
            return new ModuleObj[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(ints);
        dest.writeFloatArray(floats);
        dest.writeStringArray(strs);
    }
}
