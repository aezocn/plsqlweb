package org.reddragonfly.iplsqldevj;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContextFactory;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.Database;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public abstract class DBUtilities {
	
	public static String[] getReturnTypes(){
		return new String[]{
			"varchar2","integer","number","date","boolean","long","long raw","clob","blob","binary_integer","&lt;table.column&gt;%type","&lt;table&gt;%rowtype"
		};
	}
	
	//静态方法，得到新建object的标准样式
	public static String getReturnObjContent(String name,String parameters,String returnType,
							String objType, String userName, String tableList, String statementLevlel) {
		String ret = "";
		objType = objType.replaceAll("_body", " body");
		objType = objType.replaceAll("_source", " source");
		objType = objType.replaceAll("_table", " table");
		objType = objType.replaceAll("_view", " view");
		objType = objType.replaceAll("_link", " link");
		//System.out.println(objType);
		if (objType.equals("function")) {
			ret = "create or replace function " + name + "(" + parameters + ") return " + returnType + " is" + "<br/>" +
			"  Result " + returnType + ";"+ "<br/>" +
			"begin"+ "<br/>" +
			"  "+ "<br/>" +
			"  return(Result);"+ "<br/>" +
			"end " + name + " ;";
		} else if (objType.equals("procedure")) {
			ret = "create or replace procedure " + name + "(" + parameters + ") is" + "<br/>" +
			"begin"+ "<br/>" +
			"  "+ "<br/>" +
			"end " + name + " ;";
		} else if (objType.equals("package")) {
			ret = "create or replace " + objType + " " + name + " is" + "<br/>" +
			"  -- Author  : " + userName + "" + "<br/>" +
			"  -- Created : " + DateUtilities.getNowTime() + "" + "<br/>" +
			"  -- Purpose : " + parameters + "" + "<br/>" +
			"  " + "<br/>" +
			"  -- Public type declarations" + "<br/>" +
			"  type &lt;TypeName&gt; is &lt;Datatype&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Public constant declarations" + "<br/>" +
			"  &lt;ConstantName&gt; constant &lt;Datatype&gt; := &lt;Value&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Public variable declarations" + "<br/>" +
			"  &lt;VariableName&gt; &lt;Datatype&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Public function and procedure declarations" + "<br/>" +
			"  function &lt;FunctionName&gt;(&lt;Parameter&gt; &lt;Datatype&gt;) return &lt;Datatype&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"end " + name + ";";
		} else if (objType.equals("package body")) {
			ret = "create or replace " + objType + " " + name + " is" + "<br/>" +
			"  " + "<br/>" +
			"  -- Private type declarations" + "<br/>" +
			"  type &lt;TypeName&gt; is &lt;Datatype&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Private constant declarations" + "<br/>" +
			"  &lt;ConstantName&gt; constant &lt;Datatype&gt; := &lt;Value&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Private variable declarations" + "<br/>" +
			"  &lt;VariableName&gt; &lt;Datatype&gt;;" + "<br/>" +
			"  " + "<br/>" +
			"  -- Function and procedure implementations" + "<br/>" +
			"  function &lt;FunctionName>(&lt;Parameter&gt; &lt;Datatype&gt;) return &lt;Datatype&gt; is" + "<br/>" +
			"    &lt;LocalVariable&gt; &lt;Datatype&gt;;" + "<br/>" +
			"  begin" + "<br/>" +
			"    &lt;Statement&gt;;" + "<br/>" +
			"    return(&lt;Result&gt;);" + "<br/>" +
			"  end;" + "<br/>" +
			"  " + "<br/>" +
			"begin" + "<br/>" +
			"  -- Initialization" + "<br/>" +
			"  &lt;Statement&gt;;" + "<br/>" +
			"end " + name + ";";
		} else if (objType.equals("type")) {
			ret = "create or replace " + objType + " " + name + " as object" + "<br/>" +
			"(" + "<br/>" +
			"  -- Author  : " + userName + "" + "<br/>" +
			"  -- Created : " + DateUtilities.getNowTime() + "" + "<br/>" +
			"  -- Purpose : " + parameters + "" + "<br/>" +
			"  " + "<br/>" +
			"  -- Attributes" + "<br/>" +
			"  &lt;Attribute&gt; &lt;Datatype&gt;," + "<br/>" +
			"  " + "<br/>" +
			"  -- Member functions and procedures" + "<br/>" +
			"  member procedure &lt;ProcedureName&gt;(&lt;Parameter&gt; &lt;Datatype&gt;)" + "<br/>" +
			")";
		} else if (objType.equals("type body")) {
			ret = "create or replace " + objType + " " + name + " is" + "<br/>" +
			"  " + "<br/>" +
			"  -- Member procedures and functions" + "<br/>" +
			"  member procedure &lt;ProcedureName&gt;(&lt;Parameter&gt; &lt;Datatype&gt;) is" + "<br/>" +
			"  begin" + "<br/>" +
			"    &lt;Statements&gt;;" + "<br/>" +
			"  end;" + "<br/>" +
			"  " + "<br/>" +
			"end;";
		} else if (objType.equals("trigger")) {
			String sLevel = "for each row ";
			if ("1".equals(statementLevlel)) sLevel = "";
			ret = "create or replace " + objType + " " + name + "<br/>"  + 
			"  " + parameters + " " + returnType + " on " +  tableList + "<br/>" +
			"  " + sLevel + "<br/>" +
			"declare" + "<br/>" +
			"  -- local variables here" + "<br/>" +
			"begin" + "<br/>" +
			"<br/>" +
			"end " + name + ";";
		} else if (objType.equals("java source")) {
			ret = "create or replace and compile " + objType + " named " + name + " as" + "<br/>" +
			"public class " + parameters + "" + "<br/>" +
			"{" + "<br/>" +
			"  public static void entry()" + "<br/>" +
			"  {" + "<br/>" +
			"  }" + "<br/>" +
			"}";
		} else if (objType.equals("view")) {
			ret = "create or replace " + objType + " " + name + " as" + "<br/>" +
			" select " + parameters + " " + "<br/>" +
			"   from " + tableList ;
			if (!returnType.equals("")) {
				ret = ret + "<br/>" +
				"      where " + returnType;
			}
		} else if (objType.equals("materialized view")) {
			ret = "create " + objType + " " + name + " as" + "<br/>" +
			" select " + parameters + " " + "<br/>" +
			"   from " + tableList ;
			if (!returnType.equals("")) {
				ret = ret + "<br/>" +
				"     " + returnType;
			}
		}
		return ret;
	}
	
	
	public static StringBuffer showObjectView(HttpSession session, String type, String name) {
		Database db = null;
		ResultSet rs = null;
		String[] ownerName=name.split("\\.",2);
		String querySql = "";
		String queryViewSql = "";
		StringBuffer ret = new StringBuffer();
		
		type = type.replaceAll("_BODY", " DODY");
		type = type.replaceAll("_SOURCE", " SOURCE");
		type = type.replaceAll("_TABLE", " TABLE");
		type = type.replaceAll("_VIEW", " VIEW");
		type = type.replaceAll("_LINK", " LINK");
		//System.out.println(type);
		if(ownerName[0].equals(name)) {
			String source = "user_source";
			String viewSource = "user_views";
			if (type.equals("MATERIALIZED VIEW")) viewSource = "user_mviews";
			// ---------------------------------------------------------------------
			// 获得某个存储过程、包、函数代码脚本
			// ---------------------------------------------------------------------
			querySql = 	"select decode( type||'-'||to_char(line,'fm99999')," +
						"'PACKAGE BODY-1', null,null) || decode(line,1,'create or replace ', '' ) || " +
						" text text	from " + source + "	where name = upper('" + name + "') " +
						" AND TYPE = '" + type + "' order by type, line";
			queryViewSql = "select text from " + viewSource + " where view_name = upper('" + name + "') ";
			if (type.equals("MATERIALIZED VIEW")) {
				queryViewSql = "select query from " + viewSource + " where mview_name = upper('" + name + "') ";
			}
		} else {
			String source = "dba_source";
			String viewSource = "dba_views";
			if (type.equals("MATERIALIZED VIEW")) viewSource = "dba_mviews";
			// ---------------------------------------------------------------------
			// 获得某个存储过程、包、函数代码脚本
			// ---------------------------------------------------------------------
			querySql = 	"select decode( type||'-'||to_char(line,'fm99999')," +
						"'PACKAGE BODY-1', null,null) || decode(line,1,'create or replace ', '' ) || " +
						" text text	from " + source + "	where name = upper('" + ownerName[1] + "') " +
						" AND OWNER = '" + ownerName[0] +"'" +
						" AND TYPE = '" + type + "' order by type, line";
			queryViewSql = "select text from " + viewSource + " where view_name = upper('" + ownerName[1] + "') " +
						" AND OWNER = upper('" + ownerName[0] +"') ";
			if (type.equals("MATERIALIZED VIEW")) {
				queryViewSql = "select query from " + viewSource + " where mview_name = upper('" + ownerName[1] + "') " +
				" AND OWNER = upper('" + ownerName[0] +"') ";
			}
		}
		
		//
		try {
			UserBean ub = (UserBean) session.getAttribute("user");
			db = ub.getDb();
			if (type.equals("VIEW") || type.equals("MATERIALIZED VIEW")) {
				rs = db.getRS(queryViewSql);
				if (type.equals("VIEW")) {
					ret.append("create or replace view " + name + " as<br/>");
				} else if (type.equals("MATERIALIZED VIEW")) {
					ret.append("create materialized view " + name + " as<br/>");
				}
			} else {
				rs = db.getRS(querySql);
			}
			while (rs.next()) {
				for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
				ret.append(rs.getString(i));
				ret.append("<br/>");
			}
			//System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
			Vector v = new Vector();
			v.add("ReddragonflyErrorFlag*");
			v.add(e.getMessage());
			
		} finally {
			if (rs != null) {
				//db.close(rs);
			}
			if (db != null) {
				// db.cleanup();
			}
		}
		return ret;
	}
		
	
}
