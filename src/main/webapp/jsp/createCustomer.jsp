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
		<p CLASS=topic>ADD CUSTOMER</p>
	</header>
	<h5 class=success>${success}</h5>
	<h5 class=error>${error}</h5>
	<form autocomplete=off action="<%=request.getContextPath()%>/MainServlet" method="post">
		<div class=centerdiv>
			<div class=inputbox>
				<input autocomplete=off required TYPE="text" name=name
					oninput="this.value = this.value.toUpperCase()" /> <span>Name</span>
			</div>
			<div class=inputbox>
				<input TYPE="email" name=email autocomplete=off required> <span>Email</span>
			</div>
			<div class=inputbox>
				<input TYPE="tel" name=mobile autocomplete=off required> <span>Mobile</span>
			</div>
			<div class=inputbox>
				<input TYPE="number" name=aadhar size=14 autocomplete=off maxlength=999999999999
					required> <span>Aadhar ID</span>
			</div>
			<div class=inputbox>
				<input TYPE="text" autocomplete=off name=pan required
					oninput="this.value = this.value.toUpperCase()"> <span>Pan
					Number</span>
			</div>
			<div class=inputbox>
				<input TYPE="text" name=accountType autocomplete=off required
					oninput="this.value = this.value.toUpperCase()"> <span>Account
					Type</span>
			</div>
			<div class=inputbox>
				<input TYPE="text" autocomplete=off name=branch required
					oninput="this.value = this.value.toUpperCase()"> <span>Branch</span>
			</div>
			<div class=inputbox>
				<input TYPE="NUMBER" autocomplete=off name=deposit min=500 required> <span>Initial
					Deposit</span>
			</div>
			<div>
				<input class=actionbutton autocomplete=off type="submit" value="CREATE" name=button>
			</div>
		</div>
	</form>

</body>
</html>