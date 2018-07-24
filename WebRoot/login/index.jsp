<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.reddragonfly.iplsqldevj.Struts2Utilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.action.login.LoginAction"%>
<%@ page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ page import="java.util.Collection"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>

<html>
	<head>
		<title>iPL/SQL Developer -- Oracle Login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style>
			body {
				background: buttonface;
				margin: 2px;
				padding: 0px;
				font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
			}
	    </style>
	</head>
	<body ondragstart="return false" oncontextmenu="return false">
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<div align="center">
			<h1>
				<b><i><font color='#0080FF' face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif" size='30'>i</font><font
					color='#76003A'
					face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif">PL/SQL
						Developer</font></i></b>
			</h1>
		</div>
		<div align="center" class="10p">
			<font face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif"><%=Utilities.getVersion() %></font>
		</div>
		<br />
		<form name="formx" method="post" action="<%=request.getContextPath()=="/"?"":request.getContextPath()%>/login/login.action" enctype="">
			<table align="center" border="0" width="300">
			    <tr>
			       <td width="100%">
			          <font color="red">
			             <%
			                String overdue = (String)request.getAttribute("overdue");
			                if(overdue != null) out.print("[" + overdue + "]<br>");
		                    Collection ctmessages =  Struts2Utilities.getActionMessages(request);
		                    Collection cterrors =  Struts2Utilities.getActionErrors(request);
		                    if(ctmessages != null && ctmessages.size() > 0) out.print(ctmessages + "<br>");
		                    if(cterrors != null && cterrors.size() > 0) out.print(cterrors + "<br>");
			                List usernameInfo = Struts2Utilities.getFieldErrors(request,"username");
			                List passwordInfo = Struts2Utilities.getFieldErrors(request,"password");
			                List databaseipInfo = Struts2Utilities.getFieldErrors(request,"databaseip");
			                List listenportInfo = Struts2Utilities.getFieldErrors(request,"listenport");
			                List servernameInfo = Struts2Utilities.getFieldErrors(request,"servername");
			                if(usernameInfo != null) out.print(usernameInfo + "<br>");
			                if(passwordInfo != null) out.print(passwordInfo + "<br>");
			                if(databaseipInfo != null) out.print(databaseipInfo + "<br>");
			                if(listenportInfo != null) out.print(listenportInfo + "<br>");
			                if(servernameInfo != null) out.print(servernameInfo + "<br>");
			                LoginAction laction = null;
			                ActionSupport as = Struts2Utilities.getActionSupport(request);
			                if(as !=null && as instanceof LoginAction) laction = (LoginAction)as;
			             %>
			          </font>
			       </td>
			    </tr>
				<tr>
					<td>
						<table align="center" width="100%" border="1">
							<tr>
								<td>
									<table border="0">
										<tr>
											<td align="right" width="35%">
												Username
											</td>
											<td height="25" width="65%">
												<input name="username" type="text" style="width: 180px;"
													size="25" value="<%=laction==null?"":laction.getUsername() %>">
											</td>
										</tr>
										<tr>
											<td align="right">
												Password
											</td>
											<td height="25">
												<input name="password" type="password" style="width: 180px;"
													size="25" value="<%=laction==null?"":laction.getPassword() %>">
											</td>
										</tr>
										<tr>
											<td align="right">
												DatabaseIP
											</td>
											<td height="25">
												<input name="databaseip" type="text" style="width: 180px;"
													size="25" value="<%=laction==null?"":laction.getDatabaseip() %>">
											</td>
										</tr>
										<tr>
											<td align="right">
												ListenPort
											</td>
											<td height="25">
												<input name="listenport" type="text" style="width: 180px;"
													size="25" value="<%=laction==null?"1521":laction.getListenport() %>">
											</td>
										</tr>
										<tr>
											<td align="right">
												Database
											</td>
											<td height="25">
												<input name="servername" type="text" style="width: 180px;"
													size="25" value="<%=laction==null?"":laction.getServername() %>">
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
												<%
												    if(laction != null){
												        out.print("<script type=\"text/javascript\">");
												        out.print("document.formx.connect.value = '"+laction.getConnect()+"';");
												        out.print("</script>");
												    }
												%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="submit" value="  ok  ">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="cannel" onClick="window.close()">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<table border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<p align="center" class="9p">
						<br />
						<%=Utilities.getTeam() %>
					</p>
				</td>
			</tr>
		</table>
	</body>
</html>

