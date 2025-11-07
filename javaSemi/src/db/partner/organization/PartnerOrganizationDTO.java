package db.partner.organization;

/*
   파트너 협약 기관
 */
public class PartnerOrganizationDTO {

    // 기관 이름
    private String partnerOrgName;

    // 사업자 등록 번호
    private String bizRegNo;

    // 기관 전화번호
    private String partnerOrgTel;

    // 기관 이메일
    private String partnerOrgEmail;

    // 기관 주소
    private String partnerOrgAddress;

    // 담당자 이름
    private String managerName;

    // 담당자 연락처
    private String managerTel;

    // 은행명
    private String bankName;

    // 계좌번호
    private String accountNo;

    // 예금주
    private String accountHolder;



    public String getPartnerOrgName() {
        return partnerOrgName;
    }

    public void setPartnerOrgName(String partnerOrgName) {
        this.partnerOrgName = partnerOrgName;
    }

    public String getBizRegNo() {
        return bizRegNo;
    }

    public void setBizRegNo(String bizRegNo) {
        this.bizRegNo = bizRegNo;
    }

    public String getPartnerOrgTel() {
        return partnerOrgTel;
    }

    public void setPartnerOrgTel(String partnerOrgTel) {
        this.partnerOrgTel = partnerOrgTel;
    }

    public String getPartnerOrgEmail() {
        return partnerOrgEmail;
    }

    public void setPartnerOrgEmail(String partnerOrgEmail) {
        this.partnerOrgEmail = partnerOrgEmail;
    }

    public String getPartnerOrgAddress() {
        return partnerOrgAddress;
    }

    public void setPartnerOrgAddress(String partnerOrgAddress) {
        this.partnerOrgAddress = partnerOrgAddress;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}

