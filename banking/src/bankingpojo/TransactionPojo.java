package bankingpojo;

public class TransactionPojo {
	private int userId;
	private long accountNumber;
	private int transactionId;
	private String typeOfTransaction;
	double amount;
	private long timeOfTranstraction;
	private String transactionStatus;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}
	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}
	public long getTimeOfTranstraction() {
		return timeOfTranstraction;
	}
	public void setTimeOfTranstraction(long timeOfTranstraction) {
		this.timeOfTranstraction = timeOfTranstraction;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
