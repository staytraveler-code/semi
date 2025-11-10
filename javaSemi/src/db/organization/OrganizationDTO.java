package db.organization;

public class OrganizationDTO {
    private String orgCode;
    private String orgId;
    private String orgPwd;
    private String orgName;
    private String orgType;
    private String bizRegNo;
    private String orgTel;
    private String orgEmail;
    private String orgAddress;

    // getter / setter
    public String getOrgCode() { return orgCode; }
    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public String getOrgId() { return orgId; }
    public void setOrgId(String orgId) { this.orgId = orgId; }

    public String getOrgPwd() { return orgPwd; }
    public void setOrgPwd(String orgPwd) { this.orgPwd = orgPwd; }

    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }

    public String getOrgType() { return orgType; }
    public void setOrgType(String orgType) { this.orgType = orgType; }

    public String getBizRegNo() { return bizRegNo; }
    public void setBizRegNo(String bizRegNo) { this.bizRegNo = bizRegNo; }

    public String getOrgTel() { return orgTel; }
    public void setOrgTel(String orgTel) { this.orgTel = orgTel; }

    public String getOrgEmail() { return orgEmail; }
    public void setOrgEmail(String orgEmail) { this.orgEmail = orgEmail; }

    public String getOrgAddress() { return orgAddress; }
    public void setOrgAddress(String orgAddress) { this.orgAddress = orgAddress; }
}
