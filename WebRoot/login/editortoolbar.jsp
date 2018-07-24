<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>editor</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="StyleSheet" href="../css/cb2.css" />
		<script type="text/javascript" src="../js/others.js"></script>
		<script type="text/javascript" src="../js/mootools.js"></script>
		<script type="text/javascript" language="JavaScript1.5"
			src="../js/ieemu.js"></script>
		<script type="text/javascript" src="../js/cb2.js"></script>
		<script type="text/javascript" src="../js/baisworksql.js"></script>
		<!--以下是dwr的必备js  -->
		<script type='text/javascript' src='../dwr/interface/BaisWorkBean.js'></script>
		<script type='text/javascript' src='../dwr/engine.js'></script>
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
	<body ondragstart="return false" oncontextmenu="return false">
		<div id="myTextToolbar"
			style="overflow: no background-color :     ButtonFace; width: 100%">
			<table border="0" id="topToolBar" style="background: ButtonFace;"
				cellspacing="3">
				<tr>
					<td class="coolButton" id="newTd" onclick="parent.parent.leftFrameList.createNewSql('SQL','myTextarea');">
						<img id='columnButton' src="../images/new.gif"  title="New" alt="New" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','open');">
						<img id='columnButton' src="../images/open.gif" title="Open" alt="Open" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','saveas');">
						<img id='columnButton' src="../images/save.gif" title="Save" alt="Save" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','print');">
						<img id='columnButton' src="../images/printpor.gif" title="Print portrait" alt="Print portrait" 
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','print');">
						<img id='columnButton' src="../images/printland.gif" title="Print landscape" alt="Print landscape" 
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','undo');">
						<img id='columnButton' src="../images/undo.gif" title="Undo (Ctrl+Z)" alt="Undo (Ctrl+Z)" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','redo');">
						<img id='columnButton' src="../images/redo.gif" title="Redo (Ctrl+Y)" alt="Redo (Ctrl+Y)" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','cut');">
						<img id='columnButton' src="../images/cut.gif" title="Cut (Ctrl+X)" alt="Cut (Ctrl+X)" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','copy');">
						<img id='columnButton' src="../images/copy.gif" title="Copy (Ctrl+C)" alt="Copy (Ctrl+C)" align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','paste');">
						<img id='columnButton' src="../images/paste.gif" title="Paste (Ctrl+V)" alt="Paste (Ctrl+V)" align="absmiddle">
					</td>
					<td class="coolButton" >
						<img id='columnButton' src="../images/findorreplace.gif" title="Find & Replace (Ctrl+F)" alt="Find & Replace (Ctrl+F)"
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','indent');">
						<img id='columnButton' src="../images/indent.gif" title="Selection Indent" alt="Selection Indent" 
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="execSysCommand('myTextarea','outdent');">
						<img id='columnButton' src="../images/unindent.gif" title="Selection Unindent" alt="Selection Unindent" 
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="addComment('myTextarea');">
						<img id='columnButton' src="../images/comment.gif" title="Selection Comment" alt="Selection Comment" 
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="addComment('myTextarea');">
						<img id='columnButton' src="../images/uncomment.gif" title="Selection Uncomment" alt="Selection Uncomment" 
							align="absmiddle">
					</td>
					<td class="coolButton" width="95%">
						&nbsp;
					</td>
					<script>
					
							var toprightcells0 = document.getElementById('topToolBar').rows[0].cells;
							
							for (var i = 0; i < 15; i++)
							{
								createButton(toprightcells0[i]);
								//rightcells[i].setToggle(true);
								//alert(rightcells[i]);
								//cells1[i].setAlwaysUp(true)
							}
							//toprightcells0[1].setEnabled(false);
							//toprightcells0[2].setEnabled(false);
							//toprightcells0[3].setEnabled(false);
							//toprightcells0[4].setEnabled(false);
							
					
				</script>
				</tr>
				<tr>
					<td class="coolButton" onclick="parent.editorFrame.relogon();">
						<img id='columnButton' src="../images/logon.gif" title="Log on" alt="Log on"
						 align="absmiddle">
					</td>
					<td class="coolButton" id="executeTd" onclick="parent.editorToolFrame.executeRun('myTextarea');">
						<img id='columnButton' src="../images/execute.gif" title="Execute (F8)" alt="Execute (F8)"
							align="absmiddle">
					</td>
					<td class="coolButton" id="breakTd" onclick="parent.editorToolFrame.breakRun('myTextarea');">
						<img id='columnButton' src="../images/break.gif" title="Break (F9)" alt="Break (F9)"
						align="absmiddle">
					</td>
					<td class="coolButton" id="commitTd" onclick="parent.editorToolFrame.commit()">
						<img id='columnButton' src="../images/commit.gif" title="Commit (F10)" alt="Commit (F10)"
							align="absmiddle">
					</td>
					<td class="coolButton" id="rollbackTd" onclick="parent.editorToolFrame.rollback();">
						<img id='columnButton' src="../images/rollback.gif" title="Rollback (F12)" alt="Rollback (12)"
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="logout();">
						<img id='columnButton' src="../images/logout.gif" title="Log out" alt="Log out"
							align="absmiddle">
					</td>
					<td class="coolButton" onclick="aboutUS();">
						<img id='columnButton' src="../images/aboutus.gif" title="About US" alt="About US"
							align="absmiddle">
					</td>
					<td class="coolButton" colspan="8" width="5%">
						&nbsp;
					</td>
				</tr>
				<script>
					
							var toprightcells = document.getElementById('topToolBar').rows[1].cells;
							
							for (var i = 0; i < 7; i++)
							{
								createButton(toprightcells[i]);
								//rightcells[i].setToggle(true);
								//alert(rightcells[i]);
								//cells1[i].setAlwaysUp(true)
							}
							toprightcells[2].setEnabled(false);
							toprightcells[3].setEnabled(false);
							toprightcells[4].setEnabled(false);
							
					
				</script>
			</table>
			<div id="closeWindowList" style="position: absolute; right: 3px; top: 3px" onmousedown="changeWindowListClassDown('windowListImg', event)" onmouseup="closeWindow('windowListImg', event)" ><img id='windowListImg' src="../images/close_window.gif" title="Close Window" alt="Close Window"
							align="absmiddle"></div>
		</div>
	</body>
</html>
