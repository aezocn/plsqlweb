<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>editor</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="StyleSheet" href="../css/cb2.css" />
		<link type="text/css" rel="StyleSheet" href="../css/grid.css" />
		<link type="text/css" rel="StyleSheet" href="../css/dhtmlxgrid.css" />
		<script type="text/javascript" src="../js/others.js"></script>
		<script type="text/javascript" src="../js/sqlhighlighter.js"></script>
		<script type="text/javascript" src="../js/mootools.js"></script>
		<script type="text/javascript" src="../js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="../js/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="../js/dhtmlxgridcell.js"></script>
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
	margin: 0px;
	padding: 0px;
	height: 100%;
	color: ButtonText;
	font: MessageBox;
	border: 0;
}

table {
	border: 2px groove;
}

p {
	margin: 0px;
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

.editor {
	font-family: "Courier New", Courier, mono;
	font-size: 12px;
	overflow: auto;
	background-color: #FFFFFF;
	scrollbar-3dlight-color: threedhighlight;
	scrollbar-darkshadow-color: threedshadow;
	scrollbar-highlight-color: buttonface;
	scrollbar-shadow-color: buttonface;
	WIDTH: 97%;
	HEIGHT: 100%;
	float: left;
}

.functions {
	color: #ff1493;
}

.keywords {
	color: blue;
}

.operators {
	color: #808080;
}

.BaisworkM {
	position: absolute;
	display: none;
	overflow: hidden;
	width: 150px;
	background-color: buttonface;
	padding: 1px;
	border: 2px outset;
	z-index: 100;
	font-size: 12px;
	filter: none;
	-moz-opacity: 1;
	font: Menu;
}

.BaisworkM ul {
	margin: 1px;
	border-bottom: buttonhighlight 1px solid;
	border-top: buttonshadow 1px solid;
}

.BaisworkM a {
	display: block;
	width: 100%;
	padding: 1px 2px 2px 15px;
	cursor: default;
	text-decoration: none;
	color: #000000;
	font: Menu;
}

.BaisworkM a:hover {
	background: highlight;
	color: highlighttext;
	font: Menu;
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
		<script type="text/javascript">
	/*
		 function doLayout(){
		 	 var bodyWidth = document.body.clientWidth * 0.97;
		 	 var bodyHeight = document.body.clientHeight;
		 	 var topHeight = document.getElementById('editortop').clientHeight;
		 	 var bottomHeight = document.getElementById('t_controlDiv').clientHeight;
		 	 document.getElementById('myTextarea').style.width = (bodyWidth-2)+'px';
		 	 document.getElementById('myTextarea').style.height = (bodyHeight-topHeight-bottomHeight-2)+'px';
		 }
		 if(window.frameElement != null) {
		     window.frameElement.onresize = doLayout;
		 }else {
		     window.onresize = doLayout;
		 }
		 */
	</script>

		<script type="text/javascript">
	
		var sqlhighlighter = new SQLHighlighter();
		var ctrlKeyDown = false;
		function doHighlight(event){
			if(ctrlKeyDown) return;
			var keyCode = event.keyCode;
			if(keyCode == 16 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 119) return;
			sqlhighlighter.highlight(document.getElementById('myTextarea'));
		}
		function detectCtrlKey(event){
			if(event.ctrlKey) ctrlKeyDown = true;
			else ctrlKeyDown = false;
		}
		
	</script>

	</head>
	<body ondragstart="return false" oncontextmenu="return false" onload="initOnload()">
		<div id="SQLWindow" style="width: 100%; HEIGHT: 100%;">
			<div id="BaisworkMenu" name="BaisworkMenu" class="BaisworkM"></div>

			<div id="myText"
				style="border: 2px; overflow: no; background-color: ButtonFace; WIDTH: 100%; HEIGHT: 35%">
				<div id="editortop" class="webfxGrid" style="width: 100%">
					<div id="myTextarea" class="editor" contentEditable
						onkeydown="detectCtrlKey(event)" onkeyup=""
						onclick='hiddenBaisworkMenu(event)'
						onmouseup='showBaisworkMenu("myTextarea","BaisworkMenu",event)'>
						<!-- onkeyup="doHighlight(event)" -->
					</div>
					<div
						style="border: 0px; width: 3%; height: 100%; background: ButtonFace; float: right; vertical-align: middle;">
						<table border="0" id="rightToolBar"
							style="background: ButtonFace;" cellspacing="1" height="100%">
							<tr>
								<td class="coolButton" height="48">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="coolButton" id="previouSql" onclick="execNextSql()">
									<img id='previouSqlButton' src="../images/previous_sql.gif"
										title="Previous SQL" alt="Previous SQL" align="absmiddle">
								</td>
							</tr>
							<tr>
								<td class="coolButton" id="nextSql"
									onclick="changePreviousSql('previouSql')">
									<img id='nextSqlButton' src="../images/next_sql.gif"
										title="Next SQL" alt="Next SQL" align="absmiddle">
								</td>
							</tr>
							<tr style="height: 85%;">
								<td class="coolButton" height="85%">
									&nbsp;
								</td>
							</tr>
						</table>

					</div>
				</div>
			</div>
			<div id="t_controlDiv"
				style="border: 1px; overflow: no; background-color: ButtonFace; height: 55%; width: 100%">

				<div id="foot_outputDiv1"
					style="overflow: no; background-color : ButtonFace; width: 100%">

					<table border="0" id="toolBar" style="background: ButtonFace;"
						cellspacing="3">
						<tr>
							<td class="coolButton">
								<img id='columnButton' src="../images/column.gif"
									align="absmiddle">
							</td>
							<td onclick="changeLock('lockButton')" id="lockButtonTd">
								<img id='lockButton' src="../images/lock.gif" title="Edit data"
									alt="Edit data" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id='insertRecordTd'
								onclick="insertRecord()">
								<img id='insertRecordButton' src="../images/insert_record.gif"
									title="Insert record" alt="Insert record" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id='deleteRecordTd'
								onclick="deleteRecord()">
								<img id='deleteRecordButton' src="../images/delete_record.gif"
									title="Delete record" alt="Delete record" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id='postChangesTd'
								onclick="postChangeRecord()">
								<img id='postChangesButton' src="../images/post_changes.gif"
									title="Post changes" alt="Post changes" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="fetchNextTd"
								onclick="getFYSql()">
								<img id='fetchNextButton' src="../images/fetch_next.gif"
									title="Fetch next page" alt="Fetch next page" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="fetchLastTd"
								onclick="getFYQSql()">
								<img id='fetchLastButton' src="../images/fetch_last.gif"
									title="Fetch last page" alt="Fetch last page" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="queryByExampleTd"
								onclick="queryByExample()">
								<img id='queryByExampleButton'
									src="../images/query_by_example.gif" title="Query By Example"
									alt="Query By Example" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="clearRecordTd"
								onclick="clearRecord()">
								<img id='clearRecordButton' src="../images/clear_record.gif"
									title="Clear record" alt="Clear record" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="singleRecordViewTd"
								onclick="changeRecordView()">
								<img id='singleRecordViewButton'
									src="../images/single_record_view.gif"
									title="Single Record View" alt="Single Record View"
									align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="nextRecord"
								onclick="changeNextRecordView()">
								<img id='nextRecordButton' src="../images/next_record.gif"
									title="Next record" alt="Next record" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="previousRecord"
								onclick="changePreviousRecordView()">
								<img id='previousRecordButton'
									src="../images/previous_record.gif" title="Previous record"
									alt="Previous record" align="absmiddle">
							</td>
							<td class="coolButtonDisabled" id="exportResultResultsTd"
								onclick="exportExcel('myTextarea');">
								<img id='exportResultResultsButton'
									src="../images/export_query_results.gif"
									title="Export Query Results..." alt="Export Query Results..."
									align="absmiddle">
							</td>
							<td class="coolButton" width="95%">
								&nbsp;
							</td>
						</tr>

					</table>

				</div>
				<div style="width: 100%; height: 95%; background-color: white"
					name="outResultDiv" id="outResultDiv"
					onclick="hiddenBaisworkMenu(event)"
					onmouseup="showBaisworkMenu('outResultDiv','outResultMenu',event)">
				</div>
				<div
					style="width: 100%; height: 100%; background-color: white; display: none;"
					name="changeOutResultDiv" id="changeOutResultDiv"
					onclick="hiddenBaisworkMenu(event)"
					onmouseup="showBaisworkMenu('outResultDiv','outResultMenu',event)">
				</div>
				<div id="outResultMenu" name="outResultMenu" class="BaisworkM"></div>

				<div id="foot_outputDiv"
					style="position:fixed; overflow: no; background-color : ButtonFace; bottom: 0px; _bottom: -1px; height: 30px; width: 100%">
					<table border="0" id="toolBar_2" style="background: ButtonFace;"
						cellspacing="1" width="100%">
						<tr align='left'>
							<td class="coolButtonDisabled_my">
								<img id='execIsRunButton' src="../images/exec_norun.gif"
									align="absmiddle">
							</td>
							<td tabIndex="1" onclick="changeAutorefresh('autorefreshButton')">
								<img id='autorefreshButton' src="../images/autorefresh.gif"
									title="Auto refresh timer (5 sec)"
									alt="Auto refresh timer (5 sec)" align="absmiddle">
							</td>
							<td class="coolButtonActiveHover" width="5%">
								1:1
							</td>
							<td class="coolButtonActiveHover" width="95%">
								<div id="footview" style="font-family: Arial, Courier, mono;">
									&nbsp;
								</div>
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<script type="text/javascript"> createBaisWorkMenu('BaisworkMenu');</script>
		<script>
			function rightToolBarButton() {		
							var rightcells = document.getElementById('rightToolBar').rows[1].cells;
							//alert(rightcells[0]);
							for (var i = 0; i < rightcells.length; i++)
							{
								createButton(rightcells[i]);
								//rightcells[i].setToggle(true);
								//alert(rightcells[i]);
								//cells1[i].setAlwaysUp(true)
							}
							rightcells[0].setEnabled(false);
							rightcells = document.getElementById('rightToolBar').rows[2].cells;
							//alert(rightcells[0]);
							for (var i = 0; i < rightcells.length; i++)
							{
								createButton(rightcells[i]);
								//rightcells[i].setToggle(true);
								//alert(rightcells[i]);
								//cells1[i].setAlwaysUp(true)
							}
							rightcells[0].setEnabled(false);
			}
			rightToolBarButton();
		</script>
		<script>
			function addMyTextAreaKeyDown() {
							$('myTextarea').addEvent('keydown', function(event) {
								mykeydown(event, 'myTextarea');
							});
			}
			addMyTextAreaKeyDown();
		</script>
		<script type="text/javascript"> createOutResultMenu('outResultMenu');</script>

		<script>
			function initToolBarButton() {
			var cells = document.getElementById('toolBar').rows[0].cells;
			for (var i = 1; i < cells.length-1; i++)
			{
				createButton(cells[i]);
				//cells1[i].setAlwaysUp(true)
			}

			//cells[1].setToggle(true);
			cells[7].setToggle(true); //Query By Example true
			cells[7].setEnabled(false); 
			cells[9].setEnabled(false); 
			cells[2].setEnabled(false);
			cells[3].setEnabled(false);
			cells[4].setEnabled(false);
			cells[5].setEnabled(false); //Fetch next
			cells[6].setEnabled(false); //Fetch last
			cells[8].setEnabled(false);
			cells[10].setEnabled(false);
			cells[11].setEnabled(false);
			cells[12].setEnabled(false);
			
			cells[9].setToggle(true); 
			//cells[5].setEnabled(false);
			//cells[6].setEnabled(false);
			// cells1[0].setValue(true, true);


			var cells1 = document.getElementById('toolBar_2').rows[0].cells;
			for (var i = 1; i <= 1; i++)
			{
				createButton(cells1[i]);
				//cells1[i].setAlwaysUp(true)
				
			}

			cells1[1].setToggle(true);
			// cells1[0].setValue(true, true);
			}
			initToolBarButton();
	</script>
	</body>
</html>