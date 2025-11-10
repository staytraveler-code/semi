package db.organization;

public class OrganizationDTO {
    private String orgCode;     // ORG_001 형태 자동생성
    private String orgId;       // 로그인용 ID
    private String orgPwd;      // 비밀번호
    private String orgName;     // 기관명
    private String orgType;     // 기관 유형(대학/기업 등)
    private String bizRegNo;    // 사업자등록번호
    private String orgTel;      // 전화번호
    private String orgEmail;    // 이메일
    private String orgAddress;  // 주소

    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgPwd() {
        return orgPwd;
    }
    public void setOrgPwd(String orgPwd) {
        this.orgPwd = orgPwd;
    }

    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getBizRegNo() {
        return bizRegNo;
    }
    public void setBizRegNo(String bizRegNo) {
        this.bizRegNo = bizRegNo;
    }

    public String getOrgTel() {
        return orgTel;
    }
    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgEmail() {
        return orgEmail;
    }
    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgAddress() {
        return orgAddress;
    }
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }
}
