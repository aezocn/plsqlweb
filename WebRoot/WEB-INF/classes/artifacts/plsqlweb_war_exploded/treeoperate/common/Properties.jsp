<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet,org.reddragonfly.iplsqldevj.bean.UserBean"%>
<%@ page import="java.util.*"%>
<%
String name = CharSet.nullToEmpty(request.getParameter("name")).toUpperCase();
String type=CharSet.nullToEmpty(request.getParameter("type")).toUpperCase();
UserBean ub = (UserBean) session.getAttribute("user");
String userName=ub.getUsername().toUpperCase();



String[] ownerName=name.split("\\.",2);
String sql=" ";
String height="250px";

if(ownerName[0].equals(name)){
	if(type.equals("TABLE")) {
		height = "380px";
		sql="select '" + userName + "' OWNER, " +
		"a.table_name, a.tablespace_name, a.pct_free, a.ini_trans," +
		"a.max_trans, a.initial_extent, a.min_extents, a.max_extents," +
		"a.logging, a.backed_up, a.degree, a.instances, a.cache," +
		"a.table_lock, a.partitioned, a.temporary, a.secondary, " +
		"a.nested, a.buffer_pool, a.row_movement, a.global_stats," +
		"a.user_stats, a.skip_corrupt, a.monitoring, a.dependencies, a.compression" +
		" from user_tables a where a.table_name='" + ownerName[0] + "'";
	} else {
		sql="select '" + userName + "' OWNER, a.* from user_objects a where a.object_name='"+ownerName[0]+"' and a.object_type='"+type+"'";
	}
}else{
	if(type.equals("TABLE")) {
		height = "380px";
		sql="select a.owner, " +
		"a.table_name, a.tablespace_name, a.pct_free, a.ini_trans," +
		"a.max_trans, a.initial_extent, a.min_extents, a.max_extents," +
		"a.logging, a.backed_up, a.degree, a.instances, a.cache," +
		"a.table_lock, a.partitioned, a.temporary, a.secondary, " +
		"a.nested, a.buffer_pool, a.row_movement, a.global_stats," +
		"a.user_stats, a.skip_corrupt, a.monitoring, a.dependencies, a.compression" +
		" from dba_tables a where a.table_name='" + ownerName[1] + "' and owner='"+ownerName[0]+"'";
	} else {
		sql="select * from dba_objects a where a.object_name='"+ownerName[1]+"' and a.object_type='"+type+"' and owner='"+ownerName[0]+"'";
	}
}
//System.out.println(sql);
%>

<html>
  <head>
    <link type="text/css" rel="StyleSheet" href="../../css/dhtmlxgrid.css" />
    <script type='text/javascript' src='../../dwr/interface/DbObjectBean.js'></script>
	<script type='text/javascript' src='../../dwr/engine.js'></script>
		<script type="text/javascript" src="../../js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="../../js/others.js"></script>
		<title>Properties for <%=name %></title>
  </head>
  
  <body ondragstart="return false" oncontextmenu="return false">
    <script type="text/javascript">
     var sqls="<%=sql%>";
    DbObjectBean.getObject(sqls,['Property','value'], showDataHtmlP);
    
    </script>
  	<div id='resultdiv' width="100%" height="<%=height %>" style="background-color:white;overflow:hidden"></div>
  	<br>
  </body>
</html>