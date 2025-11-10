package db.researcher.role;

import java.util.Date;

public class ResearcherRoleDTO {
	 private String projectCode;     
	    private String researcherCode;  
	    private String role;            
	    private Date startDate;         
	    private Date endDate;
	    
	    
	    
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}
		public String getResearcherCode() {
			return researcherCode;
		}
		public void setResearcherCode(String researcherCode) {
			this.researcherCode = researcherCode;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}           

}
