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
	<div class=content>
		<h3 class=error >${error}</h3>
		<h3 class=success >${success}</h3>
			<form action="<%=request.getContextPath()%>/MainServlet"
				method="post">
				<input name=emailold type=hidden value="${userpojo.getEmail()}">
				<input name=mobileold type=hidden value="${userpojo.getMobileNo()}">
				<table class=contenttable>
					<tr>
						<td class=label>ID</td>
						<td>${userpojo.getUserId()}</td>
					</tr>
					<tr>
						<td class=label>NAME</td>
						<td>${userpojo.getName()}</td>
					</tr>
					<tr>
						<td class=label>EMAIL</td>
						<td><input type="email" value="${userpojo.getEmail()}"
							name=email /></td>
					</tr>
					<tr>
						<td class=label>MOBILE</td>
						<Td><input type="tel" value="${userpojo.getMobileNo()}"
							name=mobile /></Td>
					</tr>
					
				</table>
				<div class=savebutton>
					<BUTTON CLASS="${admin} actionbutton" value="SAVE ADMIN DETAILS" name=button>SAVE</BUTTON>
				</div>

			</form>
		</div>

</body>
</html>