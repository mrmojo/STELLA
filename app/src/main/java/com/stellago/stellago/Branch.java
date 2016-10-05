package com.stellago.stellago;

/**
 * Created by javelino on 10/5/2016.
 */

public class Branch {
    private int branchID;
    private double branchLatitude;
    private double branchLongitude;
    private String branchName;
    private int branchRate;

    public Branch(int branchID, double branchLatitude, double branchLongitude, String branchName, int branchRate) {
        this.branchID = branchID;
        this.branchLatitude = branchLatitude;
        this.branchLongitude = branchLongitude;
        this.branchName = branchName;
        this.branchRate = branchRate;
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

    public String getBranchRateString() {
        return Integer.toString(this.branchRate);
    }
}
