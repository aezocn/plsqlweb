<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>iPL/SQL Developer</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	
	<frameset rows="80,*" cols="*" frameborder="yes" border="1" framespacing="1" id='indexFrame'>
		<frame src="./top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
		<frameset cols="240,*" frameborder="yes" border="1" framespacing="1">
			<frameset rows="80%,*" cols="*" frameborder="yes" border="1" framespacing="1" id='leftMainFrame'>
				<frameset rows="25,*" cols="*" frameborder="yes" border="1" framespacing="1" id='leftMainFrameTop'>
					<frame src="../tree/lefttop.jsp" name="LeftTopFrame" scrolling="No" id="LeftTopFrame" title="LeftTopFrame" />
					<frame src="../tree/tree.jsp" scrolling="auto" name="leftFrame" id="leftFrame" title="leftFrame" />
				</frameset>
					<frame src="./windowlist.jsp" scrolling="auto" name="leftFrameList" id="leftFrameList" title="leftFrameList" />
			</frameset>
			<frameset rows="60,*" cols="*" frameborder="no" border="0" framespacing="0" id='mainFrame'>
				<frame src="./editortoolbar.jsp" scrolling="no" name="editorToolFrame" id="editorToolFrame" title="editorToolFrame" />
				<frame src="./editor.jsp" scrolling="auto" name="editorFrame" id="editorFrame" title="editorFrame" />
			</frameset>
		</frameset>
		<noframes>
			<body  ondragstart="return false" oncontextmenu="return false">
				<table width='100%' height='85%' align='center'>
					<tr>
						<td valign='middle'>
							<table align='center' cellpadding='4' class='tablefill'>
								<tr>
									<td width='100%' align='center' nowrap='nowrap'>
										您的浏览器不支持框架
										<br />
										<br />
										程序将带你到相关页面。
										<br />
										<br />
										(如果你不想等待,<a href='./login/login.action'>请点这里。</a>)
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</body>
		</noframes>
	</frameset>
</html>
