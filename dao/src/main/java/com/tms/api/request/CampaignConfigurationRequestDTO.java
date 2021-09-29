package com.tms.api.request;

public class CampaignConfigurationRequestDTO {

    private Integer cpId;
    private Integer orgId;
    private String name;
    private Integer owner;
    private Integer status;
    private String startdate;
    private String stopdate;
    private Integer createby;
    private String createdate;
    private Integer modifyby;
    private String modifydate;
    private Integer campaignTypeValue;
    private Integer campaignCategoryValue;
    private Integer campaignTagValue;


    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStopdate() {
        return stopdate;
    }

    public void setStopdate(String stopdate) {
        this.stopdate = stopdate;
    }

    public Integer getCreateby() {
        return createby;
    }

    public void setCreateby(Integer createby) {
        this.createby = createby;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Integer getModifyby() {
        return modifyby;
    }

    public void setModifyby(Integer modifyby) {
        this.modifyby = modifyby;
    }

    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public Integer getCampaignTypeValue() {
        return campaignTypeValue;
    }

    public void setCampaignTypeValue(Integer campaignTypeValue) {
        this.campaignTypeValue = campaignTypeValue;
    }

    public Integer getCampaignCategoryValue() {
        return campaignCategoryValue;
    }

    public void setCampaignCategoryValue(Integer campaignCategoryValue) {
        this.campaignCategoryValue = campaignCategoryValue;
    }

    public Integer getCampaignTagValue() {
        return campaignTagValue;
    }

    public void setCampaignTagValue(Integer campaignTagValue) {
        this.campaignTagValue = campaignTagValue;
    }
}

