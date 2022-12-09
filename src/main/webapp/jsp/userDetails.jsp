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
		<p CLASS=topic>PROFILE</p>
	</header>
	<h3 class=error>${error}</h3>
	<h3 class=success>${success}</h3>
	<div class=${hide }>
		<form action="<%=request.getContextPath()%>/MainServlet" method="post">
			<input name=emailold type=hidden value="${userpojo.getEmail()}">
			<input name=mobileold type=hidden value="${userpojo.getMobileNo()}">
			<table class=contenttable>
				<tr>
					<td>ID</td>
					<td>${userpojo.getUserId()}</td>
				</tr>
				<tr>
					<td>NAME</td>
					<td>${userpojo.getName()}</td>
				</tr>
				<tr class=${admin}>
					<td>EMAIL</td>
					<td><input style=background:transparent;  type="email" value="${userpojo.getEmail()}"
						name=email /></td>
				</tr>

				<tr class=${admin}>
					<td>MOBILE</td>
					<Td><input style=background:transparent; type="tel" value="${userpojo.getMobileNo()}"
						name=mobile /></Td>
				</tr>
				<tr  class=${customer}>
					<td>EMAIL</td>
					<td><input style=background:transparent;width:50%;border:none;outline:none type="email" readonly
						value="${userpojo.getEmail()}" name=email /></td>
				</tr>
				<tr  class=${customer}>
					<td>MOBILE</td>
					<Td><input type="text" readonly
						value="${userpojo.getMobileNo()}" name=mobile /></Td>
				</tr>
				<tr>
					<td>AADHAR</td>
					<td>${userpojo.getAadharId()}</td>
				</tr>
				<tr>
					<td>PAN NUMBER</td>
					<td>${userpojo.getPanNumber()}</td>
				</tr>
			</table>
			<div class=savebutton>
				<BUTTON CLASS="${admin} actionbutton " value="SAVE CUSTOMER DETAILS"
					name=button>SAVE</BUTTON>
			</div>

		</form>
	</div>

</body>
</html>