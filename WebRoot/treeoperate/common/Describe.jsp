<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet"%>
<%
String name=CharSet.nullToEmpty(request.getParameter("name")).toUpperCase();
String type=CharSet.nullToEmpty(request.getParameter("type")).toUpperCase();
String[] ownerName=name.split("\\.",2);
String sql="";
String headStr="Parameters";

if(ownerName[0].equals(name)){
	if(type.equals("TABLE")) {
		headStr="Columns";
		sql="select a.column_name, a.data_type||decode(a.char_col_decl_length,'',null,'('||a.char_col_decl_length||')') type, a.nullable, " +
			"a.data_default \\\"DEFAULT\\\", b.comments from user_tab_columns a, user_col_comments b " +
			"where a.table_name='" + ownerName[0] + "' " + 
			"and a.table_name = b.table_name " +
			"and a.column_name = b.column_name " +
			"order by a.column_id asc";
	} else {
		sql="select argument_name,data_type,in_out,default_value from user_arguments where package_name is null and object_name='"+ownerName[0]+"'";
	}
} else{
	if(type.equals("TABLE")) {
		headStr="Columns";
		sql="select a.column_name, a.data_type||decode(a.char_col_decl_length,'',null,'('||a.char_col_decl_length||')') type, a.nullable, " +
		"a.data_default \\\"DEFAULT\\\", b.comments from user_tab_columns a, user_col_comments b " +
		"where a.table_name='" + ownerName[1] + "' " + 
		"and a.onwer = '" + ownerName[0] + "' " +
		"and a.table_name = b.table_name " +
		"and a.column_name = b.column_name " +
		"and a.onwer = b.onwer " +
		"order by a.column_id asc";
	} else {
		sql="select argument_name,data_type,in_out,default_value from all_arguments where package_name is null and object_name='"+ownerName[1]+"' and owner='"+ownerName[0]+"'";
	}
}


//System.out.println(sql);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
   <link type="text/css" rel="StyleSheet" href="../../css/dhtmlxgrid.css" />
    <script type='text/javascript' src='../../dwr/interface/DbObjectBean.js'></script>
	<script type='text/javascript' src='../../dwr/engine.js'></script>
		<script type="text/javascript" src="../../js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="../../js/others.js"></script>
		<title><%=headStr %> of <%=name %></title>
  </head>
  
  <body ondragstart="return false" oncontextmenu="return false">
    <script type="text/javascript">
     	var sqls="<%=sql%>";
     <%
     if(type.equals("TABLE")) {
     %>	 
     	DbObjectBean.getOther2(sqls,['Name','Type','Nullable','Default','Comments'], showDataHtmlD);
     <%} else { %>
     	DbObjectBean.getOther2(sqls,['Parameter','Type','Mode','Default?'], showDataHtmlD);
     <%}%>
    </script>
  <div id='resultdiv' height="250px" style="background-color:white;overflow:hidden"></div>
  <br></body>
</html>
