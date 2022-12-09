<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<header>
<p CLASS=topic>ADD ACCOUNT</p>
</header>
<form autocomplete=off action="<%=request.getContextPath() %>/MainServlet" method="post">
<div class=centerdiv>
<div CLASS=inputbox>
<input type=number max=2147483647 autocomplete=off required name=userId >
<span>CUSTOMER ID</span>
</div>		
<div CLASS=inputbox>
<input TYPE="text" name=accountType required  autocomplete=off oninput="this.value = this.value.toUpperCase()">
<span>TYPE</span>
</div>		
<div CLASS=inputbox>
<input TYPE="text" name=branch required autocomplete=off oninput="this.value = this.value.toUpperCase()">
<span>BRANCH</span>
</div>		
<div CLASS=inputbox>
<input TYPE="NUMBER" autocomplete=off name=deposit min=500 max=1000000 required>
<span>INITIAL DEPOSIT</span>
</div>					
<DIV><input class=actionbutton autocomplete=off type="submit" value="CREATE ACCOUNT" name=button></DIV>
	</div>
	</form>
	<h5 class=success>${success}</h5><h5 class=error>${error}</h5>
</body>
</html>