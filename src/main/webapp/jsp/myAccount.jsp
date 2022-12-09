<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style>
.hide{
visibility:hidden
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
<header class="${admin}">
<p CLASS=topic>ACCOUNTS</p>
</header>
<h3 style=color:red ;font-weight:bold>${error}</h3>
<div class="${hide}">
<table class=contenttable>
<tr>
<th>ID</th>
<th>ACCOUNT NUMBER</th>
<th>TYPE</th>
<th>BRANCH</th>
<th>STATUS</th>
<th>BALANCE</th>
</tr>
<c:forEach var="entry1" items= "${map}" > 
<c:forEach var="entry" items= "${entry1.value}" >
<tr>
<td>${entry1.key}</td>
<td>${entry.key}</td>
<td>${entry.value.getAccountType()}</td>
<td>${entry.value.getAccountBranch()}</td>
<td>${entry.value.getAccountStatus()}</td>
<td>${entry.value.getAccountBalance()}</td>
</tr>
</c:forEach>
</c:forEach>
</table>
</div>
</body>
</html>