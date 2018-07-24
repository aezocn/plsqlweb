package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbTablespaceBean extends DbBean {
	
	public static String TYPE = "tablespace";
	public static String ICON_INVALID = "dbimages/tablespaces.png";
	public static String ICON_VALID = "dbimages/tablespaces.png";
	public static String ICON_PARAMTER = "dbimages/valid_queue_tables.png";
	public static String ICON_COLUMN_CHAR = "dbimages/char.png";
	public static String ICON_COLUMN_DATE = "dbimages/date.png";
	public static String ICON_COLUMN_NUMBER = "dbimages/number.png";
	public static String ICON_COLUMN_BLOB = "dbimages/blob.png";
	public static String ICON_COLUMN_OBJ = "dbimages/obj.png";
	public static String ICON_INDEX = "dbimages/index.png";
	public static String PRIVILEGE_FLAG = "reddragonflyflag";
	
	protected static String[] FIELDS = 
	    {"Tables","Indexes","Clusters"};
	protected static String[] COLUMN_TYPE = 
	{"binary_double","binary_float","blob","clob","char","date","interval day to second","interval year to month","long","long raw","nclob","number","nvarchar2","raw","timestamp","timestamp with local time zone","timestamp with time zone","varchar2"};

	protected static String INDEX_PRI = "Indexes";
	
	protected String name = "";
	public DbTablespaceBean(String name){
		this.name = name;
	}
	
	public String getTreeXml() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		for(int i = 0;i < FIELDS.length;i++){
			//客户端脚本已经重写了onmouseover事件，事实上在客户端为onmouseup事件，这是出于鼠标右键的考虑
			sb.append("<tree text=\""+FIELDS[i]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[i]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[i]+"',event)\" />");
		}
		sb.append("</tree>");
		return sb.toString();
	}
	
	public String getFieldTreeXml(String fieldName) {
		// TODO Auto-generated method stub
		String[] field = fieldName.split("\\.",4);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		//System.out.println(name);
		if(fieldName.equals(FIELDS[0])) {
			sb.append(getTable(name));
		}
		if(fieldName.equals(FIELDS[1])) {
			sb.append(getIndex(name));
		}
		if(fieldName.equals(FIELDS[2])) {
			sb.append(getCluster(name));
		} 
		if(field[0].equals(INDEX_PRI)) {	//单独加入Index处理
			if (field.length > 1 && !(field[field.length-1].equals(PRIVILEGE_FLAG))) {
				String TempFieldName = fieldName + "." + PRIVILEGE_FLAG;
				String columnStr = "Columns";
				sb.append("<tree text=\""+columnStr+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+TempFieldName+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+columnStr+"',event)\" />");
			} else if (field.length > 1) {
				if (field[field.length-1].equals(PRIVILEGE_FLAG))	{
					if (field.length == 4) sb.append(getIndexID(field[1]+"."+field[2]));
					if (field.length == 3) sb.append(getIndexID(field[1]));
				}
			}
		}
		sb.append("</tree>");
		return sb.toString();
	}

	public String getMenuScript(){
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("myMenu.width = 200;");
		returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Properties\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Drop\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Drop','500px','120px');\"));");
		return returnVal.toString();
	}
	
	public String getFieldMenuScript(String fieldName){
		StringBuffer returnVal = new StringBuffer();
		if(fieldName.equals(FIELDS[0])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[1])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[2])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}
		return returnVal.toString();
	}
	
	public String getTable(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		String icon= ICON_PARAMTER;
		try{
			String obj = null;
			String subType = "TABLE";
			if(ub.getDbglobal()) obj = "all_tables";
			else obj = "all_tables";
			sql = "select owner,table_name from " + obj + " where tablespace_name='" + name + "' order by owner,table_name";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName+"&amp;field=\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+""+"',event)\" />");
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getIndex(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		String icon= ICON_PARAMTER;
		try{
			String obj = null;
			String subType = "TABLESPACE";
			if(ub.getDbglobal()) obj = "all_indexes";
			else obj = "all_indexes";
			sql = "select owner,index_name from " + obj + " where tablespace_name='" + name + "' order by owner,index_name";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				//icon = DbBeanManager.getChildMenuIcon(subType,CharSet.nullToEmpty(rs.getString(3)));
				icon = ICON_INDEX;
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				String field = INDEX_PRI + "." + objectName;
				//String field = objectName;
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+name+"&amp;field=" + field + "\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+ INDEX_PRI +"',event)\" />");
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getIndexID(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		
		try{
			String obj = null;
			String columnIcon = ICON_COLUMN_CHAR;
			String foreignTable = "";
			if(ub.getDbglobal()) {
				obj = "all_ind_columns";
			} else {
				obj = "all_ind_columns";
			}
			String[] field = name.split("\\.",2);
			if(field[0].equals(name)) {
				sql = "select utc.column_name,utc.data_type, " +
					"(select ucons.table_name from user_constraints ucons where (ucons.owner,ucons.constraint_name) in " +
					"(select cons.r_owner,cons.r_constraint_name  from user_constraints cons where cons.owner=ucc.index_owner and constraint_name=ucc.index_name)) foreigntable " +
					"from " + obj + " ucc, all_tab_columns utc where ucc.index_owner='" + ub.getUsername().toUpperCase() + "' and ucc.index_name='" + name + "' " +
					"and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name";
			} else {
				sql = "select utc.column_name,utc.data_type, " +
					"(select ucons.table_name from user_constraints ucons where (ucons.owner,ucons.constraint_name) in " +
					"(select cons.r_owner,cons.r_constraint_name  from user_constraints cons where cons.owner=ucc.index_owner and constraint_name=ucc.index_name)) foreigntable " +
					"from " + obj + " ucc, all_tab_columns utc where ucc.index_owner='" + field[0] + "' and ucc.index_name='" + field[1] + "' " +
					"and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name";
			}
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				columnIcon = getColumnTypeIcon(CharSet.nullToEmpty(rs.getString(2)));
				if(CharSet.nullToEmpty(rs.getString(3)).equals("")) foreignTable = "";
				else foreignTable = "(" + CharSet.nullToEmpty(rs.getString(3)) + ")";
				sb.append("<tree text=\"" + CharSet.nullToEmpty(rs.getString(1)) + foreignTable + "\" icon=\""+ columnIcon +"\"  openIcon=\""+ columnIcon +"\" />"); 
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getCluster(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		String icon= ICON_PARAMTER;
		try{
			String obj = null;
			String subType = "CLUSTER";
			if(ub.getDbglobal()) obj = "all_clusters";
			else obj = "all_clusters";
			sql = "select owner,cluster_name from " + obj + " where tablespace_name='" + name + "' order by owner asc, cluster_name asc";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName.replaceAll("#","%23")+"&amp;field=\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+""+"',event)\" />");
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public static String getColumnTypeIcon(String columnDataType) {
		String columnIcon=ICON_COLUMN_CHAR;
		if (columnDataType.equals(COLUMN_TYPE[0].toUpperCase()) || columnDataType.equals(COLUMN_TYPE[1].toUpperCase())
				|| columnDataType.equals(COLUMN_TYPE[2].toUpperCase()) || columnDataType.equals(COLUMN_TYPE[9].toUpperCase())
				|| columnDataType.equals(COLUMN_TYPE[13].toUpperCase())) {
			columnIcon = ICON_COLUMN_BLOB;
		} else if (columnDataType.equals(COLUMN_TYPE[3].toUpperCase()) || columnDataType.equals(COLUMN_TYPE[4].toUpperCase())
				|| columnDataType.equals(COLUMN_TYPE[8].toUpperCase()) || columnDataType.equals(COLUMN_TYPE[12].toUpperCase())
				|| columnDataType.equals(COLUMN_TYPE[17].toUpperCase())) {
			columnIcon = ICON_COLUMN_CHAR;
		} else if (columnDataType.equals(COLUMN_TYPE[11].toUpperCase())) {
			columnIcon = ICON_COLUMN_NUMBER;
		} else if (columnDataType.equals(COLUMN_TYPE[10].toUpperCase())) {
			columnIcon = ICON_COLUMN_OBJ;
		} else {
			columnIcon = ICON_COLUMN_DATE;
		}
		return columnIcon;
	}
	
}
