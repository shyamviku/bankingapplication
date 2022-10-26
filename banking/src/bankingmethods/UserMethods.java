package bankingmethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bankingpojo.AccountPojo;
import bankingpojo.CustomerPojo;
import bankingpojo.TransactionPojo;
import bankingpojo.WithdrawRequestPojo;
import exceptionhandling.CustomException;
import persistence.PersistenceLayer;

public class UserMethods implements PersistenceLayer{
	public long getTimeInMilli() {
		long millis = System.currentTimeMillis();
		return millis;
	}
	private void nullCheck (Object given)throws CustomException{ 
		if (Objects.isNull(given)){
			throw new CustomException(" value should not be null ");
		}
	}
	public Connection getDbConnection()throws CustomException{
		final String userId = "root";
		final String userPass= "Root@1234";
		final String url= "jdbc:mysql://localhost/incubationdb";
		try {
			Connection connection = DriverManager.getConnection(url,userId, userPass);				 
			return connection;
		} catch (SQLException e) {
			throw new CustomException("SQL exception has occured",e);
		}	
	}
	public String userLogin(int userId,String check) throws CustomException {
		nullCheck(check);
		String query = "SELECT PASSWORD,ROLE FROM USER_DETAILS WHERE USER_ID = "+userId;
		String password = "";
		String role ="";
		int count;
		try{ 
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query);
					ResultSet rSet = state.executeQuery()){
				ResultSetMetaData data = rSet.getMetaData();
				count = data.getColumnCount();
				while(rSet.next()) {
					password = rSet.getString(1);
					role = rSet.getString(2);
				}
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		Pattern ptr = Pattern.compile(password);
		Matcher mat = ptr.matcher(check);
		boolean checker = mat.matches();
		if(count!= 0&&checker==true) {
			System.out.println("LOGIN SUCCESSFUL");
		}else {
			throw new CustomException("WRONG CREDENTIALS ENTERED");
		}
		return role;
	}
	public Map<Integer,CustomerPojo> getUserDetails(Integer... userId) throws  CustomException {
		CustomerPojo pojoHelper = new CustomerPojo();
		Map<Integer,CustomerPojo> map = new LinkedHashMap<>();
		
		String query = "select user_details.user_id,user_details.name,user_details.email,user_details.mobile,customer_details.status,customer_details.aadhar_id,customer_details.pan_number,user_details.password from user_details join customer_details on user_details.user_id=customer_details.customer_id where customer_id = ?";
		try {	
			try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query)){
						for(Integer user:userId) {
							state.setInt(1, user);
							ResultSet rSet = state.executeQuery();
				ResultSetMetaData data = rSet.getMetaData();
				if(data.getColumnCount()==0) {
					throw new CustomException("INVALID USER ID ENTERED");
				}else {
					while(rSet.next()) {
						pojoHelper.setUserId(rSet.getInt(1));
						pojoHelper.setName(rSet.getString(2));
						pojoHelper.setEmail(rSet.getString(3));
						pojoHelper.setMobileNo(rSet.getLong(4));
						pojoHelper.setStatus(rSet.getString(5));
						pojoHelper.setAadharId(rSet.getLong(6));
						pojoHelper.setPanNumber(rSet.getString(7));
						pojoHelper.setPassword(rSet.getString(8));
						map.put(rSet.getInt(1), pojoHelper);
					}	
				}
						}
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}return map;
	}
	public List<Long> getUserAccounts(int userId) throws CustomException{
		List<Long> list = new ArrayList<Long>();
		String query = " select account_number from account_details where customer_id ="+userId+" AND status='ACTIVE'";
		try{ 
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query);
					ResultSet rSet = state.executeQuery()){
				while(rSet.next()) {
					long acNo = rSet.getLong(1);
					list.add(acNo);
				}
			}	
			}catch(SQLException e){
				throw new CustomException("SQl Exception occurred",e);
			}return list;
	}
	public Map<Integer,Map<Long,AccountPojo>> getAccountDetails(Integer... userId) throws CustomException {
		Map<Integer,Map<Long,AccountPojo>> map= new LinkedHashMap<>(); 
		Map<Long,AccountPojo> map1 = new HashMap<>();
		String query = "SELECT * FROM ACCOUNT_DETAILS WHERE CUSTOMER_ID = ?";
		try{
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(query)){
						for(Integer user:userId) {
							state.setInt(1, user);
					ResultSet rSet = state.executeQuery();
				while(rSet.next()) { 
					AccountPojo pojoHelper = new AccountPojo();
					int customerId = rSet.getInt(1);
					Long acNo = rSet.getLong(2);
					pojoHelper.setAccountNumber(acNo);
					pojoHelper.setAccountType(rSet.getString(3));
					pojoHelper.setAccountBranch(rSet.getString(4));
					pojoHelper.setAccountStatus(rSet.getString(5));
					pojoHelper.setAccountBalance(rSet.getDouble(6));
					map1.put(acNo, pojoHelper);
					map.put(customerId, map1);
				}
			}
			}	
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
		return map;
	}
	public TransactionPojo insertIntoPojo (int userId,long acNo,long fomAcNo,String type,double amount,String status) {
		TransactionPojo pojoHelper = new TransactionPojo();
		pojoHelper.setUserId(userId);
		pojoHelper.setAccountNumber(acNo);
		pojoHelper.setTypeOfTransaction(type);
		pojoHelper.setFromAccount(fomAcNo);
		pojoHelper.setAmount(amount);
		pojoHelper.setTransactionStatus(status);
		long time = getTimeInMilli();
		pojoHelper.setTimeOfTranstraction(time);
		return pojoHelper;
	}
	public void insertTransaction(TransactionPojo pojoHelper) throws CustomException {
		String insert = "INSERT INTO TRANSACTION_DETAILS "
				+ "(USER_ID,ACCOUNT_NUMBER,TYPE,FROM_ACCOUNT,AMOUNT,STATUS,TIME) "
				+ "VALUES(?,?,?,?,?,?,?)";
		try {
			try  (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(insert)){
				state.setInt(1, pojoHelper.getUserId());
				state.setLong(2, pojoHelper.getAccountNumber());
				state.setString(3, pojoHelper.getTypeOfTransaction());
				state.setLong(4, pojoHelper.getFromAccount());
				state.setDouble(5, pojoHelper.getAmount());
				state.setString(6, pojoHelper.getTransactionStatus());
				state.setLong(7,pojoHelper.getTimeOfTranstraction());
				state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public WithdrawRequestPojo insertIntoPojoReq (int userId,long acNo,double amount) {
		WithdrawRequestPojo pojoHelper = new WithdrawRequestPojo();
		pojoHelper.setUserId(userId);
		pojoHelper.setAccountNumber(acNo);
		pojoHelper.setAmount(amount);
		pojoHelper.setStatus("PENDING");
		long time = getTimeInMilli();
		pojoHelper.setTime(time);
		return pojoHelper;
	}
	public void insertWithdrawRequest(WithdrawRequestPojo pojoHelper) throws CustomException {
		String insert = "INSERT INTO  WITHDRAW_REQUEST"
				+ "(USER_ID,ACCOUNT_NUMBER,AMOUNT,STATUS,REQ_TIME) "
				+ "VALUES(?,?,?,?,?)";
		try {
			try  (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(insert)){
				state.setInt(1, pojoHelper.getUserId());
				state.setLong(2, pojoHelper.getAccountNumber());
				state.setDouble(3, pojoHelper.getAmount());
				state.setString(4, pojoHelper.getStatus());
				long time = getTimeInMilli();
				state.setLong(5,time);
			//	state.setLong(6, pojoHelper.getUpdateTime());
				state.execute();
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public Map<Long,AccountPojo> checkAccount(int userId,long accountNo) throws CustomException {
		Map<Integer,Map<Long,AccountPojo>> map=getAccountDetails(userId);
		Map<Long,AccountPojo> map1 = map.get(userId);
		if(map1.get(accountNo)==null) {
			throw new CustomException("WRONG ACCOUNT NUMBER ENTERED");
		}else {
			return map1;
		}
	}
	public void userDeposit(int userId,long accountNo,double amount) throws CustomException {
		String deposit = "update account_details set balance = balance+"+amount+" where account_number = "+accountNo;
		checkAccount(userId,accountNo);

		try{
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(deposit)){
				state.execute();				
				TransactionPojo pojoHelper=insertIntoPojo(userId,accountNo,accountNo, "CREDIT", amount, "SUCCESSFUL");
				insertTransaction(pojoHelper);
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}	
	}
	public double getBalance (int userId,long accountNo) throws CustomException {		
		Map<Long,AccountPojo> map1 = checkAccount(userId,accountNo);
		AccountPojo pojoHelper = map1.get(accountNo); 
		double balance = pojoHelper.getAccountBalance();
		return balance;
	}
	public void userWithdrawFailed(int userId,long accountNo,double amount) throws CustomException {
		TransactionPojo pojoHelper = insertIntoPojo(userId,accountNo,accountNo, "DEBIT", amount, "FAILED");
		insertTransaction(pojoHelper);
	}
	public void userWithdraw(int userId,long accountNo,double amount) throws CustomException {
		String withdraw = "update account_details set balance = balance-"+amount+" where account_number = "+accountNo;
		try{
			try (Connection con = getDbConnection();
					PreparedStatement state = con.prepareStatement(withdraw)){
				state.execute();
				TransactionPojo pojoHelper=insertIntoPojo(userId,accountNo,accountNo, "DEBIT", amount, "SUCCESSFUL");
				insertTransaction(pojoHelper);
			}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}
	}
	public void userWithdrawRequest(int userId,long accountNo,double amount) throws CustomException {
		double checkBalance =getBalance(userId,accountNo); 
		if (checkBalance<amount) {
			throw new CustomException("Cash not available try lesser amount");
		}else {
			WithdrawRequestPojo pojoHelper =insertIntoPojoReq(userId,accountNo,amount);
			insertWithdrawRequest(pojoHelper);
		}
	}
	public Map<Long,AccountPojo> checkToAccount(long accountNo) throws CustomException{
		String query =  "select account_number,customer_id from account_details where account_number= "+accountNo+" AND status='ACTIVE'";
		Map<Long,AccountPojo> map = new LinkedHashMap<>();
		try{try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(query);
				ResultSet rSet = state.executeQuery()){
			while(rSet.next()) { 
				AccountPojo pojoHelper = new AccountPojo();
				pojoHelper.setAccountNumber(rSet.getLong(1));
				pojoHelper.setUserId(rSet.getInt(2));
				map.put(accountNo, pojoHelper);
			}
			return map;
		}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
		}	
	}
	public void moneytransfer(int fromUserId,long fromAccount,long toAccount,double amount) throws CustomException {
		Map<Long,AccountPojo> map =checkToAccount(toAccount);
		if(map.get(toAccount)==null) {
			throw new CustomException("WRONG RECIEVER'S ACCOUNT NUMBER ENTERED OR IS INACTIVE");
		}else {
			AccountPojo AccountpojoHelper =map.get(toAccount);
			int toUserId =AccountpojoHelper.getUserId();
			userWithdraw(fromUserId,fromAccount,amount);
			String deposit = "update account_details set balance = balance+"+amount+" where account_number = "+toAccount;
			try{
				try (Connection con = getDbConnection();
						PreparedStatement state = con.prepareStatement(deposit)){
					state.execute();				
					TransactionPojo pojoHelper = insertIntoPojo(toUserId,toAccount,fromAccount, "CREDIT", amount, "SUCCESSFUL");
					insertTransaction(pojoHelper);

				}
			}catch(SQLException e){
				throw new CustomException("SQl Exception occurred",e);
			}	
		}
	}
	public void mobileValidation(long mobile)throws CustomException {
		String regex = "^\\d{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(""+mobile);
		boolean check = match.matches();
		if(check==false) {
			throw new CustomException("ENTER A VALID MOBILE NUMBER");
		}
	}
	public void modifyUserDetails(int userId,String column,long mobile,String password,String email)throws CustomException {
		String modify = "UPDATE USER_DETAILS SET "+column+" = ? WHERE USER_ID = "+userId;
		try{try (Connection con = getDbConnection();
				PreparedStatement state = con.prepareStatement(modify)){
			if(column == "MOBILE") {
				mobileValidation(mobile);
				state.setLong(1, mobile);
				state.executeUpdate();
			}else if(column=="EMAIL") {
				state.setString(1, email);
				state.executeUpdate();
			}else if(column=="PASSWORD") {	
				state.setString(1, password);
				state.executeUpdate();
			}
		}
		}catch(SQLException e){
			throw new CustomException("SQl Exception occurred",e);
	}
	}
}

		
	

