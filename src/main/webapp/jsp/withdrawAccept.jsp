<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://kit.fontawesome.com/998f87dcd9.js" crossorigin="anonymous"></script>
<style>
.hide {
	visibility: hidden
}
</style>
<meta charset="ISO-8859-1">
<title>REQUESTS</title>
</head>
<link rel=stylesheet type=text/css
	href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
	<header>
		<p CLASS=topic>WITHDRAW REQUESTS</p>
	</header>
	<h3 CLASS=error>${error}</h3>
	<table class="${hide} contenttable">
		<tr>
			<th>ID</th>
			<th>USER ID</th>
			<th>ACCOUNT NUMBER</th>
			<th>AMOUNT</th>
			<th>TIME</th>
			<th>SELECT</th>
		</tr>
		<c:forEach var="entry" items="${requests }">
			<form action="<%=request.getContextPath()%>/MainServlet"
				method="post">
				<tr>
					<td>${entry.key }<input name=reqno type="hidden"
						value=${entry.key }></td>
					<td>${entry.value.getUserId() }</td>
					<td>${entry.value.getAccountNumber() }</td>
					<td>${entry.value.getAmount() }</td>
					<td><jsp:useBean id="date" class="java.util.Date" /> <c:set
							target="${date}" property="time"
							value="${entry.value.getTime() }" /> <fmt:formatDate
							value="${date }" pattern="dd-MM-yyyy HH:mm" /></td>
					<TD><button class=accept value=acceptWithdraw name=button><i class="fa-solid fa-check"></i></button>
						<button class=decline value=denyWithdraw name=button><i class="fa-solid fa-x"></i></button></TD>
				</tr>
			</form>
		</c:forEach>
	</table>

</body>
</html>