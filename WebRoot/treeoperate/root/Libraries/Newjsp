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
	    <title>Create library</title>
		<script type="text/javascript" src="../../../js/mootools.js"></script>
		<script type="text/javascript" src="../../../js/baisworksql.js"></script>
		<!--以下是dwr的必备js  -->
		<script type='text/javascript' src='../../../dwr/interface/BaisWorkBean.js'></script>
		<script type='text/javascript' src='../../../dwr/engine.js'></script>
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
		              	 <td>
		              	 	&nbsp;
		              	 </td>
		              	 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Owner</td>
		                    <td align="left"  width="100" class="smallfont">
		                    <select name="owner" id="owner">	
		                    </select></td>
		                 </tr>
		                 
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Name</td>
		                    <td align="left"  width="100" class="smallfont"><input name="name" id="name" type="text" size="20"></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Filespec</td>
		                    <td align="left"  width="200" class="smallfont"><input name="Filespec" id="Filespec" type="file" size="36"></td>
		                 </tr>
		                 <tr>
		                 	<td colspan="4">
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
	<script> 
		getUserName('owner');
	</script>
</html>