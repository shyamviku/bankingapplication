<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<p CLASS=topic>ACCOUNT ACTIVATION</p>
	</header>
	<table class=centertable>
		<form action="<%=request.getContextPath()%>/MainServlet" method="post">
			<tr>
				<th>ACCOUNT DEACTIVATE</th>
				<td><select required class=dropdownbox name=select>
						<option value="">SELECT AN ACCOUNT</option>
						<c:forEach var="entrys" items="${list}">
							<option value="${entrys}">${entrys}</option>
						</c:forEach>
				</select> <input class=actionbutton TYPE="submit" VALUE="DEACTIVATE"
					NAME=button /></td>
			</tr>
		</form>
		<form action="<%=request.getContextPath()%>/MainServlet" method="post">
		<tr>
			<th>ACCOUNT ACTIVATE</th>
			<td><select required class=dropdownbox name=select1>
					<option value="">SELECT AN ACCOUNT</option>
					<c:forEach var="entrys" items="${list1}">
						<option value="${entrys}">${entrys}</option>
					</c:forEach>
			</select> <input class=actionbutton TYPE="submit" VALUE="ACTIVATE"
				NAME=button /></td>
		</tr>
		</form>
	</table>
	<h4 style="color: red" ;font-weight:bold>${error}</h4>
	<h4 style="color: green" ;font-weight:bold>${success}</h4>


</body>
</html>