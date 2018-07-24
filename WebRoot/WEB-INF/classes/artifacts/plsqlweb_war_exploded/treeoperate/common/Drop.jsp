<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet" %>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>
<%@page import="org.reddragonfly.iplsqldevj.bean.Database"%>

<%
    String action = CharSet.nullToEmpty(request.getParameter("action"));
	String type = CharSet.nullToEmpty(request.getParameter("type"));
	String name = CharSet.nullToEmpty(request.getParameter("name"));
	String field = CharSet.nullToEmpty(request.getParameter("field"));
    if("exec".equals(action)){
       UserBean ub = (UserBean)session.getAttribute("user");
       Database db = ub.getDb();
       try{
          String execSQL = "drop " + type + " " + name;
          db.execSqlUpdate(execSQL);
          out.print("<script type=\"text/javascript\">opener.selectedNote.getParent().reload();window.close();</script>");
       }catch(Exception e){
          e.printStackTrace();
          String eMsg = e.getLocalizedMessage();
          if(eMsg.length() > 1 && eMsg.endsWith("\n")) eMsg = eMsg.substring(0,eMsg.length()-1);
          out.print("<script type=\"text/javascript\">opener.selectedNote.getParent().reload();alert(\""+eMsg+"\");window.close();</script>");
       }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Drop</title>
	    <base target='_self'>
	    <link type="text/css" rel="StyleSheet" href="../../css/cb2.css" />
	    <link type="text/css" rel="StyleSheet" href="../../css/xmenu.css" />
	</head>
	<style>
		body {
			background: buttonface;
			margin: 0px;
			padding: 0px;
			font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
		}
		
	</style>
	<body ondragstart="return false" oncontextmenu="return false">
		<form name="formx" method="post" action="">
		    <table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
		       <tr align="center">
		          <td>
		            <nobr>&nbsp;<img src="../../images/confirm.gif" align="absmiddle"> Drop <%=type %> <%=name %>?</nobr>
		          </td>
		       </tr>
		       <tr>
		          <td colspan="2" align="center">
		             <input type="button" value="&nbsp;&nbsp;Yes&nbsp;&nbsp;" onclick="location.href='Drop.jsp?type=<%=type %>&name=<%=name %>&field=<%=field %>&action=exec';">&nbsp;&nbsp;
		             <input type="button" value="&nbsp;&nbsp;No&nbsp;&nbsp;" onclick="window.close();">
		          </td>
		       </tr>
		       <tr>
		       	<td>
		       	<hr>
		       	</td>
		       </tr>
		    </table>
		    <table align="left" border="0" id="toolBar" style='background-color: buttonface;'>
				<tr align="left">
					<td colspan="2" align="left">
						<input align="left" type="checkbox" name="dropflag2" value="1">Don't show this message again
					</td>
				</tr>
	  		</table>
		</form>
		
	</body>
</html>