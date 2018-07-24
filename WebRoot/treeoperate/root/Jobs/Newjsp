<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>
<%@ page import="org.reddragonfly.iplsqldevj.DBUtilities"%>

<%
	String name = CharSet.nullToEmpty(request.getParameter("name"));
	String nodeType = CharSet.nullToEmpty(request.getParameter("type"));
	UserBean ub = (UserBean) session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Create job</title>
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
		              <table align="left"  border="0" height="100%" width="580" cellpadding="0" cellspacing="0">
		              	 <tr>
		              	 <td>
		              	 	&nbsp;
		              	 </td>
		              	 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Submitter</td>
		                    <td align="left"  width="100" class="smallfont"><input name="submitter" id="submitter" type="text" size="18" value="<%=ub.getUsername().toUpperCase() %>" class="inputcss" readonly></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Priv user</td>
		                    <td align="left"  width="100" class="smallfont"><input name="priv_user" id="priv_user" type="text" size="18" class="inputcss" readonly></td>
		                 </tr>
		                 
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Job</td>
		                    <td align="left"  width="100" class="smallfont"><input name="job" id="job" type="text" size="18" class="inputcss" readonly></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Schema user</td>
		                    <td align="left"  width="100" class="smallfont"><input name="schema_user" id="schema_user" type="text" size="18" class="inputcss" readonly></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" rowspan="3" class="smallfont">What</td>
		                    <td align="left"  width="100" rowspan="3" class="smallfont"><textarea rows="8" name="what" id="what" cols="16"></textarea></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Last date</td>
		                    <td align="left"  width="100" class="smallfont"><input name="last_date" id="last_date" type="text" size="18" class="inputcss" readonly></td>
		                 </tr>
		                 <tr>
		                    <td align="right"  width="120" height="25" class="smallfont">This date</td>
		                    <td align="left"  width="100" class="smallfont"><input name="this_date" id="this_date" type="text" size="18" class="inputcss" readonly></td>
		                 </tr>
		                 <tr>
		                    <td align="right"  width="120" height="25" class="smallfont">Total time(sec)</td>
		                    <td align="left"  width="100" class="smallfont"><input name="total_time" id="total_time" type="text" size="18" class="inputcss" value="0" readonly></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Next date</td>
		                    <td align="left"  width="100" class="smallfont"><input name="next_date" id="next_date" type="text" size="18"></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Failures</td>
		                    <td align="left"  width="100" class="smallfont"><input name="failures" id="failures" type="text" size="18" class="inputcss" value="0" readonly></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Interval</td>
		                    <td align="left"  width="100" class="smallfont"><input name="interval" id="interval" type="text" size="18"></td>
		                    <td align="right"  width="120" height="25" class="smallfont">NLS environment</td>
		                    <td align="left"  width="100" class="smallfont"><input name="environment" id="environment" type="text" size="18" class="inputcss" readonly></td>
		                 </tr>
		                <tr>
		                    <td align="right" width="100" height="25" class="smallfont"></td>
		                    <td align="left"  width="100" class="smallfont" colspan="3"><input type="checkbox" name="broken" value="">Broken</td>
		                 </tr>
		                 <tr>
		                 	<td colspan="4">
		                 	<br/>
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