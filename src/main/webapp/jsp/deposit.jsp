<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>TRANSACTIONS</p>
</header>
	<form action="<%=request.getContextPath()%>/MainServlet" method="post">
		<table class=centertable>
			<tr>
				<th>ACCOUNT</th>
				<td><select CLASS=dropdownbox name=select required>
						<option value="">SELECT AN ACCOUNT</option>
						<c:forEach var="entrys" items="${list}">
							<option value="${entrys}">${entrys}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>AMOUNT</th>
				<td><input required TYPE="number" min=1 name=amount></td>
			</tr>
			<tr>
				<td></td>
				<td><button class=actionbutton  type="submit" value="DEPOSIT" name=button>DEPOSIT</button></td>
			</tr>
		</table>
	</form>
	<h5 CLASS=error>${error}</h5>
	<h5 class=success>${success}</h5>
</body>
</html>