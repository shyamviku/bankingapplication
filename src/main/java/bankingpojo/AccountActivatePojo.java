package bankingpojo;

public class AccountActivatePojo {
int userId;
int reqNo;
long acNo;
String type;
String status;
long reqTime;
long updateTime;
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getReqNo() {
	return reqNo;
}
public void setReqNo(int reqNo) {
	this.reqNo = reqNo;
}
public long getAcNo() {
	return acNo;
}
public void setAcNo(long acNo) {
	this.acNo = acNo;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public long getReqTime() {
	return reqTime;
}
public void setReqTime(long reqTime) {
	this.reqTime = reqTime;
}
public long getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(long updateTime) {
	this.updateTime = updateTime;
}
}
