package db.milestone;

//마일스톤
public class milestoneDTO {
	private String mileCode; //milestone code
	private String pCode; //project code
	private String name;
	private String desc; // description
	private String peDate; //p_end_date
	private String aeDate; //a_end_date
	private String status; //status
	
	
	public String getmCode() {
		return mileCode;
	}
	public void setmCode(String mCode) {
		this.mileCode = mCode;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPeDate() {
		return peDate;
	}
	public void setPeDate(String peDate) {
		this.peDate = peDate;
	}
	public String getAeDate() {
		return aeDate;
	}
	public void setAeDate(String aeDate) {
		this.aeDate = aeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
