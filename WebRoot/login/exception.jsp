<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>exception</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style>
			body {
				background: buttonface;
				margin: 2px;
				padding: 0px;
				font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
			}
	    </style>
	</head>
	<body ondragstart="return false" oncontextmenu="return false">
	    <p>We are sorry,but an exception has occured:
	    <p><s:property value="exception.message"/>
	    <p><s:property value="exceptionStack"/>	    
	    <p><input type="button" value="back" onclick="history.back()">
	</body>
</html>
