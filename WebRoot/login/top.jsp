<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<%
UserBean ub = (UserBean) session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<META content="text/html; charset=utf-8" http-equiv="Content-Type">
		<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<link type="text/css" rel="stylesheet" href="../css/xmenu.css">
		<script type="text/javascript" src="../js/others.js"></script>
		<script type="text/javascript" src="../js/mootools.js"></script>
		<!--以下是dwr的必备js  -->
		<script type='text/javascript' src='../dwr/interface/DbObjectBean.js'></script>
		<script type='text/javascript' src='../dwr/engine.js'></script>
		<style>
			.topSS {
				background-repeat: repeat;background-image: url(../images/log.png)!important;background-image:url();
			}
			*html .topSS {filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="../images/log.png");
			}
		</style>
	</head>
	
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		style="background-color: buttonface" ondragstart="return false" oncontextmenu="return false">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			height="80">
			<tr>
				<td width="360" rowspan="2" class="topSS">
					<br />
					<br />
					<br />
					<br />
					<div align="left">
						<font color="#666666" face="MS Sans Serif"><b>&nbsp;<%=Utilities.getVersion()%></b>
						</font>
					</div>
				</td>
				<td align="right">
					<br />
					<span class="style1"> <%=ub.getUsername()%><%="@" + ub.getDatabaseip() + ":" + ub.getListenport()
					+ ":" + ub.getServername()%>
						<br /> <%=ub.getDbversion()%><br /> <%=Utilities.getTeam()%> </span>
				</td>
			</tr>
		</table>
	</body>
</html>
<script language="javascript" id="keepSessionValid">
</script>
<script language="javascript">

	DbObjectBean.getUserObject(getUserObject);
	
	keepSessionValid();
 	function keepSessionValid()
  	{
  		window.document.getElementById("keepSessionValid").src="../js/keepSessionValid.js?Rand="+Math.random();
		window.setTimeout("keepSessionValid()",1200000); //20分钟执行一次
	}

</script>