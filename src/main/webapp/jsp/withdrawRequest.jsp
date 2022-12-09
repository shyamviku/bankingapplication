<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>WITHDRAW</p>
</header>
<form action="<%=request.getContextPath() %>/MainServlet" method="post">
<table class=centertable>
<tr>
<th><label>ACCOUNT</label></th>  
<td><select class=dropdownbox name=select required> 
<option value="">SELECT ACCOUNT</option> 
<c:forEach var="entryss" items="${list}">
<option value ="${entryss}"> ${entryss}</option>
</c:forEach>      
</select></td>
</tr>
<tr>
<th><label>AMOUNT</label></th>
<td><input required TYPE="number" min=1 name=amount></td>
</tr>
<tr><td></td>
<td><button class=actionbutton type="submit" value="REQUEST WITHDRAW" name =button>WITHDRAW</button></td>
</tr>
</form>
</table>
<h5 class=error>${error}</h5><h5 class=success>${success}</h5>
</body>
</html>