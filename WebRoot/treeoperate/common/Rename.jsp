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
          String new_name = CharSet.nullToEmpty(request.getParameter("new_name"));
          if(new_name.trim().equals("")) throw new Exception("new name could not be empty");
          String execSQL = "RENAME " + name + " to " + new_name;
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
	    <title>Rename</title>
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
		<form name="formx" method="post" action="Rename.jsp?action=exec&type=<%=type %>&name=<%=name %>&field=<%=field %>" onsubmit="return validate()">
		    <table align="center" border="0" class="editorout" width="100%" cellpadding="0" cellspacing="0">
		       <tr><td>New name</td></tr>
		       <tr><td><input type="text" name="new_name" size="50" value="<%=name %>" /></td></tr>
		       <tr>
		           <td>
		              <input type="submit" value="&nbsp;&nbsp;OK&nbsp;&nbsp;" />
		              <input type="button" value="Cancel" onclick="window.close()" />
		           </td>
		       </tr>
		    </table>
		</form>
	</body>
	<script type="text/javascript">
	    function validate(){
	       if(isEmpty(document.formx.new_name.value)){
	          alert("New name can not be empty");
	          return false;
	       }
	       return true;
	    }
	</script>
</html>