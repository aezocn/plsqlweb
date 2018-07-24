<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<%
UserBean ub = (UserBean) session.getAttribute("user");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<META content="text/html; charset=utf-8" http-equiv="Content-Type">
		<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<link type="text/css" rel="StyleSheet" href="../css/cb2.css" />
		<script type="text/javascript" src="../js/others.js"></script>
		<script type="text/javascript" src="../js/mootools.js"></script>
		<script type="text/javascript" language="JavaScript1.5"	src="../js/ieemu.js"></script>
		<script type="text/javascript" src="../js/cb2.js"></script>
		<style>
* {
	box-sizing: border-box;
	-moz-box-sizing: border-box;
}

html {
	height: 100%;
	background: ButtonFace;
}

body {
	overflow: auto;
	margin: 0;
	padding: 0;
	height: 100%;
	color: ButtonText;
	font: MessageBox;
	border: 0;
}

table {
	border: 2px groove;
}

p {
	margin: 10px;
}

/* Overriding style for one of the tables */
#blueTable {
	background-color: rgb(120, 172, 255);
}

#blueTable td {
	background-color: Transparent;
}

#blueTable .coolButtonUpDisabled,#blueTable .coolButtonActiveDisabled,#blueTable .coolButtonDisabled
	{
	color: rgb(60, 86, 128);
}

#blueTable .coolButtonUpDisabled .coolButtonDisabledContainer,#blueTable .coolButtonActiveDisabled .coolButtonDisabledContainer,#blueTable .coolButtonDisabled .coolButtonDisabledContainer
	{
	background: rgb(60, 86, 128);
}

#blueTable .coolButtonUpDisabled .coolButtonDisabledContainer .coolButtonDisabledContainer,#blueTable .coolButtonActiveDisabled .coolButtonDisabledContainer .coolButtonDisabledContainer,#blueTable .coolButtonDisabled .coolButtonDisabledContainer .coolButtonDisabledContainer
	{
	background: Transparent;
}
</style>

		<style disabled="disabled" id="mozGrooveCSS">
/* Mozilla does not understand disabled stylesheets but
			 * for forward compatibility I'll enabled it using js
			 * below
			 */
table {
	border: 2px groove ButtonFace;
}
</style>
		<script language="JavaScript1.5">
			if (window.moz == true) document.getElementById("mozGrooveCSS").removeAttribute("disabled");
		</script>

	</head>
	
	<body 
		style="background-color: buttonface" ondragstart="return false" oncontextmenu="return false">
		<div style="border-top:3px dashed #bbbbbb;height: 1px;overflow:hidden"></div>
		<div id="hiddenDiv" style="display:none">
			<!-- 考虑到性能问题，暂只最大只支持9个窗口历史数据记录 -->
			<div id="SQLWindowTemp"></div>
			<div id="SQLWindow0"></div>
			<div id="SQLWindow1"></div>
			<div id="SQLWindow2"></div>
			<div id="SQLWindow3"></div>
			<div id="SQLWindow4"></div>
			<div id="SQLWindow5"></div>
			<div id="SQLWindow6"></div>
			<div id="SQLWindow7"></div>
			<div id="SQLWindow8"></div>
		</div>
		<div id="windowList" style="overflow: no background-color :     ButtonFace; width: 100%">
			<table width="100%" id="windowListBar" border="0" cellspacing="1" cellpadding="0">
				
			</table>
		</div>
		<script>
			//初始化一个按钮
			createWindowList("SQL","initnew");
		</script>
	</body>
</html>