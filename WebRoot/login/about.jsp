<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.reddragonfly.iplsqldevj.Utilities"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="../css/xmenu.css">
		<script type="text/javascript" src="../js/mootools.js"></script>
		<title>About iPL/SQL Developer Team</title>
		<style>
html {
	height: 100%;
	background: ButtonFace;
}

body {
	font: 12px Tahoma;
	margin: 0px;
	background: #FFF;
}

#Container {
	width: 100%;
}

#PageBody {
	width: 100%;
	margin: 0px auto;
	height: 200px;
	background: #FFF;
}

#Sidebar {
	width: 160px;
	text-align: center;
	float: left;
	clear: left;
	overflow: hidden;
	margin-left: 5px;
	margin-top: 3px;
}

#MainBody {
	width: 62%;
	text-align: left;
	float: right;
	clear: right;
	overflow: hidden;
}

#Footer {
	width: 100%;
	margin: 0px auto;
	height: 50px;
	background: ButtonFace;
}
</style>

	</head>
	<body ondragstart="return false" oncontextmenu="return false">
		<!-- 整个布局容器 -->
		<div id="Container">
			<!-- 开始生成body页面 -->
			<div id="PageBody">
				<div id="Sidebar">
					<img src="../images/iplsqldeveloper.png"></img>
				</div>
				<div id="MainBody">
					<p>
					<h3>
						<b><i><font color='#0080FF'
								face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif"
								size="5">i</font><font color='#76003A'
								face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif">PL/SQL
								Developer</font>
						</i>
						</b>
					</h3>
					</p>
					<p>
						<%=Utilities.getVersion() %>
					</p>
					<p>
					<li>
						Cindyzh: <a href="mailto:zjxjin@126.com">zjxjin@126.com</a>
					</li>
					<li>
						Jeanwendy: <a href="mailto:jeanwendy@163.com">jeanwendy@163.com</a>
					</li>
					<li>
						Nanking: <a href="mailto:nanking@hotmail.com">nanking@hotmail.com</a>
					</li>
					<li>
						Phanrider: <a href="mailto:phanrider@126.com">phanrider@126.com</a>
					</li>
					</p>
					<p>
						<font color=#000000
							face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif">Copyright
						<a href="http://www.reddragonfly.org" target="_blank"><font
							color=#000000
							face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif">Reddragonfly
							&amp; Studio</font> </a> &copy; 2007-2011 </font>
						<br />

						Code By
						<a href="http://iplsqldeveloper.sf.net" target="_blank"> <font
							color=#000000
							face="Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif">iPlsqldeveloper
							Team</font>
						</a>
					</p>
				</div>
			</div>
			<!-- 开始生成页脚页面 -->
			<div id="Footer">
				<hr />
				<p align="left">
					&nbsp;&nbsp;
					<a href="http://www.reddragonfly.org" target="_blank"><img
							style="vertical-align: middle; border: 0;"
							src="../images/logo.gif"></img>
					</a>&nbsp;&nbsp;
					<a href="http://iplsqldeveloper.sf.net" target="_blank"><img
							style="vertical-align: middle; border: 0;" src="../images/sf.png"></img>
					</a>&nbsp;&nbsp;
					<input type="button" value="Close AboutUS"
						onClick="window.close()" style="vertical-align: middle;"></input>
				</p>
			</div>
		</div>
	</body>
</html>