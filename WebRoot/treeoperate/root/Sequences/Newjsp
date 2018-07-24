<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>
<%@ page import="org.reddragonfly.iplsqldevj.DBUtilities"%>

<%
	String name = CharSet.nullToEmpty(request.getParameter("name"));
	String nodeType = CharSet.nullToEmpty(request.getParameter("type"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Create sequence</title>
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
		              <div id="tabBar" name="tabBar">
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
		                    </select>
		                    </td>
		                    <td align="right"  width="120" height="25" class="smallfont">Start with</td>
		                    <td align="left"  width="100" class="smallfont"><input name="start_with" id="start_with" type="text" size="18"></td>
		                 </tr>
		                 
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Name</td>
		                    <td align="left"  width="100" class="smallfont"><input name="name" id="name" type="text" size="18"></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Increment by</td>
		                    <td align="left"  width="100" class="smallfont"><input name="increment_by" id="increment_by" type="text" size="18"></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Min value</td>
		                    <td align="left"  width="100" class="smallfont"><input name="min_value" id="min_value" type="text" size="18"></td>
		                    <td align="right"  width="120" height="25" class="smallfont">Cache size</td>
		                    <td align="left"  width="100" class="smallfont"><input name="cache_size" id="cache_size" type="text" size="18"></td>
		                 </tr>
		                 <tr>
		                    <td align="right" width="120" height="25" class="smallfont">Max value</td>
		                    <td align="left"  width="100" class="smallfont"><input name="max_value" id="max_value" type="text" size="18"></td>
		                    <td align="right"  width="120" height="25" class="smallfont"></td>
		                    <td align="left"  width="150" class="smallfont"><input type="checkbox" id="cycle" name="cycle">Cycle<input type="checkbox" id="order" name="order">Order</td>
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
		              </div>
		              <div id="tabShowSql" name="tabShowSql" style="width: 100%; height: 210px; background-color: white; display: none;"></div>
		           </td>
		       </tr>

		       <tr>
		           <td>
		           		<table align="center" border="0" width="98%" cellpadding="0" cellspacing="0">
		           			<tr>
		           				<td>
		           					<br/>
		           				</td>
		           			</tr>
							<tr>
								<td align="left" height="30" valign="top">
									<input type="button" value=" Apply " onclick="objApply()">
									<input type="button" value="Refresh" onclick="">
									<input type="button" value=" Close " onclick="window.close()">
									<input type="button" value=" Help  " onclick="">
								</td>
								<td align="right" height="30" valign="top">
									<input type="button" value="View SQL" onclick="showDiv()">
								</td>
							</tr>
						</table>
		           </td>
		       </tr>
		    </table>
	</body>
	<script> 
		function getTmpSql() {
			var str="--Create sequence" + "<br/>";
			var cycle = "";
		    var order = "";
		    owner1 = $('owner').get('value');
			start_with1 = $('start_with').get('value');
			name1 = $('name').get('value');
			increment_by1 =$('increment_by').get('value');
			min_value1 = $('min_value').get('value');
			cache_size1 = $('cache_size').get('value');
			max_value1 = $('max_value').get('value');
			if ($('cycle').get('checked')) cycle = "cycle <br/>";
			if ($('order').get('checked')) order = "order";
			str = str + "create sequence " + owner1 + "."  + name1 + "<br/>"
					+ "minvalue " + min_value1 + "<br/>"
					+ "maxvalue " + max_value1 + "<br/>"
					+ "start with " + start_with1 + "<br/>"
					+ "increment by " + increment_by1 + "<br/>"
					+ "cache " + cache_size1 + "<br/>"
					+ cycle
					+ order ;
			return str;
		}
		function showDiv() {
			if (document.getElementById('tabBar').style.display == "") {
				document.getElementById('tabBar').style.display = "none";
				document.getElementById('tabShowSql').style.display = "";
				$('tabShowSql').set('html',getTmpSql()+";");
			} else {
				document.getElementById('tabBar').style.display = "";
				document.getElementById('tabShowSql').style.display = "none";
			}
		}
		function objApply() {
		    var tmpSql = getTmpSql();
			execOtherObject(tmpSql);
			if(opener.selectedNote.text == "Sequences" || opener.selectedNote.text == "Libraries" ) {		//这里可以加其他类型的OBJECT判断
				opener.tree.getSelected().reload();
			} else  {
				opener.tree.getSelected().getParent().reload();  //父节点刷新
			}
		}
		getUserName('owner');
		
	</script>
</html>