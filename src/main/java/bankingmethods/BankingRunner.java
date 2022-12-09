//package bankingmethods;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Scanner;
//import java.util.logging.Logger;
//
//import bankingpojo.AccountPojo;
//import bankingpojo.CustomerPojo;
//import bankingpojo.UserPojo;
//import bankingpojo.WithdrawRequestPojo;
//
//
//public class BankingRunner {
//	static Scanner scan = new Scanner(System.in);
//	static Logger logger = Logger.getLogger(BankingRunner.class.getName());
//
//
//	public static void main(String[] args) {
//		String carryOn = "";
//		UserMethods userHelper = new UserMethods();
//		AdminMethods adminHelper = new AdminMethods();
//		try {
//			logger.info("-------------WELCOME TO ZOHO BANK OF INDIA-----------------");	
//			logger.info("Enter the user id :");
//			int userId = scan.nextInt();
//			logger.info("Enter the password :");
//			String password = scan.next();
//			String authenticate = userHelper.userLogin(userId,password);
//			String role= "CUSTOMER";
//			if(authenticate.equals(role)) {
//				
//				CustomerPojo customerpojo = userHelper.getUserDetails(userId).get(userId);
//				logger.info("USER_ID :"+customerpojo.getUserId() );
//				logger.info("NAME :"+customerpojo.getName());
//				logger.info("EMAIL :"+customerpojo.getEmail());
//				logger.info("MOBILE NUMBER :"+customerpojo.getMobileNo());
//				logger.info("AADHAR ID :"+customerpojo.getAadharId());
//
//
//				do {
//					logger.info("\n1)SHOW ACCOUNTS DETAILS \n"
//							+ "2)BALANCE \n"
//							+ "3)DEPOSIT \n"
//							+ "4)WITHDRAW \n"
//							+ "5)TRANSFER \n"
//							+ "6)MODIFY PERSONAL DETAILS");
//					logger.info("ENTER THE OPTION NO. YOU WANT TO PROCEED WITH :: ");
//					int option = scan.nextInt();
//					switch(option) {
//					case 1:{
//						Map<Integer,Map<Long,AccountPojo>> map =userHelper.getAccountDetails(userId);
//						Map<Long,AccountPojo> map1 = map.get(userId);
//						List<Long> list =userHelper.getUserAccounts(userId);
//						int listLength = list.size();
//						for(int i = 0;i<listLength;i++) {
//							int que = i+1;
//							System.out.println("OPTION "+que+") "+list.get(i));
//						}
//						logger.info("ENTER THE OPTION TO GET DETAILS");
//						int entry  = scan.nextInt();
//						if(entry<listLength+1) {
//							long acNo = list.get(entry-1);
//							AccountPojo acPojoHelper = map1.get(acNo);
//							logger.info("ACCOUNT NO :"+acPojoHelper.getAccountNumber());
//							logger.info("ACCOUNT TYPE :"+acPojoHelper.getAccountType());
//							logger.info("ACCOUNT BRANCH :"+acPojoHelper.getAccountBranch());
//							logger.info("ACCOUNT STATUS :"+acPojoHelper.getAccountStatus());
//							logger.info("ACCOUNT BALANCE :"+acPojoHelper.getAccountBalance());
//						}else {
//							logger.info("ENTER VALID OPTION");
//						}
//
//						break;
//					}
//					case 2:{
//						List<Long> list =userHelper.getUserAccounts(userId);					
//						int listLength = list.size();
//						for(int i = 0;i<listLength;i++) {
//							int que = i+1;
//							System.out.println("OPTION "+que+") "+list.get(i));
//						}
//						logger.info("ENTER THE ACCOUNT OPTION TO GET BALANCE");
//						int entry  = scan.nextInt();
//						if(entry<listLength+1) {
//							long acNo = list.get(entry-1);
//							double balance = userHelper.getBalance(userId,acNo);
//							logger.info("THE BALANCE IS :"+balance);
//						}else {
//							logger.info("ENTER VALID OPTION");
//
//						}break;
//					}
//					case 3:{
//						List<Long> list =userHelper.getUserAccounts(userId);					
//						int listLength = list.size();
//						for(int i = 0;i<listLength;i++) {
//							int que = i+1;
//							System.out.println("OPTION "+que+") "+list.get(i));
//						}
//						logger.info("ENTER THE ACCOUNT OPTION TO DEPOSIT");
//						int entry  = scan.nextInt();
//						if(entry<listLength+1) {
//							long acNo = list.get(entry-1);
//							logger.info("Enter the amount for deposit");
//							double money = scan.nextDouble();						
//							userHelper.userDeposit(userId,acNo,money);
//							logger.info("Deposit successful");
//						}else {
//							logger.info("ENTER VALID OPTION");
//
//						}break;
//					}
//					case 4:{
//						List<Long> list =userHelper.getUserAccounts(userId);					
//						int listLength = list.size();
//						for(int i = 0;i<listLength;i++) {
//							int que = i+1;
//							System.out.println("OPTION "+que+") "+list.get(i));
//						}
//						logger.info("ENTER THE ACCOUNT OPTION TO WITHDRAW");
//						int entry  = scan.nextInt();
//						if(entry<listLength+1) {
//							long acNo = list.get(entry-1);
//							logger.info("Enter the amount for withdraw");
//							double money = scan.nextDouble();
//							userHelper.userWithdrawRequest(userId, acNo,money);
//							logger.info("withdraw request submitted");
//						}else {
//							logger.info("ENTER VALID OPTION");
//
//						}break;
//					}
//					case 5:{
//						List<Long> list =userHelper.getUserAccounts(userId);					
//						int listLength = list.size();
//						for(int i = 0;i<listLength;i++) {
//							int que = i+1;
//							System.out.println("OPTION "+que+") "+list.get(i));
//						}
//						logger.info("ENTER THE ACCOUNT OPTION TO TRANSFER");
//						int entry  = scan.nextInt();
//						if(entry<listLength+1) {
//							long acNo = list.get(entry-1);
//							logger.info("Enter the sender's account number");
//							long toAcNo= scan.nextLong();
//							logger.info("Enter the amount to transfer");
//							double amount= scan.nextLong();
//							userHelper.moneytransfer(userId, acNo, toAcNo, amount);
//						}else {
//							logger.info("ENTER VALID OPTION");
//
//						}break;
//					}
////					case 6:{
////						logger.info(" SELECT ONE DETAIL TO MODIFY \n"
////								+ "1)MOBILE \n"
////								+ "2)EMAIL\n"
////								+ "3)PASSWORD");
////						int modOption = scan.nextInt();		
////						if(modOption==1) {
////							String column = "MOBILE";
////							logger.info("ENTER NEW MOBILE NUMBER");
////							long mobile=scan.nextLong();
////							userHelper.modifyUserDetails(userId,column,mobile,customerpojo.getPassword(),customerpojo.getEmail());
////						}else if(modOption ==2) {
////							String column = "EMAIL";
////							logger.info("ENTER NEW EMAIL");
////							String mail = scan.next();
////							userHelper.modifyUserDetails(userId,column,customerpojo.getMobileNo(),customerpojo.getPassword(),mail);
////						}else if(modOption==3) {
////							String column = "PASSWORD";
////							logger.info("ENTER OLD PASSWORD"); 
////							String oPass = scan.next();
////							logger.info("ENTER NEW PASSWORD"); 
////							String pass = scan.next();
////							if(oPass.equals(customerpojo.getPassword())) {
////								userHelper.modifyUserDetails(userId,column,customerpojo.getMobileNo(),pass,customerpojo.getEmail());
////							}else {
////								System.out.println("OLD PASSWORD IS  WRONG");
////							}
////						}else {
////							System.out.println("ENTER PROPER OPTION");
////						}
////						break;
////					}
//					}
//					logger.info("----------DO YOU WANT TO CONTINUE WITH OTHER OPTIONS(yes/no)-----------");
//					carryOn=scan.next();
//
//				}while(carryOn.equalsIgnoreCase("YES"));
//
//
//
//			}else {
//
//				UserPojo userPojo = adminHelper.getAdminDetails(userId);
//				logger.info("WELCOME ADMIN :"+userPojo.getName());
//				logger.info("USER_ID :"+userPojo.getUserId() );
//				logger.info("NAME :"+userPojo.getName());
//				logger.info("EMAIL :"+userPojo.getEmail());
//				logger.info("MOBILE NUMBER :"+userPojo.getMobileNo());
//				logger.info("ROLE :"+userPojo.getRole());
//
//				do {
//					logger.info("\n1)SHOW REQUESTS \n"
//							+ "2)ADD NEW CUSTOMER \n"
//							+ "3)DEPOSIT \n"
//							+ "4)WITHDRAW");
//					logger.info("ENTER THE OPTION NO. YOU WANT TO PROCEED WITH :: ");
//					int option = scan.nextInt();
//					switch(option) {
//					case 1:{
//						Map<Integer,WithdrawRequestPojo> map = adminHelper.getRequests();
//						for (Entry<Integer,WithdrawRequestPojo> set:map.entrySet()) {
//							WithdrawRequestPojo reqPojoHelper = set.getValue();
//							logger.info("UserID: "+reqPojoHelper.getUserId() +"ReqNo: "+reqPojoHelper.getReqNumber()+" Account No:"+reqPojoHelper.getAccountNumber()
//							+" Amount :"+reqPojoHelper.getAmount()+" Request time: "+reqPojoHelper.getTime());
//						}
//						logger.info("Enter the request number for manipulation");
//						int reqNo = scan.nextInt();
//						logger.info("\nPress 1 for Approve \nPress 2 for Deny");
//						int manipulate = scan.nextInt();
//						if(manipulate==1) {
//							adminHelper.manipulateRequests(reqNo);
//							logger.info("Request Approved");
//						}else if(manipulate==2) {
//							adminHelper.updateStatusRejected(reqNo);
//							logger.info("Request denied");
//						}else {
//							logger.info("Enter proper command");
//						}
//						break;
//
//					}
//					case 2:{
//						logger.info("ENTER THE NAME OF THE NEW CUSTOMER");
//						String name = scan.next();
//						logger.info("ENTER EMAIL");
//						String email = scan.next();
//						logger.info("ENTER MOBILE NUMBER");
//						long mobile = scan.nextLong();
//						logger.info("ENTER THE AADHAR ID OF THE CUSTOMER");
//						long aadharId = scan.nextLong();
//						logger.info("ENTER THE PAN NUMBER OF THE CUSTOMER");
//						String pan = scan.next();
//						logger.info("TYPE OF ACCOUNT");
//						String type = scan.next();
//						logger.info("BRANCH OF ACCOUNT");
//						String branch = scan.next();
//						logger.info("INITIAL BALANCE DEPOSIT");
//						double balance = scan.nextDouble();
//						logger.info("PASSWORD FOR THE NEW CUSTOMER");
//						String newPass = scan.next();
//						CustomerPojo helper = new CustomerPojo();
//						helper.setPassword(newPass);
//						helper.setName(name);
//						helper.setAadharId(aadharId);
//						helper.setEmail(email);
//						helper.setPanNumber(pan);
//						helper.setMobileNo(mobile);
//						AccountPojo accountPojo = new AccountPojo();
//						accountPojo.setAccountBranch(branch);
//						accountPojo.setAccountType(type);
//						adminHelper.createCustomer(helper,accountPojo,balance);
//					}
//					}
//					logger.info("----------DO YOU WANT TO CONTINUE WITH OTHER OPTIONS(yes/no)-----------");
//					carryOn=scan.next();
//
//				}while(carryOn.equalsIgnoreCase("YES"));
//			}
//
//
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//
//	}
//}