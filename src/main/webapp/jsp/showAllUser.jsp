<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
<table class=contenttable>
<tr>
<th>ID</th>
<th>NAME</th>
<th>EMAIL</th>
<th>MOBILE</th>
<th>STATUS</th>
<th>AADHAR</th>
<th>PAN</th>
</tr>
<c:forEach var="customer" items="${map}">
<tr>
<td>${customer.value.getUserId()}</td>
<td>${customer.value.getName()}</td>
<td>${customer.value.getEmail()}</td>
<td>${customer.value.getMobileNo()}</td>
<td>${customer.value.getStatus()}</td>
<td>${customer.value.getAadharId()}</td>
<td>${customer.value.getPanNumber()}</td>
</tr>
</c:forEach>
</table>
</body>
</html>