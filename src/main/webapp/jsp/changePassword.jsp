<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>CHANGE PASSWORD</p>
</header>
<form action="<%=request.getContextPath() %>/MainServlet" method="post" TARGET=customer>
<table class=centertable>
<tr><th>OLD PASSWORD</th><td><input required name=old TYPE="password"></td></tr>
<tr><th>NEW PASSWORD</th><td><input required name=newPass TYPE="password"></td></tr>
<tr><th>RE-ENTER </th><td><input required name=enter TYPE="password"></td></tr>
<tr><th></th><td><input class=actionbutton name=button value="CHANGE PASSWORD" type="submit"></td></tr>
</table>
</form>
<h5 CLASS=error>${error1}</h5><h5 class=success >${success}</h5><h5 class=error >${error2}</h5>
</body>
</html>