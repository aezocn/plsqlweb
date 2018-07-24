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
	    <title>Create directory</title>
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
		.inputcss {
				background: buttonface;
		}
	</style>
	<body ondragstart="return false" oncontextmenu="return false">
		    <table align="center" border="0" class="editorout" height="100%" width="100%" cellpadding="0" cellspacing="0">
		       <tr>
		           <td>
		              <table align="left"  border="0" height="100%" width="500" cellpadding="0" cellspacing="0">
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Name</td>
		                    <td align="left"  width="100" class="smallfont">&nbsp;<input name="name" id="name" type="text" size="18"></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Path</td>
		                    <td align="left"  width="200" class="smallfont">&nbsp;<input name="Filespec" id="Filespec" type="file" size="36"></td>
		                 </tr>
		                 <tr>
		                 	<td colspan="4">
		                 	<br/>
		                 	<br/>
		                 	<br/>
		                 	</td>
		                 </tr>
		              </table>
		           </td>
		       </tr>
		       <script>
				function createTrigger() {
					var name = $('name').get('value');
					var first = $('first').get('value');
					var triggerEvent = $('triggerEvent').get('value');
					var atementLevel = $('atementLevel').get('value');
					var returnType = $('returnType').get('value');
					//alert(returnType);
					window.close();
					window.opener.showNewObject('trigger',name,parameters,returnType,'New','<%=nodeType%>','','');
				}
				</script>
		       <tr>
		           <td>
						<table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
							<tr>
								<td align="left" height="30" valign="top">
									<input type="button" value=" Apply " onclick="">
									<input type="button" value="Refresh" onclick="">
									<input type="button" value=" Close " onclick="window.close()">
									<input type="button" value=" Help  " onclick="">
								</td>
								<td align="right" height="30" valign="top">
									<input type="button" value="View SQL" onclick="">
								</td>
							</tr>
						</table>
		           </td>
		       </tr>
		    </table>
	</body>
</html>