

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bankingmethods.AdminMethods;
import bankingmethods.UserMethods;
import bankingpojo.AccountActivatePojo;
import bankingpojo.AccountPojo;
import bankingpojo.CustomerPojo;
import bankingpojo.TransactionPojo;
import bankingpojo.UserPojo;
import bankingpojo.WithdrawRequestPojo;
import exceptionhandling.CustomException;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserMethods userHelper = new UserMethods();
	private AdminMethods adminHelper = new AdminMethods();

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//		// TODO Auto-generated method stub
	//		response.getWriter().append("Served at: ").append(request.getContextPath());
	//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String parameter = request.getParameter("button");
		

		switch(parameter){
		case "login":{
			int userId=Integer.parseInt(request.getParameter("userid"));
			String pass = request.getParameter("password");
			try {
				HttpSession session =request.getSession(true);
				
				String role = userHelper.userLogin(userId,pass);
				if(role.equals("CUSTOMER")) {							
					request.getRequestDispatcher("jsp/customer.jsp").forward(request, response);
					session.setAttribute("customerId", userId);
				}else if(role.equals("ADMIN")){	
					request.getRequestDispatcher("jsp/admin.jsp").forward(request, response);	
					session.setAttribute("customerId", userId);
				}
			} catch (CustomException e) {
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
				e.printStackTrace();
			}
			break;
		}
		case "userDetails":{

			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				CustomerPojo customerPojo = userHelper.getUserDetails(userId).get(userId);
				request.setAttribute("userpojo", customerPojo);
				request.setAttribute("customer", "hide");
				request.getRequestDispatcher("jsp/userDetails.jsp").forward(request, response);
			} catch (CustomException e) {
				e.printStackTrace();
			}

			break;
		}
		case "SAVE CUSTOMER DETAILS":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			Long mobile =Long.parseLong(request.getParameter("mobile"));
			String email=request.getParameter("email");
			try {
				Long mobileOld=Long.parseLong(request.getParameter("mobileold"));
				String emailOld=request.getParameter("emailold");
				
				if(email.equals(emailOld)&&mobile.equals(mobileOld)) {
					request.setAttribute("error","CHANGE DETAILS TO UPDATE");
				}else {
				userHelper.modifyUserDetails(userId, mobile, email);
				request.setAttribute("success", "SAVED DETAILS");
				}
			} catch (CustomException e) {
				request.setAttribute("error",e.getMessage());
				e.printStackTrace();	
			}
			try {
				CustomerPojo customerPojo = userHelper.getUserDetails(userId).get(userId);
				request.setAttribute("userpojo", customerPojo);
				request.setAttribute("customer", "hide");
				request.getRequestDispatcher("jsp/userDetails.jsp").forward(request, response);			} catch (CustomException e1) {
					e1.printStackTrace();
				}
			break;			
		}
		case "myAccount":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			System.out.println(userId);
			try {
				Map<Integer,Map<Long,AccountPojo>> map =userHelper.getAccountDetails(userId);
				request.setAttribute("map", map);
			} catch (CustomException e) {	
				e.printStackTrace();
			}
			request.getRequestDispatcher("jsp/myAccount.jsp").forward(request, response);
			break;
		}
		case "viewtransaction":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			request.setAttribute("userId", userId);
			request.setAttribute("render", "hide");
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error",e.getMessage());
			}
			
			request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			break;
		}
		case"showtransaction":{
			int userId = Integer.parseInt(request.getParameter("userId"));
			try {
				long acNo=Long.parseLong(request.getParameter("select"));
				long time = System.currentTimeMillis()-2592000000L;
				Map<Long,Map<Integer,TransactionPojo>> map=userHelper.showAccountTransaction( time,acNo);
				Map<Integer,TransactionPojo> map1=map.get(acNo);
				request.setAttribute("map", map1);
				request.setAttribute("acNo", acNo);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}try {
				request.setAttribute("userId", userId);
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error",e.getMessage());
			}
			request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			break;
		}
		case "withdrawRequest":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/withdrawRequest.jsp").forward(request, response);
			break;
		}
		case "REQUEST WITHDRAW":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(request.getParameter("select"));
				String table ="withdraw_request";
				userHelper.checkDuplicateRequest(table,acNo);
				double amount = Double.parseDouble(request.getParameter("amount"));
				userHelper.userWithdrawRequest(userId, acNo, amount);
				request.setAttribute("success", "REQUEST SUBMITTED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e1) {	
				request.setAttribute("error", e1.getMessage());
			}
			request.getRequestDispatcher("jsp/withdrawRequest.jsp").forward(request, response);
			break;
		}
		case "deposit":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/deposit.jsp").forward(request, response);
			break;
		}
		case "DEPOSIT":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(request.getParameter("select"));
				double amount = Double.parseDouble(request.getParameter("amount"));
				userHelper.userDeposit(userId, acNo, amount);
				request.setAttribute("success", "SUCCESSFULLY DEPOSITED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/deposit.jsp").forward(request, response);
			break;
		}
		case "transfer":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/transfer.jsp").forward(request, response);
			break;
		}
		case "TRANSFER":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(request.getParameter("select"));
				long toAcNo=Long.parseLong(request.getParameter("toaccount"));
				double amount = Double.parseDouble(request.getParameter("amount"));			
				userHelper.moneyTransfer(userId, acNo,toAcNo, amount);
				request.setAttribute("success", "SUCCESSFULLY TRANSFERRED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("list", list);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/transfer.jsp").forward(request, response);
			
			break;
		}
		case "accountManipulate":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				List<Long> list1=userHelper.getInactiveAccounts(userId);
				request.setAttribute("list", list);
				request.setAttribute("list1", list1);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/accountManipulate.jsp").forward(request, response);
			break;
		}
		case"DEACTIVATE":{
			System.out.println("pasjoiwjeoifjowiejfiweiw");
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(request.getParameter("select"));
				String table ="account_activate_request";
				userHelper.checkDuplicateRequest(table,acNo);
				AccountActivatePojo pojoHelper = new AccountActivatePojo();
				pojoHelper.setUserId(userId);
				pojoHelper.setAcNo(acNo);
				userHelper.AccountDeactivateRequest(pojoHelper);
				request.setAttribute("success", "REQUEST SUBMITTED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}try {
				List<Long> list=userHelper.getUserAccounts(userId);
				List<Long> list1=userHelper.getInactiveAccounts(userId);
				request.setAttribute("list", list);
				request.setAttribute("list1", list1);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/accountManipulate.jsp").forward(request, response);
			break;
		}
		case"ACTIVATE":{
			System.out.println("pasjoiwjeoifjowiejfiweiw");
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(request.getParameter("select1"));
				String table ="account_activate_request";
				userHelper.checkDuplicateRequest(table,acNo);
				AccountActivatePojo pojoHelper = new AccountActivatePojo();
				pojoHelper.setUserId(userId);
				pojoHelper.setAcNo(acNo);
				userHelper.AccountActivateRequest(pojoHelper);
				request.setAttribute("success", "REQUEST SUBMITTED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
				
			}try {
				List<Long> list=userHelper.getUserAccounts(userId);
				List<Long> list1=userHelper.getInactiveAccounts(userId);
				request.setAttribute("list", list);
				request.setAttribute("list1", list1);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			request.getRequestDispatcher("jsp/accountManipulate.jsp").forward(request, response);
			
			break;
		}case "changePass":{
			request.getRequestDispatcher("jsp/changePassword.jsp").forward(request, response);
			break;
		}
		case "CHANGE PASSWORD":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			String old = request.getParameter("old");
			String newPass = request.getParameter("newPass");
			String enter = request.getParameter("enter");
			if(newPass.equals(enter)) {
				try{
					userHelper.changeUserPassword(userId, old, newPass);
					request.setAttribute("success", "PASSWORD CHANGED");
				}catch(CustomException e){
					request.setAttribute("error1", e.getMessage());
					request.getRequestDispatcher("jsp/changePassword.jsp").forward(request, response);
				}
			}else {
				request.setAttribute("error2", "NEW PASSWORD DOES NOT MATCH");
				request.getRequestDispatcher("jsp/changePassword.jsp").forward(request, response);
			}

			break;
		}
		case "adminDetails":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				UserPojo adminPojo = adminHelper.getAdminDetails(userId);
				request.setAttribute("userpojo", adminPojo);
				request.setAttribute("customer", "hide");
				request.getRequestDispatcher("jsp/adminDetails.jsp").forward(request, response);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;

		}
		case "SAVE ADMIN DETAILS":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				Long mobile=Long.parseLong(request.getParameter("mobile"));
				String email=request.getParameter("email");
				Long mobileOld=Long.parseLong(request.getParameter("mobileold"));
				String emailOld=request.getParameter("emailold");
				if(email.equals(emailOld)&&mobile.equals(mobileOld)) {
					request.setAttribute("error","CHANGE DETAILS TO UPDATE");
				}else {
				userHelper.modifyUserDetails(userId, mobile, email);
				request.setAttribute("success", "SAVED DETAILS");
				}
			} catch (CustomException e) {
				request.setAttribute("error",e.getMessage());
				e.printStackTrace();	
			}
			try {
				UserPojo adminPojo = adminHelper.getAdminDetails(userId);
				request.setAttribute("userpojo", adminPojo);
				request.getRequestDispatcher("jsp/adminDetails.jsp").forward(request, response);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;			
		}
		case "getWithdrawRequest":{
			try {
				Map<Integer, WithdrawRequestPojo> map = adminHelper.getWithdrawRequests();
				request.setAttribute("requests", map);
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
			}
			request.getRequestDispatcher("jsp/withdrawAccept.jsp").forward(request, response);
			break;
		}
		case "acceptWithdraw":{
			int reqNo =  Integer.parseInt(request.getParameter("reqno"));
			try {
				adminHelper.acceptWithdraw(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}try {
				Map<Integer, WithdrawRequestPojo> map = adminHelper.getWithdrawRequests();
				request.setAttribute("requests", map);
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
			}
			request.getRequestDispatcher("jsp/withdrawAccept.jsp").forward(request, response);
		}
		case "denyWithdraw":{
			int reqNo =  Integer.parseInt(request.getParameter("reqno"));
			try {
				adminHelper.withdrawRejected(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}try {
				Map<Integer, WithdrawRequestPojo> map = adminHelper.getWithdrawRequests();
				request.setAttribute("requests", map);
				
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());	
			}
			request.getRequestDispatcher("jsp/withdrawAccept.jsp").forward(request, response);
			break;
		}
		case "activateRequest":{
			try {
				Map<Integer, AccountActivatePojo> map = adminHelper.getActivationRequests();
				request.setAttribute("requests", map);
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			}
			break;
		}
		case "acceptActivation":{
			int reqNo = Integer.parseInt(request.getParameter("requestnumber"));
			try {
				adminHelper.updateActivationApproved(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}try {
				Map<Integer, AccountActivatePojo> map = adminHelper.getActivationRequests();
				request.setAttribute("requests", map);
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			}
			break;
		}
		case"denyActivation":{
			int reqNo = Integer.parseInt(request.getParameter("requestnumber"));
			try {
				adminHelper.updateActivationRejected(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}try {
				Map<Integer, AccountActivatePojo> map = adminHelper.getActivationRequests();
				request.setAttribute("requests", map);
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			} catch(CustomException e) {
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/accountRequest.jsp").forward(request, response);
			}
			break;
		}

		case "createCustomer":{
			request.getRequestDispatcher("jsp/createCustomer.jsp").forward(request, response);
			break;
		}
		case"CREATE":{
			CustomerPojo customer = new CustomerPojo();
			AccountPojo account = new AccountPojo();
			customer.setName(request.getParameter("name"));
			customer.setEmail(request.getParameter("email"));
			customer.setMobileNo(Long.parseLong(request.getParameter("mobile")));
			customer.setAadharId(Long.parseLong(request.getParameter("aadhar")));
			customer.setPanNumber(request.getParameter("pan"));
			customer.setPassword(request.getParameter("password"));
			double amount= Double.parseDouble(request.getParameter("deposit"));
			account.setAccountType(request.getParameter("accountType"));
			account.setAccountBranch(request.getParameter("branch"));
			try {
				adminHelper.createCustomer(customer, account, amount);
				request.setAttribute("success", "CUSTOMER CREATED");
			} catch (CustomException | SQLException e) {
				e.printStackTrace();
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				//request.getRequestDispatcher("jsp/userDetails.jsp").forward(request, response);
			}request.getRequestDispatcher("jsp/createCustomer.jsp").forward(request, response);
			break;	
		}
		case "createAccountPage":{
			request.getRequestDispatcher("jsp/createAccount.jsp").forward(request, response);
			break;
		}
		case "CREATE ACCOUNT":{
			int userId = Integer.parseInt(request.getParameter("userId"));
			AccountPojo account = new AccountPojo();
			double amount= Double.parseDouble(request.getParameter("deposit"));
			account.setAccountType(request.getParameter("accountType"));
			account.setAccountBranch(request.getParameter("branch"));
			try {
				adminHelper.createAccount(userId, account, amount);
				request.setAttribute("success", "ACCOUNT CREATED");
			} catch (CustomException e) {
				e.printStackTrace();
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("jsp/createAccount.jsp").forward(request, response);
			}
			request.getRequestDispatcher("jsp/createAccount.jsp").forward(request, response);
			break;
		}
		case "searchCustomer":{
			request.getRequestDispatcher("jsp/searchCustomer.jsp").forward(request, response);
			break;
		}

		case"getUserDetails":{
			int userId=Integer.parseInt(request.getParameter("customerId"));
			try {
				CustomerPojo customerpojo = userHelper.getUserDetails(userId).get(userId);
				request.setAttribute("userpojo", customerpojo);
				request.setAttribute("admin", "hide");
				request.getRequestDispatcher("jsp/userDetails.jsp").forward(request, response);
			} catch (CustomException e) {
				request.setAttribute("hide", "hide");
				request.setAttribute("admin", "hide");
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("jsp/userDetails.jsp").forward(request, response);

			}

			break;
		}
		case "getUserAccounts":{
			int userId=Integer.parseInt(request.getParameter("customerId"));
			try {
				Map<Integer,Map<Long,AccountPojo>> map =userHelper.getAccountDetails(userId);
				request.setAttribute("map", map);
			} catch (CustomException e) {	
				e.printStackTrace();
				request.setAttribute("hide", "hide");
				request.setAttribute("admin", "hide");
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("jsp/myAccount.jsp").forward(request, response);
			}
			request.getRequestDispatcher("jsp/myAccount.jsp").forward(request, response);
			break;
		}
		case "viewTransactionsAdmin":{
			int userId=Integer.parseInt(request.getParameter("customerId"));
			try {
				request.setAttribute("userId", userId);
				List<Long> list=userHelper.getUserAccounts(userId);
				request.setAttribute("render", "hide");
				request.setAttribute("admin", "hide");
				request.setAttribute("list", list);
			} catch (CustomException e) {
				request.setAttribute("hide1", "hide");
				request.setAttribute("admin", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			}request.setAttribute("userId", userId);
			request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			break;
		}
		case"showAllCustomers":{
			try {
				Map<Integer,CustomerPojo>map= userHelper.getUserDetails();
				request.setAttribute("map", map);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("jsp/showAllUser.jsp").forward(request, response);
			break;
		}
		case "searchAccount":{
			request.getRequestDispatcher("jsp/searchAccount.jsp").forward(request, response);
			break;
		}
		case"getAccountDetails":{
			
			long acNo=Long.parseLong(request.getParameter("acNo"));
			try {
				AccountPojo account = adminHelper.getSpecificAccount(acNo).get(acNo);
				request.setAttribute("value", account);
				
			} catch (CustomException e) {
				e.printStackTrace();
				
				request.setAttribute("hide", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/viewAccount.jsp").forward(request, response);
			}
			request.getRequestDispatcher("jsp/viewAccount.jsp").forward(request, response);
			break;
		}
		case"showAllAccounts":{
			try {
				Map<Integer, Map<Long, AccountPojo>>map= userHelper.getAccountDetails();
				request.setAttribute("map", map);
				
			} catch (CustomException e) {
				e.printStackTrace();
				
			}
			request.getRequestDispatcher("jsp/myAccount.jsp").forward(request, response);
			break;
		}
		case"getAccountTransaction":{
			long acNo=Long.parseLong(request.getParameter("acNo"));
			try {
				long time=System.currentTimeMillis();
				long days=Long.parseLong(request.getParameter("days"));
				time=time-days;
				Map<Long,Map<Integer,TransactionPojo>> map=userHelper.showAccountTransaction(time, acNo);
				Map<Integer,TransactionPojo> map1=map.get(acNo);
				request.setAttribute("acNo", acNo);
				
				request.setAttribute("map", map1);
				request.setAttribute("hide1", "hide");
			} catch (CustomException e) {	
				
				request.setAttribute("hide1", "hide");
				request.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			}
			request.getRequestDispatcher("jsp/viewTransactions.jsp").forward(request, response);
			break;
		}
		case "modifyDetails":{
			request.getRequestDispatcher("jsp/modifyDetails.jsp").forward(request, response);
			break;

		}
		case "logout":{
			HttpSession session = request.getSession(false);
			session.invalidate();
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
		}

		}

	}
}
