package db.researcher.role;

public class ResearcherRoleDTO {
	 private String projectCode;     
	    private String researcherCode;  
	    private String role;            
	    private String startDate;         
	    private String endDate;
	    
	    
	    
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
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}           

}
