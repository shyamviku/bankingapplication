

import java.io.BufferedReader;
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

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import bankingmethods.AdminMethods;
import bankingmethods.UserMethods;
import bankingpojo.AccountActivatePojo;
import bankingpojo.AccountPojo;
import bankingpojo.CustomerPojo;
import bankingpojo.TransactionPojo;
import bankingpojo.WithdrawRequestPojo;
import exceptionhandling.CustomException;

/**
 * Servlet implementation class BankServlet
 */
@WebServlet("/BankServlet")
public class BankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserMethods userHelper = new UserMethods();
	private AdminMethods adminHelper = new AdminMethods();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BankServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String parameter;
		JSONObject jsonObject;
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /*report an error*/ }

		try {
			jsonObject =  new JSONObject(jb.toString());
			parameter = jsonObject.optString("button");

		} catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}



		switch(parameter ){
		case "login":{
			int userId=Integer.parseInt(jsonObject.optString("userid"));
			String pass =jsonObject.optString("pwd");
			try {

				String role = userHelper.userLogin(userId,pass);
				HttpSession session =request.getSession(true);
				session.setAttribute("customerId", userId);
				String obj;
				if(role.equals("CUSTOMER")) {
					obj="{url:customer.html}";

				}else {
					obj="{url:admin.html}";
					//					HttpSession session =request.getSession(true);
					//					session.setAttribute("customerId", userId);
				}
				JSONObject jsonResponse=new JSONObject(obj.toString());
				String json=jsonResponse.toString();
				response.getWriter().write(json);

			} catch (CustomException e) {
				String error=e.getMessage();
				String obj="{error:"+error+"}";
				JSONObject jsonResponse=new JSONObject(obj.toString());
				String json=jsonResponse.toString();
				response.getWriter().write(json);
				e.printStackTrace();
			}
			break;
		}
		case "myAccount":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			if(!jsonObject.optString("userid").isEmpty()) {
				userId = Integer.parseInt(jsonObject.optString("userid"));
			}
			try {
				Map<Integer,Map<Long,AccountPojo>> map =userHelper.getAccountDetails(userId);
				Map<Long,AccountPojo> map2=map.get(userId);
				JSONObject jsonResponse=new JSONObject(map2);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {	
				e.printStackTrace();
			}
			break;
		}
		case "viewTransaction":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			if(!jsonObject.optString("userid").isEmpty()) {
				userId = Integer.parseInt(jsonObject.optString("userid"));
			}
			try {
				List<Long> list=userHelper.getUserAccounts(userId);
				String j = "{list:"+list+"}";

				JSONObject jsonResponse=new JSONObject(j);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {	
				request.setAttribute("error",e.getMessage());
			}

			break;
		}
		case "showTransaction":{
			long acNo = Long.parseLong(jsonObject.optString("acno"));
			try {
				long time = System.currentTimeMillis()-2592000000L;
				Map<Long,Map<Integer,TransactionPojo>> map=userHelper.showAccountTransaction( time,acNo);
				Map<Integer,TransactionPojo> map1=map.get(acNo);
				JSONObject jsonResponse=new JSONObject(map1);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			break;
		}

		case "deposit":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(jsonObject.optString("acno"));
				double amount = Double.parseDouble(jsonObject.optString("amount"));
				userHelper.userDeposit(userId, acNo, amount);
				request.setAttribute("success", "REQUEST SUBMITTED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}
		}
		case "transfer":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(jsonObject.optString("acno"));
				long toAcNo=Long.parseLong(jsonObject.optString("toac"));
				double amount = Double.parseDouble(jsonObject.optString("amount"));		
				userHelper.moneyTransfer(userId, acNo,toAcNo, amount);
				request.setAttribute("success", "SUCCESSFULLY TRANSFERRED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
			}
			break;
		}case "REQUEST WITHDRAW":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(jsonObject.optString("acno"));
				String table ="withdraw_request";
				userHelper.checkDuplicateRequest(table,acNo);
				double amount = Double.parseDouble(jsonObject.optString("amount"));
				userHelper.userWithdrawRequest(userId, acNo, amount);
				String obj="{success:REQUEST SUBMITTED}";
				JSONObject jsonResponse=new JSONObject(obj.toString());
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {	
				e.printStackTrace();
				String obj="{error: "+e.getMessage()+"}";
				JSONObject jsonResponse=new JSONObject(obj.toString());
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			}
			break;
		}
		case "accountManipulate":{
			System.out.println("wiuef");
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				List<Long> list2=userHelper.getInactiveAccounts(userId);
				System.out.println(list2);
				List<Long> list1=userHelper.getUserAccounts(userId);
				System.out.println(list1);
				String j = "{list1:"+list1+",list2:"+list2+"}";
				JSONObject jsonResponse=new JSONObject(j);
				String json=jsonResponse.toString();
				System.out.println(j);
				response.getWriter().write(json);
			} catch (CustomException e) {	
				request.setAttribute("error",e.getMessage());
			}

			break;
		}
		case"DEACTIVATE":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(jsonObject.optString("acno"));
				String table ="account_activate_request";
				userHelper.checkDuplicateRequest(table,acNo);
				AccountActivatePojo pojoHelper = new AccountActivatePojo();
				pojoHelper.setUserId(userId);
				pojoHelper.setAcNo(acNo);
				userHelper.AccountDeactivateRequest(pojoHelper);
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());
				e.getStackTrace();
			}
			break;
		}
		case"ACTIVATE":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			try {
				long acNo=Long.parseLong(jsonObject.optString("acno"));
				String table ="account_activate_request";
				userHelper.checkDuplicateRequest(table,acNo);
				AccountActivatePojo pojoHelper = new AccountActivatePojo();
				pojoHelper.setUserId(userId);
				pojoHelper.setAcNo(acNo);
				userHelper.AccountActivateRequest(pojoHelper);
				request.setAttribute("success", "REQUEST SUBMITTED");
			} catch (CustomException e) {	
				request.setAttribute("error", e.getMessage());

			}
			break;
		}
		case"userDetails":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			if(!jsonObject.optString("userid").isEmpty()) {
				userId = Integer.parseInt(jsonObject.optString("userid"));
			}
			try {
				CustomerPojo customerPojo = userHelper.getUserDetails(userId).get(userId);
				JSONObject jsonResponse=new JSONObject(customerPojo);
				String json=jsonResponse.toString();
				System.out.println(json);
				response.getWriter().write(json);
			} catch (CustomException e) {
				e.printStackTrace();
			}

			break;
		}
		case"savecustomerdetails":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			Long mobile =Long.parseLong(jsonObject.optString("mobile"));
			String email=jsonObject.optString("email");
			try {
				System.out.println(email);
				userHelper.modifyUserDetails(userId, mobile, email);
				request.setAttribute("success", "SAVED DETAILS");

			} catch (CustomException e) {
				request.setAttribute("error",e.getMessage());
				e.printStackTrace();	
			}
			break;
		}
		case "CHANGE PASSWORD":{
			HttpSession session =request.getSession(false);
			int userId= (int) session.getAttribute("customerId");
			String old = jsonObject.optString("old");
			String newPass = jsonObject.optString("new");
			String enter = jsonObject.optString("enter");
			if(newPass.equals(enter)) {
				try{
					userHelper.changeUserPassword(userId, old, newPass);
					request.setAttribute("success", "PASSWORD CHANGED");
				}catch(CustomException e){
					request.setAttribute("error1", e.getMessage());
				}
			}else {
				request.setAttribute("error2", "NEW PASSWORD DOES NOT MATCH");
			}

			break;
		}
		case "getWithdrawRequest":{
			try {
				Map<Integer, WithdrawRequestPojo> map = adminHelper.getWithdrawRequests();
				JSONObject jsonResponse=new JSONObject(map);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch(CustomException e) {
				e.printStackTrace();

			}
			break;
		}
		case "acceptWithdraw":{
			int reqNo =  Integer.parseInt(jsonObject.optString("reqno"));
			try {
				adminHelper.acceptWithdraw(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case "denyWithdraw":{
			int reqNo =  Integer.parseInt(jsonObject.optString("reqno"));
			try {
				adminHelper.withdrawRejected(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case "activateRequest":{
			try {
				Map<Integer, AccountActivatePojo> map = adminHelper.getActivationRequests();
				JSONObject jsonResponse=new JSONObject(map);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch(CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case "acceptActivation":{
			int reqNo =  Integer.parseInt(jsonObject.optString("reqno"));
			try {
				adminHelper.updateActivationApproved(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case"denyActivation":{
			int reqNo =  Integer.parseInt(jsonObject.optString("reqno"));
			try {
				adminHelper.updateActivationRejected(reqNo);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case"CREATE CUSTOMER":{
			CustomerPojo customer = new CustomerPojo();
			AccountPojo account = new AccountPojo();
			customer.setName(jsonObject.optString("name"));
			customer.setEmail(jsonObject.optString("email"));
			customer.setMobileNo(Long.parseLong(jsonObject.optString("mobile")));
			customer.setAadharId(Long.parseLong(jsonObject.optString("aadhar")));
			customer.setPanNumber(jsonObject.optString("pan"));
			double amount= Double.parseDouble(jsonObject.optString("deposit"));
			account.setAccountType(jsonObject.optString("type"));
			account.setAccountBranch(jsonObject.optString("branch"));
			try {
				adminHelper.createCustomer(customer, account, amount);
				request.setAttribute("success", "CUSTOMER CREATED");
			} catch (CustomException | SQLException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
			break;	
		}
		case "CREATE ACCOUNT":{
			int userId = Integer.parseInt(jsonObject.optString("userId"));
			AccountPojo account = new AccountPojo();
			double amount= Double.parseDouble(jsonObject.optString("deposit"));
			account.setAccountType(jsonObject.optString("type"));
			account.setAccountBranch(jsonObject.optString("branch"));
			try {
				adminHelper.createAccount(userId, account, amount);
				request.setAttribute("success", "ACCOUNT CREATED");
			} catch (CustomException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
			break;
		}
		case"showAllCustomers":{
			try {
				Map<Integer,CustomerPojo>map= userHelper.getUserDetails();
				JSONObject jsonResponse=new JSONObject(map);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case"showAllAccounts":{
			try {
				Map<Integer, Map<Long, AccountPojo>>map= userHelper.getAccountDetails();
				JSONObject jsonResponse=new JSONObject(map);
				String json=jsonResponse.toString();
				response.getWriter().write(json);
			} catch (CustomException e) {
				e.printStackTrace();

			}
			break;
		}
		case"viewAccount":{

			long acNo=Long.parseLong(jsonObject.optString("acno"));
			try {
				AccountPojo account = adminHelper.getSpecificAccount(acNo).get(acNo);
				System.out.println(account.getAccountNumber());
				JSONObject jsonResponse=new JSONObject(account);
				String json=jsonResponse.toString();
				response.getWriter().write(json);

			} catch (CustomException e) {
				e.printStackTrace();
			}
			break;
		}
		case "logout":{
			HttpSession session = request.getSession(false);
			session.invalidate();
			break;
		}
		}

	}
}
