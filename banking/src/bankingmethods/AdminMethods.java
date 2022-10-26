package bankingmethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bankingpojo.AccountPojo;
import bankingpojo.CustomerPojo;
import bankingpojo.UserPojo;
import bankingpojo.WithdrawRequestPojo;
import exceptionhandling.CustomException;

public class AdminMethods extends UserMethods {
	public UserPojo getAdminDetails(int adminId) throws  CustomException {
		String query = "select * from user_details where user_id = "+adminId; 
		UserPojo pojoHelper = new UserPojo();
		try {	
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query);
							ResultSet rSet = state.executeQuery()){
					while(rSet.next()) {
						pojoHelper.setUserId(rSet.getInt(1));
						pojoHelper.setName(rSet.getString(3));
						pojoHelper.setEmail(rSet.getString(4));
						pojoHelper.setMobileNo(rSet.getLong(5));
						pojoHelper.setRole(rSet.getString(6));
					}
						}
			}
		catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}return pojoHelper;
	}
	public Map<Integer,WithdrawRequestPojo>  getRequests() throws CustomException {
		Map<Integer,WithdrawRequestPojo> map = new HashMap<>();
String request = "select * from WITHDRAW_REQUEST where status = 'PENDING'";
try {	
	try (Connection con = getDbConnection();
			PreparedStatement state = con.prepareStatement(request);
			ResultSet rSet = state.executeQuery()){
		while(rSet.next()) {
			WithdrawRequestPojo pojoObj = new WithdrawRequestPojo();
			pojoObj.setUserId(rSet.getInt(1));
			int rNumber = rSet.getInt(2);
			pojoObj.setReqNumber(rNumber);
			pojoObj.setAccountNumber(rSet.getLong(3));
			pojoObj.setAmount(rSet.getDouble(4));
		    pojoObj.setStatus(rSet.getString(5));
		    pojoObj.setTime(rSet.getLong(6));
		    map.put(rNumber, pojoObj);
	}
}
}catch(SQLException e){
	throw new CustomException("SQl Exception occurred",e);
}
return map;
	}
	public WithdrawRequestPojo selectRequests(int reqNo) throws CustomException {
		Map<Integer,WithdrawRequestPojo> map = getRequests();
		if(map.get(reqNo)==null) {
			throw new CustomException("INVALID REQUEST NUMBER");
		}else {
			WithdrawRequestPojo helper = map.get(reqNo);
			return helper;
		}
		}
	public void manipulateRequests(int reqNo) throws CustomException {
		WithdrawRequestPojo helper=selectRequests(reqNo);
		int userId = helper.getUserId();
		long accountNumber = helper.getAccountNumber();
		double amount = helper.getAmount();
		userWithdraw(userId,accountNumber,amount);
		updateStatusApproved(reqNo);
	}
	public void updateStatusApproved(int reqNo) throws CustomException {
		long updateTime = System.currentTimeMillis();
		String update = "UPDATE WITHDRAW_REQUEST SET STATUS = 'APPROVED',UPDATE_TIME = "+updateTime+" WHERE REQ_NUMBER = "+reqNo;
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(update)){
			state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		}
	public void updateStatusRejected(int reqNo) throws CustomException {
		long updateTime = System.currentTimeMillis();
		String update = "UPDATE WITHDRAW_REQUEST SET STATUS = 'REJECTED',UPDATE_TIME = "+updateTime+" WHERE REQ_NUMBER = "+reqNo;
		WithdrawRequestPojo helper=selectRequests(reqNo);
		int userId = helper.getUserId();
		long accountNumber = helper.getAccountNumber();
		double amount = helper.getAmount();
		userWithdrawFailed(userId,accountNumber,amount);
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(update)){
			state.execute();
			
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		
		}
	public void createCustomer(CustomerPojo helper,AccountPojo accountObj,double amount) throws CustomException {
		String query ="INSERT INTO USER_DETAILS (PASSWORD,NAME,EMAIL,MOBILE,ROLE) VALUES(?,?,?,?,?)";
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)){
			state.setString(1,helper.getPassword());
			state.setString(2, helper.getName());
			state.setString(3, helper.getEmail());
			state.setLong(4, helper.getMobileNo());
			state.setString(5, "CUSTOMER");
			state.execute();
			try(ResultSet rSet = state.getGeneratedKeys()){
				while(rSet.next()) {
				int userId =rSet.getInt(1);
				addDocsCustomer(userId, helper);
				addAccount(userId, accountObj,amount);
				}
			}
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public void addDocsCustomer(int userId,CustomerPojo helper) throws CustomException {
		String query ="insert into customer_details (CUSTOMER_ID,STATUS,AADHAR_ID,PAN_NUMBER) VALUES(?,?,?,?)";
		try{
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query)){
				state.setInt(1,userId);
				state.setString(2, "ACTIVE");
				state.setLong(3, helper.getAadharId());
				state.setString(4, helper.getPanNumber());
				state.execute();
				
	}
	}catch(SQLException e){
		throw new CustomException("SQl Exception occurred",e);
	}
}
	public void addAccount(int userId,AccountPojo accountObj,double amount) throws CustomException {
		String query ="insert into account_details (CUSTOMER_ID,ACCOUNT_TYPE,BRANCH,STATUS,BALANCE) VALUES(?,?,?,?,?)";
		try{
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)){
				state.setInt(1,userId);
				state.setString(2, accountObj.getAccountType());
				state.setString(3,accountObj.getAccountBranch());
				state.setString(4, "ACTIVE");
				state.setDouble(5, amount);
								state.execute();
								try(ResultSet rSet = state.getGeneratedKeys()){
									if(rSet.next()) {
								long acNo = rSet.getLong(1);
									userDeposit(userId,acNo,amount);
									}
								}
	}
	}catch(SQLException e){
		throw new CustomException("SQl Exception occurred",e);
	}
}
}

