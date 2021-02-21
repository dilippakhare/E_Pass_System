package com.tkit.epasssystem.model;

public class ModelPassesType{
    private float PassTypeId;
    private String PassName;
    private boolean IsRoutType;
    private boolean IsDailyPass;
    private float DailyPassPrice;


    // Getter Methods

    public float getPassTypeId() {
        return PassTypeId;
    }

    public String getPassName() {
        return PassName;
    }

    public boolean getIsRoutType() {
        return IsRoutType;
    }

    public boolean getIsDailyPass() {
        return IsDailyPass;
    }

    public float getDailyPassPrice() {
        return DailyPassPrice;
    }

    // Setter Methods

    public void setPassTypeId(float PassTypeId) {
        this.PassTypeId = PassTypeId;
    }

    public void setPassName(String PassName) {
        this.PassName = PassName;
    }

    public void setIsRoutType(boolean IsRoutType) {
        this.IsRoutType = IsRoutType;
    }

    public void setIsDailyPass(boolean IsDailyPass) {
        this.IsDailyPass = IsDailyPass;
    }

    public void setDailyPassPrice(float DailyPassPrice) {
        this.DailyPassPrice = DailyPassPrice;
    }


    public String toString()
    {
        return  getPassName();
    }
}