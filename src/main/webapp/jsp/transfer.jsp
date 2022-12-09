<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css
	href="<%=request.getContextPath()%>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>TRANSFER</p>
</header>
<form action="<%=request.getContextPath() %>/MainServlet" method="post">
<TABLE class=centertable>
<tr>
<th>TO ACCOUNT</th> 
<td><select CLASS=dropdownbox name=select required> 
<option value="">SELECT AN ACCOUNT</option> 
<c:forEach var="entryss" items="${list}">
<option value ="${entryss}"> ${entryss}</option>
</c:forEach>      
</select></td>
<tr>
<th>AMOUNT</th><td><input required TYPE="number" name=amount></td>
</tr>
<tr>
<th>TO ACCOUNT</th><td><input TYPE="number" NAME=toaccount></td>
</tr>
<tr>
<td></td><td><input class=actionbutton name= button type="submit" value="TRANSFER"/></td>
</tr>
</TABLE>
</form>
<h5 class=error>${error}</h5><h5 class=success>${success}</h5>
</body>
</html>