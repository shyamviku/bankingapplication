
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<title>ZOHO INTERNATIONAL BANK</title>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
</head>
<body class=mainpage>
<img class = "loginlogo" alt="zoho" src="<%=request.getContextPath() %>\images\OIP.png">
<p class=logintitle align=center >ZOHO INTERNATIONAL BANK</p>
<div>
<form class=loginform action="<%=request.getContextPath() %>/MainServlet" method="post">
<table class=centertable>
<tr>
<td>USER ID</td><td><input class=logininput required name=userid type="number"></input></td>
</tr>
<tr>
<td>PASSWORD</td><td><input class=logininput required name=password type="password"></input></td>
</tr>
<tr>
<td></td>
<td><button class=actionbutton value="login" name=button>LOGIN</button></td>
</tr>
</table>
</form>
<h5 class=error >${error}</h5>
</div>
</body>
</html>