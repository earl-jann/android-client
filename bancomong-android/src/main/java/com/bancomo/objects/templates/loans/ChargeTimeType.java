package com.bancomo.objects.templates.loans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rajan Maurya on 16/07/16.
 */
public class ChargeTimeType implements Parcelable {

    @SerializedName("id")
    Integer id;

    @SerializedName("code")
    String code;

    @SerializedName("value")
    String value;

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
    public String toString() {
        return "ChargeTimeType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
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

    public ChargeTimeType() {
    }

    protected ChargeTimeType(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.code = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<ChargeTimeType> CREATOR =
            new Parcelable.Creator<ChargeTimeType>() {
        @Override
        public ChargeTimeType createFromParcel(Parcel source) {
            return new ChargeTimeType(source);
        }

        @Override
        public ChargeTimeType[] newArray(int size) {
            return new ChargeTimeType[size];
        }
    };
}
