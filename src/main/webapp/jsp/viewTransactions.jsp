<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<style>
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css
	href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
<h3 style=color:red ;font-weight:bold>${error}</h3>
<div class="${hide}">
	<span><p CLASS=topic>TRANSACTIONS</p></span>
	<form action="<%=request.getContextPath()%>/MainServlet" method="post">
<label class="${hide1}" style=font-weight:bold>ACCOUNT</label>
 <select class="${hide1} dropdownbox" name=select required>
			<option value="" >SELECT AN
				ACCOUNT</option>
			<c:forEach var="entrys" items="${list}">
				<option value="${entrys}">${entrys}</option>
			</c:forEach>
		</select>
		<button  class="${hide1} actionbutton" value="showtransaction" name=button>SHOW</button>
		<input class=hide type=number value="${userId}" name ="userId">	
	
	</form>
	<table  class="${render} contenttable ${hide}">
		<tr>
			<th>ID</th>
			<th>TYPE</th>
			<th>FROM</th>
			<th>AMOUNT</th>
			<th>STATUS</th>
			<th>TIME</th>
			<th>CLOSING BALANCE</th>
		</tr>
		<c:forEach var="entry" items="${map}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value.getTypeOfTransaction()}</td>
				<td>${entry.value.getFromAccount()}</td>
				<td>${entry.value.getAmount()}</td>
				<td>${entry.value.getTransactionStatus()}</td>
				<td><jsp:useBean id="date" class="java.util.Date" /> <c:set
							target="${date}" property="time"
							value="${entry.value.getTimeOfTranstraction()}" /> <fmt:formatDate
							value="${date }" pattern="dd-MM-yyyy HH:mm" /></td>
				<TD>${entry.value.getClosingBalance()}</TD>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>