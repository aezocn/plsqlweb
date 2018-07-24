package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbPackageBean extends DbBean {
	
	public static String TYPE = "package";
	public static String ICON_INVALID = "dbimages/invalid_pkgs.png";
	public static String ICON_VALID = "dbimages/valid_pkgs.png";
	public static String ICON_PARAMTER = "dbimages/parameter.png";
	
	protected static String[] FIELDS = 
	    {"Functions","Procedures","Types","Variables","Constants","Exceptions","References","Referenced by","Synonyms","Granted to users","Granted to roles"};
	
	protected String name = "";
	public DbPackageBean(String name){
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
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		//System.out.println(name);
		if(fieldName.equals(FIELDS[0])) {
			sb.append(getParameter(name));
		}
		if(fieldName.equals(FIELDS[1])) {
			sb.append("<tree text=\""+FIELDS[1]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[2]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[1]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[2])) {
			sb.append("<tree text=\""+FIELDS[2]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[2]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[2]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[3])) {
			sb.append("<tree text=\""+FIELDS[3]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[3]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[3]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[4])) {
			sb.append("<tree text=\""+FIELDS[4]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[4]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[4]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[5])) {
			sb.append("<tree text=\""+FIELDS[5]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[5]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[5]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[6])) {
			sb.append(getReference(name));
		}
		if(fieldName.equals(FIELDS[7])) {
			sb.append(getReferencedBy(name));
		}
		if(fieldName.equals(FIELDS[8])) {
			sb.append(getSynonym(name));
		}
		if(fieldName.equals(FIELDS[9])) {
			sb.append(getGrantedToUser(name));
		}
		if(fieldName.equals(FIELDS[10])) {
			sb.append(getGrantedToRole(name));
		}
		sb.append("</tree>");
		return sb.toString();
	}
	
	public String getMenuScript(){
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("myMenu.width = 200;");
		returnVal.append("myMenu.add(new WFXMI(\"New...\", \"javascript:showRoot('"+TYPE+"','"+name+"','Packages','New...','550px','300px');\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Properties\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Properties','500px','312px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Describe\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Describe','500px','312px');\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"View\",\"javascript:showViewObject('"+TYPE+"','"+name+"','','View');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit\",\"javascript:showEditObject('"+TYPE+"','"+name+"','Packages','Edit');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"View Spec & Body\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit Spec & Body\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Drop\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Drop','500px','120px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Browse\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Recompile\", \"javascript:recompile('"+TYPE+"','"+name+"','0');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Add debug information\", \"javascript:recompile('"+TYPE+"','"+name+"','1');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Recompile referencing objects\"));");
		returnVal.append("var sub1 = new WebFXMenu;");
		returnVal.append("sub1.width = 100;");
		returnVal.append("sub1.add(new WFXMI(\"DDL\"));");
		returnVal.append("sub1.add(new WFXMI(\"XML\"));");
		returnVal.append("myMenu.add(new WFXMI(\"DBMS_Metadata\",null,null,sub1));");
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
		}else if(fieldName.equals(FIELDS[6])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[7])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[8])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[9])){
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}else if(fieldName.equals(FIELDS[10])){
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
			if(ub.getDbglobal()) obj = "all_arguments";
			else obj = "user_arguments";
			//sql = "describe " + name;
			sql = "select argument_name from " + obj + " where object_name='" + name + "' and PACKAGE_NAME IS NULL order by sequence";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				if (CharSet.nullToEmpty(rs.getString(1)).equals("")) sb.append("<tree text=\"(RESULT)\" icon=\""+ ICON_PARAMTER +"\" openIcon=\""+ ICON_PARAMTER +"\" />"); 
				else sb.append("<tree text=\""+CharSet.nullToEmpty(rs.getString(1))+"\" icon=\""+ ICON_PARAMTER +"\" openIcon=\""+ ICON_PARAMTER +"\" />"); 
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
			sql = "select referenced_owner,referenced_name,referenced_type from " + obj + " where name='" + name + "' and referenced_type != 'NON-EXISTENT' and type = '" +  TYPE.toUpperCase() + "' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc";
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				String objectName = CharSet.nullToEmpty(rs.getString(1))+ "." + CharSet.nullToEmpty(rs.getString(2));
				String subType = CharSet.nullToEmpty(rs.getString(3));
				String icon= ICON_PARAMTER;
				
				if (CharSet.nullToEmpty(rs.getString(1)).toUpperCase().equals(ub.getUsername().toUpperCase())) objectName = CharSet.nullToEmpty(rs.getString(2));
				else objectName = CharSet.nullToEmpty(rs.getString(1)) + "." + CharSet.nullToEmpty(rs.getString(2));
				
				icon = DbBeanManager.getChildMenuIcon(subType,"");
				
				sb.append("<tree text=\""+objectName+"\" src=\"showTree.action?type="+subType+"&amp;name="+objectName+"&amp;field=\" icon=\""+ icon +"\" openIcon=\""+ icon +"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+subType+"','"+objectName+"','"+""+"',event)\" />");
				//sb.append(DbBeanManager.getTreeXml(subType, objectName, ""));
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
			if(ub.getDbglobal()) { 
				obj = "all_dependencies";
				String[] field = name.split("\\.",2);
				if (field[0].equals(name)) {
					sql = "select owner,name,type, " +
						"(select status from all_objects where object_type=type and owner=referenced_owner and object_name=name) status " +
						" from " + obj + " where referenced_name='" + name + "' and referenced_owner='" + ub.getUsername().toUpperCase() + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc, TYPE asc, NAME asc";
				} else {
					sql = "select owner,name,type, " +
						"(select status from all_objects where object_type=type and owner=referenced_owner and object_name=name) status " +
						" from " + obj + " where referenced_name='" + field[1].toUpperCase() + "' and referenced_owner='" + field[0].toUpperCase() + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc, TYPE asc, NAME asc";
				}
			}else {
				//暂和全局变量处理一样
				obj = "all_dependencies";
				String[] field = name.split("\\.",2);
				if (field[0].equals(name)) {
					sql = "select owner,name,type, " +
						"(select status from all_objects where object_type=type and owner=referenced_owner and object_name=name) status " +
						" from " + obj + " where referenced_name='" + name + "' and referenced_owner='" + ub.getUsername().toUpperCase() + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc, TYPE asc, NAME asc";
				} else {
					sql = "select owner,name,type, " +
						"(select status from all_objects where object_type=type and owner=referenced_owner and object_name=name) status " +
						" from " + obj + " where referenced_name='" + field[1].toUpperCase() + "' and referenced_owner='" + field[0].toUpperCase() + "' and referenced_type != 'NON-EXISTENT' order by REFERENCED_TYPE asc, REFERENCED_OWNER asc, REFERENCED_NAME asc, TYPE asc, NAME asc";
				}
			}
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
}
