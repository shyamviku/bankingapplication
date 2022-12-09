package bankingmethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bankingpojo.AccountActivatePojo;
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
//	public Map<Integer,CustomerPojo> getUserDetails(int userId) throws  CustomException {
//		CustomerPojo pojoHelper = new CustomerPojo();
//		Map<Integer,CustomerPojo> map = new LinkedHashMap<>();
//		
//		String query = "select user_details.user_id,user_details.name,user_details.email,user_details.mobile,customer_details.status,customer_details.aadhar_id,customer_details.pan_number,user_details.password from user_details join customer_details on user_details.user_id=customer_details.customer_id where customer_id = "+userId;
//		try {	
//			try (Connection con = getDbConnection();
//				PreparedStatement state = con.prepareStatement(query)){
//							ResultSet rSet = state.executeQuery();
//				ResultSetMetaData data = rSet.getMetaData();
//				if(data.getColumnCount()==0) {
//					throw new CustomException("INVALID USER ID ENTERED");
//				}else {
//					while(rSet.next()) {
//						pojoHelper.setUserId(rSet.getInt(1));
//						pojoHelper.setName(rSet.getString(2));
//						pojoHelper.setEmail(rSet.getString(3));
//						pojoHelper.setMobileNo(rSet.getLong(4));
//						pojoHelper.setStatus(rSet.getString(5));
//						pojoHelper.setAadharId(rSet.getLong(6));
//						pojoHelper.setPanNumber(rSet.getString(7));
//						pojoHelper.setPassword(rSet.getString(8));
//						map.put(rSet.getInt(1), pojoHelper);
//					}	
//				
//						}
//			}
//		}catch(SQLException e){
//			throw new CustomException("SQl Exception occurred",e);
//		}return map;
//	}
	public Map<Long,AccountPojo> getSpecificAccount(long acNo) throws CustomException{
		String query="SELECT * FROM ACCOUNT_DETAILS WHERE ACCOUNT_NUMBER ="+acNo;
		Map<Long,AccountPojo> map= new HashMap<>();
		AccountPojo pojoHelper = new AccountPojo();
		try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query)){
				ResultSet rSet = state.executeQuery();
			while(rSet.next()) { 
				pojoHelper.setUserId( rSet.getInt(1));
				pojoHelper.setAccountNumber(rSet.getLong(2));
				pojoHelper.setAccountType(rSet.getString(3));
				pojoHelper.setAccountBranch(rSet.getString(4));
				pojoHelper.setAccountStatus(rSet.getString(5));
				pojoHelper.setAccountBalance(rSet.getDouble(6));
				map.put(acNo, pojoHelper);
			}if(map.get(acNo)==null) {
				throw new CustomException("ACCOUNT NUMBER NOT AVAILABLE");
			}
			}catch(SQLException e){
				throw new CustomException("SQl Exception occurred",e);
			}return map;
		}
	public Map<Integer,WithdrawRequestPojo>  getWithdrawRequests() throws CustomException {
		Map<Integer,WithdrawRequestPojo> map = new HashMap<>();
String request = "select * from WITHDRAW_REQUEST where status = 'REQUESTED'";
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
	}if(map.isEmpty()) {
		throw new CustomException("NO PENDING REQUESTS");
	}
}
}catch(SQLException e){
	throw new CustomException("SQl Exception occurred",e);
}
return map;
	}
	
	public Map<Integer,AccountActivatePojo>  getActivationRequests() throws CustomException {
		Map<Integer,AccountActivatePojo> map = new HashMap<>();
String request = "select * from ACCOUNT_ACTIVATE_REQUEST where status = 'REQUESTED'";
try {	
	try (Connection con = getDbConnection();
			PreparedStatement state = con.prepareStatement(request);
			ResultSet rSet = state.executeQuery()){
		while(rSet.next()) {
			AccountActivatePojo pojoObj = new AccountActivatePojo();
			pojoObj.setUserId(rSet.getInt(1));
			int rNumber = rSet.getInt(2);
			pojoObj.setReqNo(rNumber);
			pojoObj.setAcNo(rSet.getLong(3));
			pojoObj.setType(rSet.getString(4));
			pojoObj.setStatus(rSet.getString(5));
		    pojoObj.setReqTime(rSet.getLong(6));
		    map.put(rNumber, pojoObj);
	}if(map.isEmpty()) {
		throw new CustomException("NO PENDING REQUESTS");
	}
}
}catch(SQLException e){
	throw new CustomException("SQl Exception occurred",e);
}
return map;
	}
	public WithdrawRequestPojo selectRequests(int reqNo) throws CustomException {
		Map<Integer,WithdrawRequestPojo> map = getWithdrawRequests();
		if(map.get(reqNo)==null) {
			throw new CustomException("INVALID REQUEST NUMBER");
		}else {
			WithdrawRequestPojo helper = map.get(reqNo);
			return helper;
		}
		}
	public void acceptWithdraw(int reqNo) throws CustomException {
		WithdrawRequestPojo helper=selectRequests(reqNo);
		int userId = helper.getUserId();
		long accountNumber = helper.getAccountNumber();
		double amount = helper.getAmount();
		userWithdraw(userId,accountNumber,amount);
		updateWithdrawApproved(reqNo);
	}
	public void updateWithdrawApproved(int reqNo) throws CustomException {
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
	public void withdrawRejected(int reqNo) throws CustomException {
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
	public AccountActivatePojo selectAccountRequests(int reqNo) throws CustomException {
		Map<Integer,AccountActivatePojo> map = getActivationRequests();
		if(map.get(reqNo)==null) {
			throw new CustomException("INVALID REQUEST NUMBER");
		}else {
			AccountActivatePojo helper = map.get(reqNo);
			return helper;
		}
		}
	public void activateAccount (long acNo) throws CustomException {
		String query = "UPDATE ACCOUNT_DETAILS SET STATUS = 'ACTIVE' WHERE ACCOUNT_NUMBER= "+acNo;
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query)){
			state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public void deactivateAccount (long acNo) throws CustomException {
		String query = "UPDATE ACCOUNT_DETAILS SET STATUS = 'INACTIVE' WHERE ACCOUNT_NUMBER= "+acNo;
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query)){
			state.execute();
			
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public void updateActivationApproved(int reqNo) throws CustomException {
		long updateTime = System.currentTimeMillis();
		String update = "UPDATE ACCOUNT_ACTIVATE_REQUEST SET STATUS = 'APPROVED',UPDATE_TIME = "+updateTime+" WHERE REQ_NUMBER = "+reqNo;
		AccountActivatePojo helper=selectAccountRequests(reqNo);
		long acNo=helper.getAcNo();
		String type = helper.getType();
		if(type.equals("ACTIVATE")) {
		activateAccount (acNo);
		}else if(type.equals("DEACTIVATE")) {
			deactivateAccount(acNo);
		}
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(update)){
			state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		}
	public void updateActivationRejected(int reqNo) throws CustomException {
		long updateTime = System.currentTimeMillis();
		String update = "UPDATE ACCOUNT_ACTIVATE_REQUEST SET STATUS = 'REJECTED',UPDATE_TIME = "+updateTime+" WHERE REQ_NUMBER = "+reqNo;
		try{
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(update)){
			state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		}
	public void aadharValidation(long aadhar)throws CustomException {
		String regex = "^[1-9]\\d{11}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(String.valueOf(aadhar));
		boolean check = match.matches();
		if(check==false) {
			throw new CustomException("ENTER A VALID AADHAR ID");
		}
	}
	public void panValidation(String pan)throws CustomException {
		String regex = "^[A-Z]{5}\\d{4}[A-Z]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(String.valueOf(pan));
		boolean check = match.matches();
		if(check==false) {
			throw new CustomException("ENTER A VALID PAN NUMBER");
		}
	}


	public void createCustomer(CustomerPojo helper,AccountPojo accountObj,double amount) throws CustomException, SQLException {
		String query ="INSERT INTO USER_DETAILS (PASSWORD,NAME,EMAIL,MOBILE,ROLE) VALUES(?,?,?,?,?)";
		Savepoint save = null;
		mobileValidation(helper.getMobileNo());
		emailValidation(helper.getEmail());
		try(Connection con = getDbConnection()){
		try{con.setAutoCommit(false);
			try (	
				PreparedStatement state = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)){	
				state.setString(1,helper.getName()+"@"+helper.getMobileNo());
			state.setString(2, helper.getName());
			state.setString(3, helper.getEmail());
			state.setLong(4, helper.getMobileNo());
			state.setString(5, "CUSTOMER");
			save=con.setSavepoint();
			state.execute();
			try(ResultSet rSet = state.getGeneratedKeys()){
				while(rSet.next()) {
				int userId =rSet.getInt(1);
				aadharValidation(helper.getAadharId());
				panValidation(helper.getPanNumber());
				addDocsCustomer(userId, helper);
				createAccount(userId, accountObj,amount);
				con.commit();
				}
			}
			}
		}catch(SQLException e){
			con.rollback(save);
			throw new CustomException("MOBILE OR EMAIL ALREADY REGISTERED",e);
		}
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
		throw new CustomException("DOCUMENTS EXISTS ALREADY",e);
	}
}
	public void createAccount(int userId,AccountPojo accountObj,double amount) throws CustomException {
		String query ="insert into account_details (CUSTOMER_ID,ACCOUNT_TYPE,BRANCH,STATUS,BALANCE) VALUES(?,?,?,?,?)";
	   getUserDetails(userId);
		try{
			 getUserDetails(userId);
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)){
				state.setInt(1,userId);
				state.setString(2, accountObj.getAccountType());
				state.setString(3,accountObj.getAccountBranch());
				state.setString(4, "ACTIVE");
				state.setDouble(5, 0);
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