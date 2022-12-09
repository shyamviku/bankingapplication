<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<link rel=stylesheet type=text/css
	href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
<h3 style=color:red ;font-weight:bold>${error}</h3>
<DIV CLASS="${hide }">
<table class=contenttable>
<tr>
<th>ID</th>
<th>ACCOUNT NUMBER</th>
<th>TYPE</th>
<th>BRANCH</th>
<th>STATUS</th>
<th>BALANCE</th>
</tr>

</table>
</DIV>
</body>
</html>