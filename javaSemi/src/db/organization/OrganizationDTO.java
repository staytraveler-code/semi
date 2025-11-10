package db.organization;

/** 연구기관 정보 DTO */
public class OrganizationDTO {
    private String orgId;      // id
    private String orgPwd;     // pwd
    private String orgName;    // name
    private String orgType;    // type
    private String bizRegNo;   // biz_reg_no
    private String orgTel;     // tel
    private String orgEmail;   // email
    private String orgAddress; // address

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
