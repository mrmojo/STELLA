package com.billmastervr;

/**
 * Created by KTenshi on 10/3/2016.
 */
public class Bill
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


}
