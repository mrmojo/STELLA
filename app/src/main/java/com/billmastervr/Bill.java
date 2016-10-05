package com.billmastervr;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KTenshi on 10/3/2016.
 */
public class Bill implements Parcelable
{
    private String billMerchant;
    private Double billAmount;
    private String billMonth;
    private Boolean billStatus;


    public Bill(String billMerchant, Double billAmount, String billMonth, Boolean billStatus)
    {
        this.billMerchant = billMerchant;
        this.billAmount = billAmount;
        this.billMonth = billMonth;
        this.billStatus = billStatus;
    }

    public String getBillMerchant() {
        return billMerchant;
    }

    public void setBillMerchant(String billMerchant) {
        this.billMerchant = billMerchant;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }


    public Boolean getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Boolean billStatus) {
        this.billStatus = billStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getBillMerchant());
        dest.writeDouble(getBillAmount());
        dest.writeString(getBillMonth());
        dest.writeByte((byte) (getBillStatus() ? 1 : 0)); ;
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public Bill(Parcel in) {
        setBillMerchant(in.readString());
        setBillAmount(in.readDouble());
        setBillMonth(in.readString());
        setBillStatus(in.readByte() != 0);
    }
}
