package db.fund.management;

public class FundManagementDTO {
	private String fcode;
	private String pcode;
	private String rcode;
	private String charger_name;
	private String category;
	private String date_used;
	private long expense;
	private String content;
	private String vendor_name;
	private String proof_type;
	private String memo;

	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getRcode() {
		return rcode;
	}
	public void setRcode(String rcode) {
		this.rcode = rcode;
	}
	public String getCharger_name() {
		return charger_name;
	}
	public void setCharger_name(String charger_name) {
		this.charger_name = charger_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate_used() {
		return date_used;
	}
	public void setDate_used(String date_used) {
		this.date_used = date_used;
	}
	public long getExpense() {
		return expense;
	}
	public void setExpense(long expense) {
		this.expense = expense;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	public String getProof_type() {
		return proof_type;
	}
	public void setProof_type(String proof_type) {
		this.proof_type = proof_type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

}
