<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<%	
    UserBean ub = (UserBean)session.getAttribute("user");
	//得到提交过来的isglobal
	String isglobal = request.getParameter("isglobal");
	if("1".equals(isglobal)){
	   ub.setDbglobal(true);
	}else{
	   ub.setDbglobal(false);
	}
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>tree</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<META HTTP-EQUIV="pragma"   CONTENT="no-cache">   
        <META HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">   
        <META HTTP-EQUIV="expires"   CONTENT="Mon,   23   Jan   1978   12:52:30   GMT">
		<link type="text/css" rel="stylesheet" href="../css/xtree2.links.css">
		<link type="text/css" rel="stylesheet" href="../css/xtree2.css">
		<link type="text/css" rel="stylesheet" href="../css/xmenu.windows.css">
		<script type="text/javascript" src="../js/xtree2.js"></script>
		<script type="text/javascript" src="../js/xloadtree2.js"></script>
		<script type="text/javascript" src="../js/cssexpr.js"></script>
		<script type="text/javascript" src="../js/xmenu.js"></script>
		<script type="text/javascript" src="../js/others.js"></script>
		<style>
			select{vertical-align:middle;height:20px;}
			form {margin:0;}
		</style>
		<script>
	 		function forsubmit() {
	 			document.formx.submit();
	 			//重新加载新的页面文件
				parent.leftFrame.location.replace("./tree.jsp");
	 		}
	 	</script>
	</head>
	
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="background-color: FFFFFF" oncontextmenu="return false">
	<div style="background-color: buttonface; overflow: no; height:25px;">
		<table width="100%" style="height:25px;" border='0' cellpadding="0" cellspacing="0" style="background-color: buttonface" align="center" valign="middle">
			<tr>
				<td valign="middle">
				   <form name="formx" id="formx" method="post" action="">
				        <select style="width:100%;font-size:11px;" name="isglobal" onchange="forsubmit()">
							<option value="1" <%=ub.getDbglobal()?"selected":"" %>>
								All objects&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</option>
							<option value="0" <%=ub.getDbglobal()?"":"selected" %>>
								My  objects&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</option>
						</select>
					</form>
				</td>
			</tr>
		</table>
	 </div>
	</body>
</html>
