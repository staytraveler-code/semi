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

    private String ministryName;     
    private String partnerOrgName;   

    private int totalMilestones;      // 전체 마일스톤 수
    private int completedMilestones;  // 완료된 마일스톤 수

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

    public String getMinistryName() { return ministryName; }
    public void setMinistryName(String ministryName) { this.ministryName = ministryName; }

    public String getPartnerOrgName() { return partnerOrgName; }
    public void setPartnerOrgName(String partnerOrgName) { this.partnerOrgName = partnerOrgName; }

    public int getTotalMilestones() { return totalMilestones; }
    public void setTotalMilestones(int totalMilestones) { this.totalMilestones = totalMilestones; }

    public int getCompletedMilestones() { return completedMilestones; }
    public void setCompletedMilestones(int completedMilestones) { this.completedMilestones = completedMilestones; }

    // 편의 메소드: 진행도 비율(0.0 ~ 1.0)
    public double getProgressRatio() {
        if (totalMilestones <= 0) return 0.0;
        return (double) completedMilestones / totalMilestones;
    }
}
