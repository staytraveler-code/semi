package db.project;

import java.sql.Date;

public class ProjectDTO {
    private String projectCode;
    private String orgCode;
    private String title;
    private String stage;
    private String status;
    private long budget;
    private Date startDate;
    private Date endDate;

    public String getProjectCode() { return projectCode; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

    public String getOrgCode() { return orgCode; }
    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getBudget() { return budget; }
    public void setBudget(long budget) { this.budget = budget; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
}
