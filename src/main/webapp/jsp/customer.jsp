<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CUSTOMER PORTAL</title>
<script src="https://kit.fontawesome.com/998f87dcd9.js"
	crossorigin="anonymous"></script>
</head>
<link rel=stylesheet type=text/css
	href="<%=request.getContextPath()%>/css/mainpage.css">
<body class=mainpage>
	<header>
		<img class="loginlogo" alt="bank logo" src="images/OIP.png">
		<div class=logoutdropdown style="float: right">
			<button class="dropbtn">
				<i class="fa-solid fa-user"></i>
			</button>
			<div class="dropdown-content">
				<form action="<%=request.getContextPath()%>/MainServlet"
					method="post" target=customer>
					<div class=logobut>
						<button value=userDetails name=button>
							<i class="fa-solid fa-id-card"></i>PROFILE
						</button>
					</div>
					<div class=logobut>
						<button value=changePass name=button>
							<i class="fa-solid fa-pen"></i>CHANGE PASSWORD
						</button>
					</div>
				</form>
				<form action="<%=request.getContextPath()%>/MainServlet"
					method="post">
					<div class=logobut>
						<button value=logout name=button>
							<i class="fa-solid fa-house-lock"></i>LOG OUT
						</button>
					</div>
				</form>
			</div>
		</div>
	</header>
	<div style=display:flex >
	<iframe class=menuframe src="jsp/customerMenu.jsp" ></iframe>
	<iframe class=mainframe
		src="<%=request.getContextPath()%>/jsp/myAccount.jsp"  name=customer> </iframe>
		</div>
</body>
</html>