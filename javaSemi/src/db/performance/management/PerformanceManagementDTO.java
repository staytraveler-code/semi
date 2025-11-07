package db.performance.management;

// 성과관리 테이블
public class PerformanceManagementDTO {

	private String perfCode; //performance code
	private String pCode; //project code
	private String name;
	private String category; 
	private String content;
	private String pDate; //p_date 성과발생일자
	private String memo;
	
	public String getPerfCode() {
		return perfCode;
	}
	public void setPerfCode(String perfCode) {
		this.perfCode = perfCode;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
	
}
