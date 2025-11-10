package db.researcher;

public class ResearcherDTO {
	
	private String ResearcherCode;
	private String OrgCode;
	private String name;
	private String tel;
	private String email;
	
	
	
	
	public String getResearcherCode() {
		return ResearcherCode;
	}
	public void setResearcherCode(String researcherCode) {
		ResearcherCode = researcherCode;
	}
	public String getOrgCode() {
		return OrgCode;
	}
	public void setOrgCode(String orgCode) {
		OrgCode = orgCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
