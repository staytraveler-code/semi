package db.organization;

/*
  연구기관
 */
public class OrganizationDTO {

	
	private String orgName;
	private String orgType; //기관유형
	private String BizRegNo;
	private String orgTel;
	private String orgEmail;
	private String orgAddress;
	private String orgId;
	private String orgPwd;
	
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
		return BizRegNo;
	}
	public void setBizRegNo(String bizRegNo) {
		BizRegNo = bizRegNo;
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
	public void setOrgEmail(String orgEamil) {
		this.orgEmail = orgEamil;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
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
	

}
