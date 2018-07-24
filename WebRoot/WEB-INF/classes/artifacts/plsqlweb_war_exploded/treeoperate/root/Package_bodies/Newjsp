<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet" %>
<%@page import="org.reddragonfly.iplsqldevj.DBUtilities"%>

<%
	String name = CharSet.nullToEmpty(request.getParameter("name"));
	String nodeType = CharSet.nullToEmpty(request.getParameter("type"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Template Wizard</title>
	    <script type="text/javascript" src="../../../js/mootools.js"></script>
	</head>
	<style>
		body {
			background: buttonface;
			margin: 0px;
			padding: 0px;
			font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
		}
		.lagerfont{
		    font-size: 14px;
		}
		.smallfont{
		    font-size: 12px;
		}
		.editorout {
		    border-left: 1px ridge #999999; 
		    border-right: 1px ridge #999999; 
		    border-top: 1px ridge #999999; 
		    border-bottom: 1px ridge #999999;
		}
		.editorin { 
		    border-left: 1px inset #999999; 
		    border-right: 1px inset #999999; 
		    border-top: 1px inset #999999; 
		    border-bottom: 1px inset #999999;
		}
	</style>
	<body>
		    <table align="center" border="0" class="editorout" width="100%" cellpadding="0" cellspacing="0">
		       <tr>
		           <td>
		                <table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
		                   <tr><td align="left" valign="bottom" height="25" class="lagerfont">Package Body</td></tr>
		                </table>
		           </td>
		       </tr>
		       <tr>
		           <td>
		              <table align="center" border="0" class="editorin" width="98%" cellpadding="0" cellspacing="0">
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Name</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;<input name="name" id="name" type="text" size="56"></td>
		                 </tr>
		                 <tr>
		                 	<td>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	</td>
		                 </tr>
		              </table>
		           </td>
		       </tr>
		       <tr>
		          <td><hr></td>
		       </tr>
		       <script>
				function createPackb() {
					var name = $('name').get('value');
					window.close();
					window.opener.showNewObject('package_body',name,'','','New','<%=nodeType%>','','');
				}
				</script>
		       <tr>
		           <td>
						<table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right" height="30" valign="top">
									<input type="submit" value="  ok  " onclick="createPackb()">
									<input type="button" value="cannel" onclick="window.close()">
								</td>
							</tr>
						</table>
		           </td>
		       </tr>
		    </table>
	</body>
</html>