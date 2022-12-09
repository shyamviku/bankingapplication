<!DOCTYPE html>
<html>
<head>
<style>
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<h5  class=error >${error}</h5>
<div style=display:flex>
<form action="<%=request.getContextPath() %>/MainServlet" method=post TARGET=showAccount>
<label>ACCOUNT NUMBER</label>
<input type="number" value="${acNo}"  name=acNo required>
<button class="actionbutton" name=button value=getAccountDetails>ACCOUNT DETAILS</button>
<button class="actionbutton " name=button value=getAccountTransaction>ACCOUNT TRANSACTION</button>
<select class=dropdownbox required name=days>
<option value=1296000000>15 DAYS</option>
<option value=2592000000>30 DAYS</option>
<option value=15552000000>6 MONTHS</option>
</select>
</form>

<form  action="<%=request.getContextPath() %>/MainServlet" method=post TARGET=showAccount>
<button class="actionbutton" value="showAllAccounts" name=button >ALL ACCOUNTS</button>
</form></div>
<iframe  class=showframe name=showAccount></iframe>
</body>
</html>