package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbMaterializedViewBean extends DbBean {
	
	public static String TYPE = "materialized view";
	public static String ICON_INVALID = "dbimages/mat_views.png";
	public static String ICON_VALID = "dbimages/mat_views.png";
	public static String ICON_PARAMTER = "dbimages/parameter.png";
	public static String ICON_KEY = "dbimages/key.png";
	public static String ICON_COLUMN_CHAR = "dbimages/char.png";
	public static String ICON_COLUMN_DATE = "dbimages/date.png";
	public static String ICON_COLUMN_NUMBER = "dbimages/number.png";
	public static String ICON_COLUMN_BLOB = "dbimages/blob.png";
	public static String ICON_COLUMN_OBJ = "dbimages/obj.png";
	public static String ICON_INDEX = "dbimages/index.png";
	public static String PRIVILEGE_FLAG = "flag";
	
	protected static String[] FIELDS = 
	    {"Columns","References","Referenced by","Synonyms","Granted to users","Granted to roles"};
	protected static String[] COLUMN_TYPE = 
	{"binary_double","binary_float","blob","clob","char","date","interval day to second","interval year to month","long","long raw","nclob","number","nvarchar2","raw","timestamp","timestamp with local time zone","timestamp with time zone","varchar2"};
	protected static String FIELDS_PRI = "Primary Key";
	
	protected String name = "";
	public DbMaterializedViewBean(String name){
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
		//String[] field = fieldName.split("\\.",4);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		//System.out.println(name);
		if(fieldName.equals(FIELDS[0])) {
			sb.append(getParameter(name));
		}
		if(fieldName.equals(FIELDS[1])) {
			sb.append(getReference(name));
		}
		if(fieldName.equals(FIELDS[2])) {
			sb.append(getReferencedBy(name));
		}
		if(fieldName.equals(FIELDS[3])) {
			sb.append(getSynonym(name));
		}
		if(fieldName.equals(FIELDS[4])) {
			sb.append(getGrantedToUser(name));
		}
		if(fieldName.equals(FIELDS[5])) {
			sb.append(getGrantedToRole(name));
		}
		sb.append("</tree>");
		return sb.toString();
	}

	public String getMenuScript(){
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("myMenu.width = 200;");
		returnVal.append("myMenu.add(new WFXMI(\"New...\", \"javascript:showRoot('"+TYPE+"','"+name+"','Materialized_views','New...','550px','300px');\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Properties\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Properties','500px','312px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Describe\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Describe','500px','312px');\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"View\",\"javascript:showViewObject('"+TYPE+"','"+name+"','','View');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit\",\"javascript:showEditObject('"+TYPE+"','"+name+"','Materialized_views','Edit');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Drop\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Drop','500px','120px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Browse\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Recompile\", \"javascript:recompile('"+TYPE+"','"+name+"','0');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Recompile referencing objects\"));");
		returnVal.append("var sub1 = new WebFXMenu;");
		returnVal.append("sub1.width = 100;");
		returnVal.append("sub1.add(new WFXMI(\"DDL\"));");
		returnVal.append("sub1.add(new WFXMI(\"XML\"));");
		returnVal.append("myMenu.add(new WFXMI(\"DBMS_Metadata\",null,null,sub1));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Query data\",\"javascript:execQueryObjData(\'myTextarea\','" + name +"')\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit data\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("var sub2 = new WebFXMenu;");
		returnVal.append("sub2.width = 180;");
		returnVal.append("sub2.add(new WFXMI(\"(No user defined folders)\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Add to folder\",null,null,sub2));");
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
		}else if(fieldName.equals(FIELDS[3])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[4])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[5])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}
		return returnVal.toString();
	}
	
	public String getParameter(String name) {
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
			if(ub.getDbglobal()) obj = "all_tab_columnss";
			else obj = "user_tab_columns";
			sql = "select column_name,data_type from " + obj + " where table_name='" + name + "' order by column_id";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				columnIcon = getColumnTypeIcon(CharSet.nullToEmpty(rs.getString(2)));
				if (CharSet.nullToEmpty(rs.getString(1)).equals("")) sb.append("<tree text=\"(RESULT)\" icon=\""+ columnIcon +"\" openIcon=\""+ columnIcon +"\" />"); 
				else sb.append("<tree text=\""+CharSet.nullToEmpty(rs.getString(1))+"\" icon=\""+ columnIcon +"\" openIcon=\""+ columnIcon +"\" />"); 
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getTrigger(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		String icon= ICON_KEY;
		try{
			String obj = null;
			String subType = "TRIGGER";
			if(ub.getDbglobal()) obj = "all_triggers";
			else obj = "all_triggers";
			sql = "select tri.owner,tri.trigger_name,obj.status from " + obj + " tri,all_objects obj where tri.table_name='" + name + "' and tri.table_owner='" + ub.getUsername().toUpperCase()+ "' and tri.owner = obj.owner and tri.trigger_name = obj.object_name";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				icon = DbBeanManager.getChildMenuIcon(subType,CharSet.nullToEmpty(rs.getString(3)));
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				//String field = FIELDS_PRI + "." + objectName;
				//String field = objectName;
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName+"&amp;field=\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','',event)\" />");
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getReference(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		try{
			String obj = null;
			if(ub.getDbglobal()) obj = "all_dependencies";
			else obj = "user_dependencies";
			sql = "select referenced_owner,referenced_name,referenced_type from " + obj + " where name='" + name + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = CharSet.nullToEmpty(rs.getString(1))+ "." + CharSet.nullToEmpty(rs.getString(2));
				String subType = CharSet.nullToEmpty(rs.getString(3));
				String icon= ICON_PARAMTER;
				
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				
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
	
	public String getReferencedBy(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		try{
			String obj = null;
			if(ub.getDbglobal()) obj = "all_dependencies";
			else obj = "user_dependencies";
			sql = "select referenced_owner,name,type, " +
				"(select status from all_objects where object_type=type and owner=referenced_owner and object_name=name) status " +
				" from " + obj + " where referenced_name='" + name + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc, TYPE asc, NAME asc";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				String subType = CharSet.nullToEmpty(rs.getString(3));
				String icon= ICON_PARAMTER;
				
				if (CharSet.nullToEmpty(rs.getString(1)).equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
						
				icon = DbBeanManager.getChildMenuIcon(subType,CharSet.nullToEmpty(rs.getString(4)));
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
	
	public String getSynonym(String name) {
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
			String subType = "SYNONYM";
			if(ub.getDbglobal()) obj = "all_synonyms";
			else obj = "user_synonyms";
			sql = "select synonym_name from " + obj + " where table_name='" + name + "' and table_owner='" + ub.getUsername().toUpperCase() + "'";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				objectName = CharSet.nullToEmpty(rs.getString(1));
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
	
	public String getGrantedToUser(String name) {
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
			String roleObj = "role_tab_privs";
			String subType = "USER";
			String filed = DbUserBean.FIELDS_PRI + "." + name;
			if(ub.getDbglobal()) {
				obj = "all_tab_privs";
				sql = "select distinct grantee from " + obj + " where table_name='" + name + "' and grantor='" + ub.getUsername().toUpperCase() + "'";
			} else {
				obj = "user_tab_privs";
				sql = "select distinct grantee from " + obj + " userp where table_name='" + name + "' and not exists (select 1 from " + roleObj + " rolep where rolep.role = userp.grantee and rolep.table_name = userp.table_name)";
			}
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				objectName = CharSet.nullToEmpty(rs.getString(1));
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName+"&amp;field="+filed+"\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+""+"',event)\" />");
			}	
			if (i == 0) sb.append("<tree text=\"Nodata\" />");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(rs != null) ub.getDb().close(rs);
		}
		return sb.toString();
	}
	
	public String getGrantedToRole(String name) {
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
			String roleObj = "role_tab_privs";
			String subType = "ROLE";
			String filed = DbUserBean.FIELDS_PRI + "." + name;
			if(ub.getDbglobal()) {
				obj = "all_tab_privs";
				sql = "select distinct grantee from " + obj + " where table_name='" + name + "' and grantor='" + ub.getUsername().toUpperCase() + "'";
			} else {
				obj = "user_tab_privs";
				sql = "select distinct grantee from " + obj + " userp where table_name='" + name + "' and exists (select 1 from " + roleObj + " rolep where rolep.role = userp.grantee and rolep.table_name = userp.table_name)";
			}
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = "";
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				objectName = CharSet.nullToEmpty(rs.getString(1));
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName+"&amp;field="+filed+"\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+""+"',event)\" />");
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
