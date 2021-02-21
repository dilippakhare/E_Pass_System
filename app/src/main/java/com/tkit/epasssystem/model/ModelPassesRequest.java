package com.tkit.epasssystem.model;

public class ModelPassesRequest {

    private float RequestId;
    private float UserId;
    private float PassTypeId;
    private String FromDate;
    private String ToDate;
    private String EnterDate;
    private String ApprovedStatus;
    private String QrCodeUrl;
    private float Price;
    private String AuthorityLetter = null;
    private String Organization = null;
    private String IdCard = null;


    // Getter Methods

    public float getRequestId() {
        return RequestId;
    }

    public float getUserId() {
        return UserId;
    }

    public float getPassTypeId() {
        return PassTypeId;
    }

    public String getFromDate() {
        return FromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public String getEnterDate() {
        return EnterDate;
    }

    public String getApprovedStatus() {
        return ApprovedStatus;
    }

    public String getQrCodeUrl() {
        return QrCodeUrl;
    }

    public float getPrice() {
        return Price;
    }

    public String getAuthorityLetter() {
        return AuthorityLetter;
    }

    public String getOrganization() {
        return Organization;
    }

    public String getIdCard() {
        return IdCard;
    }

    // Setter Methods

    public void setRequestId(float RequestId) {
        this.RequestId = RequestId;
    }

    public void setUserId(float UserId) {
        this.UserId = UserId;
    }

    public void setPassTypeId(float PassTypeId) {
        this.PassTypeId = PassTypeId;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public void setEnterDate(String EnterDate) {
        this.EnterDate = EnterDate;
    }

    public void setApprovedStatus(String ApprovedStatus) {
        this.ApprovedStatus = ApprovedStatus;
    }

    public void setQrCodeUrl(String QrCodeUrl) {
        this.QrCodeUrl = QrCodeUrl;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public void setAuthorityLetter(String AuthorityLetter) {
        this.AuthorityLetter = AuthorityLetter;
    }

    public void setOrganization(String Organization) {
        this.Organization = Organization;
    }

    public void setIdCard(String IdCard) {
        this.IdCard = IdCard;
    }
}