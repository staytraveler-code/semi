package db.researcher;

public class ResearcherDTO {
    private String researcherCode;
    private String orgCode;
    private String name;
    private String tel;
    private String email;

    public String getResearcherCode() { return researcherCode; }
    public void setResearcherCode(String researcherCode) { this.researcherCode = researcherCode; }

    public String getOrgCode() { return orgCode; }
    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
