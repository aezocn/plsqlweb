<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<%
	UserBean ub = (UserBean) session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	   <title>Oracle Logon</title> 	
	</head>
	<link type="text/css" rel="stylesheet" href="../css/xmenu.css">
	<script type="text/javascript" src="../js/others.js"></script>
	<style>
body {
	background: buttonface;
	margin: 2px;
	padding: 0px;
	font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
}
</style>
	<body  ondragstart="return false" oncontextmenu="return false">
		<br/>
		<form name="form1" method="post" action="login/login.action" onsubmit="return cc()" enctype="">
			<table align="center" border="0" class="EditorBodyLog">
				<tr>
					<td>
						<table border="0" id="toolBar"
							style='background-color: buttonface;'>
							<tr>
								<td align="right">
									Username
								</td>
								<td height="25">
									<input name="username" type="text" style="width: 180px;"
										size="25" value="<%=ub.getUsername()%>">
								</td>

							</tr>
							<tr>
								<td align="right">
									Password
								</td>
								<td height="25">
									<input name="password" type="password" style="width: 180px;"
										size="25">
								</td>

							</tr>
							<tr>
								<td align="right">
									DatabaseIP
								</td>
								<td height="25">
									<input name="databaseip" type="text" style="width: 180px;"
										size="25" value="<%=ub.getDatabaseip()%>">
								</td>

							</tr>
							<tr>
								<td align="right">
									ListenPort
								</td>
								<td height="25">
									<input name="listenport" type="text" style="width: 180px;"
										size="25" value="<%=ub.getListenport()%>">
								</td>

							</tr>
							<tr>
								<td align="right">
									Database
								</td>
								<td height="25">
									<input name="servername" type="text" style="width: 180px;"
										size="25" value="<%=ub.getServername()%>">
								</td>

							</tr>
							<tr>
								<td align="right">
									Connect as
								</td>
								<td height="25">
									<select id="connect" name="connect">
										<option value="0" selected>
											Normal&nbsp;&nbsp;
										</option>
										<option value="2">
											SYSDBA&nbsp;&nbsp;
										</option>
										<option value="1">
											SYSOPER&nbsp;&nbsp;
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<p>
			<table border="0" id="toolBar" style='background-color: buttonface;'>
				<tr align="center">
					<td align="center" colspan="2">
					    <input type="hidden" name="relogin" value="true">
						<input type="submit" value="  ok  ">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="cannel" onClick="window.close()">
					</td>
				</tr>
			</table>

		</form>

		<script type="text/javascript">
          		function cc() {
          		   if(isEmpty(document.form1.username.value)){
          		      alert("Username is required");
          		      return false;
          		   }
          		   if(isEmpty(document.form1.password.value)){
          		      alert("Password is required");
          		      return false;
          		   }          		   
          		   if(isEmpty(document.form1.databaseip.value)){
          		      alert("DatabaseIP is required");
          		      return false;
          		   }          		   
          		   if(isEmpty(document.form1.listenport.value)){
          		      alert("ListenPort is required");
          		      return false;
          		   }          		   
          		   if(isEmpty(document.form1.servername.value)){
          		      alert("ServerName is required");
          		      return false;
          		   }          		   
          		   return true;
				}
   		</script>

	</body>
</html>