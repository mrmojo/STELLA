package com.stellago.stellago;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javelino on 10/5/2016.
 */

public class Branch implements Parcelable {
    private int branchID;
    private double branchLatitude;
    private double branchLongitude;
    private String branchName;
    private int branchRate;
    private double branchUserRate;

    public Branch(int branchID, double branchLatitude, double branchLongitude, String branchName, int branchRate, double branchUserRate) {
        this.branchID = branchID;
        this.branchLatitude = branchLatitude;
        this.branchLongitude = branchLongitude;
        this.branchName = branchName;
        this.branchRate = branchRate;
        this.branchUserRate = branchUserRate;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public double getBranchLatitude() {
        return branchLatitude;
    }

    public void setBranchLatitude(double branchLatitude) {
        this.branchLatitude = branchLatitude;
    }

    public double getBranchLongitude() {
        return branchLongitude;
    }

    public void setBranchLongitude(double branchLongitude) {
        this.branchLongitude = branchLongitude;
    }

    public double getBranchUserRate() {
        return branchUserRate;
    }

    public void setBranchUserRate(double branchUserRate) {
        this.branchUserRate = branchUserRate;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBranchRate() {
        return branchRate;
    }

    public void setBranchRate(int branchRate) {
        this.branchRate = branchRate;
    }

    public double getBranchRateAverage() {
        return (getBranchRate() + getBranchUserRate())/2;
    }

    @Override
    public String toString() {
        return this.branchName.concat(". Rating = ").concat(Double.toString(getBranchRateAverage())); // Value to be displayed in the Spinner
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getBranchID());
        dest.writeDouble(getBranchLatitude());
        dest.writeDouble(getBranchLongitude());
        dest.writeString(getBranchName());
        dest.writeInt(getBranchRate());
        dest.writeDouble(getBranchUserRate());
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    public Branch(Parcel in) {
        setBranchID(in.readInt());
        setBranchLatitude(in.readDouble());
        setBranchLongitude(in.readDouble());
        setBranchName(in.readString());
        setBranchRate(in.readInt());
        setBranchUserRate(in.readDouble());
    }

}
