<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>ACTIVATION REQUESTS</p>
</header>
<h5 class=success>${success}</h5><h5 class=error>${error}</h5>
<form action="<%=request.getContextPath() %>/MainServlet" method="post">
<TABLE class=centertable>
<tr>
<th>ENTER CUSTOMER ID</th>
<td><input type=number required name=userId ></td>
<tr>
		<th>ACCOUNT TYPE</th>
		<td><input TYPE="text" name=accountType required oninput="this.value = this.value.toUpperCase()"></td>
		<span>TYPE</span>
		</tr>
		<tr>
		<th>BRANCH</th>
		<td><input TYPE="text" name=branch required oninput="this.value = this.value.toUpperCase()"></td>
		</tr>
		<tr>
		<th>PASSWORD</th>
		<td><input TYPE="text" name=PASSWORD required oninput="this.value = this.value.toUpperCase()"></td>
		</tr>
		<tr>
		<th>INITIAL DEPOSIT</th>
		<td><input TYPE="NUMBER" name=deposit min=500 required></td>
		</tr>
		<tr>
		<th></th>
		<td><input class=actionbutton type="submit" value="CREATE ACCOUNT" name=button></td>
		</tr>
	</table>
	
	</form>
</body>
</html>