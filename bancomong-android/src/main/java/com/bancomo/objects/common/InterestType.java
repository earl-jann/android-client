package com.bancomo.objects.common;

/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajan on 13/3/16.
 */
public class InterestType implements Parcelable {

    private Integer id;
    private String code;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.code);
        dest.writeString(this.value);
    }

    public InterestType() {
    }

    protected InterestType(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.code = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<InterestType> CREATOR = new Parcelable
            .Creator<InterestType>() {
        @Override
        public InterestType createFromParcel(Parcel source) {
            return new InterestType(source);
        }

        @Override
        public InterestType[] newArray(int size) {
            return new InterestType[size];
        }
    };
}
