<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<h5  class=error >${error}</h5>
<div style=display:flex>
<form action="<%=request.getContextPath() %>/MainServlet" method=post TARGET=showframe>
<label>CUSTOMER ID</label>
<input  type="number" value="${userId}" max=2147483647 name=customerId required>
<button class=actionbutton name=button value=getUserDetails>DETAILS</button>
<button class=actionbutton name=button value=getUserAccounts>ACCOUNTS</button>
<button class=actionbutton name=button value=viewTransactionsAdmin>TRANSACTIONS</button>
<button class=actionbutton name=button value=modifyDetails>MODIFY DOCUMENTS</button>
</form>
<form action="<%=request.getContextPath() %>/MainServlet" method=post TARGET=showframe>
<button class=actionbutton name=button value=showAllCustomers>ALL CUSTOMERS</button>
</form>
</div>
<iframe  class=showframe name=showframe></iframe>
<script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
<script>




</script>
</body>
</html>