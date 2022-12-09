package persistence;
import java.util.Map;

import bankingpojo.AccountPojo;
import bankingpojo.CustomerPojo;
import bankingpojo.TransactionPojo;
import bankingpojo.UserPojo;
import bankingpojo.WithdrawRequestPojo;
import exceptionhandling.CustomException;

public interface PersistenceLayer {
	String userLogin(int userId,String check) throws CustomException ;
	Map<Integer, CustomerPojo> getUserDetails(Integer... userId) throws  CustomException;
	Map<Integer, Map<Long, AccountPojo>> getAccountDetails(Integer...  userId) throws CustomException;
	//Map<Integer,Map<Long,AccountPojo>> getAccountDetails() throws CustomException;
//	Map<Integer,Map<Long,AccountPojo>> getAccountDetails(Integer... userId) throws CustomException;

	void insertTransaction(TransactionPojo pojoHelper) throws CustomException ;
	void insertWithdrawRequest(WithdrawRequestPojo pojoHelper) throws CustomException;
	Map<Long,AccountPojo> checkAccount(int userId,long accountNo) throws CustomException;
	void userDeposit(int userId,long accountNo,double amount) throws CustomException;
	void userWithdraw(int userId,long accountNo,double amount) throws CustomException ;
	
}