<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet" %>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>
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
	</style>
	<body ondragstart="return false" oncontextmenu="return false">
		    <table align="center" border="0" class="editorout" width="100%" cellpadding="0" cellspacing="0">
		       <tr>
		           <td>
		                <table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
		                   <tr><td align="left" valign="bottom" height="25" class="lagerfont"><br>Trigger</td></tr>
		                </table>
		           </td>
		       </tr>
		       <tr>
		           <td>
		              <table align="center" border="0" class="editorin" width="98%" cellpadding="0" cellspacing="0">
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Name</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;<input name="name" id="name" type="text" size="50"></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Fires</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;<select name="fires" id="fires">
		                    	<option value="before" selected>before</option>
		                    	<option value="after">after</option>
		                    	<option value="instead of">instead of</option>
		                    	</select>
		                    </td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Event</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;<select name="triggerEvent" id="triggerEvent">
		                    	<option value="insert">insert</option>
		                    	<option value="update">update</option>
		                    	<option value="delete">delete</option>
		                    </select>
		                    </td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Table or view</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;
		                    <select name="tableOrView" id="tableOrView">
		                    </select>
		                    </td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="20%" height="25" class="smallfont">Atement level?</td>
		                    <td align="left" width="80%" class="smallfont">&nbsp;<input name="atementLevel" id="atementLevel" type="checkbox"></td>
		                 </tr>
		                 <tr>
		                 	<td>
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
				function createTrigger() {
					var name = $('name').get('value');
					var fires = $('fires').get('value');
					var triggerEvent = $('triggerEvent').get('value');
					var tableOrView = $('tableOrView').get('value');
					var atementLevel = "0";
					if ($('atementLevel').get('checked')) atementLevel="1";
					//alert(returnType);
					window.close();
					window.opener.showNewObject('trigger',name,fires,triggerEvent,'New','<%=nodeType%>',tableOrView,atementLevel);
				}
				getTalbeAndView(tableOrView);
				</script>
		       <tr>
		           <td>
						<table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right" height="30" valign="top">
									<input type="submit" value="  ok  " onclick="createTrigger()">
									<input type="button" value="cannel" onclick="window.close()">
								</td>
							</tr>
						</table>
		           </td>
		       </tr>
		    </table>
	</body>
</html>