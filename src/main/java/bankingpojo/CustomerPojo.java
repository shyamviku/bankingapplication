package bankingpojo;

public class CustomerPojo extends UserPojo {
int CustomerId;
String status;
long aadharId;
String panNumber;
public int getCustomerId() {
	return CustomerId;
}
public void setCustomerId(int customerId) {
	CustomerId = customerId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public long getAadharId() {
	return aadharId;
}
public void setAadharId(long aadharId) {
	this.aadharId = aadharId;
}
public String getPanNumber() {
	return panNumber;
}
public void setPanNumber(String panNumber) {
	this.panNumber = panNumber;
}
}