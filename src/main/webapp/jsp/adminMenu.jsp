<!DOCTYPE html>
<html LANG="en">
<head>
<meta charset="UTF-8">
<title>ADMIN MENU</title>
</head>
<link rel=stylesheet type=text/css href="<%=request.getContextPath() %>/css/mainpage.css">
<body>
<div class=menu>
<div><h1 align=center>MENU</h1></div>
<div><button value=getWithdrawRequest id=home  name =button>HOME</button></div>
<div><button value= activateRequest name =button>ACCOUNT REQUESTS</button></div>
<div><button value="createCustomer" name=button>ADD CUSTOMER</button></div>
<div><button value="createAccountPage" name=button >ADD ACCOUNT</button></div>
<div><button value="searchCustomer" name=button >CUSTOMER</button></div>
<div></div>
<div></div>
<div><button value="searchAccount" name=button >ACCOUNT</button></div>

</div>
<script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
<script>
window.onload=function(){
$("#home").click();
}
</script>
</body>
</html>