<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet" %>
<%@page import="org.reddragonfly.iplsqldevj.DBUtilities"%>

<%
	String name = CharSet.nullToEmpty(request.getParameter("name"));
	if ("".equals(name)) name="Name";
	String parameters = CharSet.nullToEmpty(request.getParameter("parameters"));
	String tablelist = CharSet.nullToEmpty(request.getParameter("tablelist"));
	String returnType = CharSet.nullToEmpty(request.getParameter("returnType"));
	String objType = CharSet.nullToEmpty(request.getParameter("objType"));
	String statementLevlel = CharSet.nullToEmpty(request.getParameter("statementLevlel"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>View</title>
	    <link type="text/css" rel="StyleSheet" href="../../css/cb2.css" />
	    <link type="text/css" rel="StyleSheet" href="../../css/tab.winclassic.css" />
	    <script type="text/javascript" src="../../js/tabpane.js"></script>
	    <script type="text/javascript" src="../../js/mootools.js"></script>
	    <script type="text/javascript" language="JavaScript1.5"
			src="../../js/ieemu.js"></script>
		<script type="text/javascript" src="../../js/cb2.js"></script>
	</head>
	<style>
		html,body {
			background: ButtonFace;
			margin: 0px;
			padding: 0px;
			font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
			overflow: auto;
			height: 100%;
		}
		.lagerfont{
		    font-size: 12px;
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
		.editor {
			font-family: "Courier New", Courier, mono;
			font-size: 12px;
			overflow: auto;
			background-color: #FFFFFF;
			scrollbar-3dlight-color: threedhighlight;
			scrollbar-darkshadow-color: threedshadow;
			scrollbar-highlight-color: buttonface;
			scrollbar-shadow-color: buttonface;
			WIDTH: 100%;
			height:100%;
			float: left;
			
		}
		.dynamic-tab-pane-control .tab-page {
			height:		610px;
			
		}

		.dynamic-tab-pane-control .tab-page .dynamic-tab-pane-control .tab-page {
			height:		100px;
		}
		.dynamic-tab-pane-control h2 {
			text-align:	center;
			width:		auto;
		}

		.dynamic-tab-pane-control h2 a {
			display:	inline;
			width:		auto;
		}

		.dynamic-tab-pane-control a:hover {
			background: transparent;
		}
		p {
			margin: 0px;
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
	<body ondragstart="return false" oncontextmenu="return false" onload="parent.editorToolFrame.initOnload()">
		<div id="SQLWindow" style="min-height:100%; _height:100%; position: relative; border: 2px; overflow: no; background-color: ButtonFace;">
			<div class="tab-pane" id="tabPanel" style=" min-height:90%; _height:90%;">
				<div class="tab-page" id="tabpage_1" style=" min-height:100%; _height:100%;">
				<h2 class="tab" id="tabTitle"><img style="border:none" id='objIcoId' src='' align='absmiddle' /><span id="tmpImg" style="display:none"></span> <span id='objTitle'><%=name %></span></h2>
					<div id="myTextarea" class="editor" contentEditable>
					<%=DBUtilities.getReturnObjContent(name, parameters, returnType, objType, request.getRemoteUser(),tablelist, statementLevlel) %>
					</div>
				</div>
			</div>
		
			<div id="foot_outputDiv"
					style="position: relative; overflow: no background-color : ButtonFace; width: 100%">
					<table border="0" id="toolBar_2" style="background: ButtonFace;"
						cellspacing="1" width="100%">
						<tr align='left'>
							<td class="coolButtonDisabled_my">
								<img id='execIsRunButton' src="../../images/exec_norun.gif" align="absmiddle" />
							</td>
							<td tabIndex="1" class="coolButtonActiveHover" onclick="">
								&
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
		<script>
			function addMyTextAreaKeyDown() {
							$('myTextarea').addEvent('keydown', function(event) {
								parent.editorToolFrame.mykeydown(event, 'myTextarea');
							});
			}
			addMyTextAreaKeyDown();
			
			
			function initViewFootButton() {
				var cells1 = document.getElementById('toolBar_2').rows[0].cells;
				for (var i = 1; i <= 1; i++)
				{
					createButton(cells1[i]);
					//cells1[i].setAlwaysUp(true)
				}
				cells1[1].setToggle(true);
				
				cells1[1].setValue(true, true);
			}
			initViewFootButton();
			//setupAllTabs();
			function changeObjIco() {
				imgPath = "../../tree/dbimages/";
				imgIco = "";
				if ("<%=objType %>" == "function") {
					imgIco = "valid_funs.png";
				} else if ("<%=objType %>" == "procedure") {
					imgIco = "valid_prcs.png";
				} else if ("<%=objType %>" == "package") {
					imgIco = "valid_pkgs.png";
				} else if ("<%=objType %>" == "package_body") {
					imgIco = "valid_pkgs_b.png";
				} else if ("<%=objType %>" == "type") {
					imgIco = "valid_types.png";
				} else if ("<%=objType %>" == "type_body") {
					imgIco = "valid_types_b.png";
				} else if ("<%=objType %>" == "trigger") {
					imgIco = "ena_trigers.png";
				} else if ("<%=objType %>" == "java_source") {
					imgIco = "valid_javas.png";
				} else if ("<%=objType %>" == "view") {
					imgIco = "valid_views.png";
				} else if ("<%=objType %>" == "materialized_view") {
					imgIco = "mat_views.png";
				}
				$('tmpImg').set('text',imgPath + imgIco);
				$('objIcoId').set('src', imgPath + imgIco);
				//alert($('tmpImg').get('text'));
			}
			changeObjIco();
		</script>
	</body>
</html>