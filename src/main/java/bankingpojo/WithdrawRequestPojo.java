package bankingpojo;

public class WithdrawRequestPojo {
	private int userId;
	int reqNumber;
	long accountNumber;
	double amount;
	String status;
	long reqTime;
	long updateTime;
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUdateTime(long appprovalTime) {
		this.updateTime = appprovalTime;
	}
	public int getReqNumber() {
		return reqNumber;
	}
	public void setReqNumber(int reqNumber) {
		this.reqNumber = reqNumber;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTime() {
		return reqTime;
	}
	public void setTime(long time) {
		this.reqTime = time;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}