package com.bancomo.objects.templates.loans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankjindal on 02/10/16.
 */

public class Status implements Parcelable {

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
        return "Status{" +
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

    public Status() {
    }

    protected Status(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.code = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Status> CREATOR =
            new Parcelable.Creator<Status>() {
                @Override
                public Status createFromParcel(Parcel source) {
                    return new Status(source);
                }

                @Override
                public Status[] newArray(int size) {
                    return new Status[size];
                }
            };
}
