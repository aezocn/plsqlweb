package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbRoleBean extends DbBean {
	
	public static String TYPE = "role";
	public static String ICON_INVALID = "dbimages/roles.png";
	public static String ICON_VALID = "dbimages/roles.png";
	public static String ICON_PARAMTER = "dbimages/privilege.png";
	public static String PRIVILEGE_FLAG = "flag";	//by phanrider add  2010-01-24 在不改变showTree.action传递参数及接口getFieldTreeXml情况下加此标志
	
	protected static String[] FIELDS = 
	    {"Object privileges","System privileges","Role grants","Granted to users","Granted to roles"};
	protected static String FIELDS_PRI = "Privileges"; //by phanrider add  2010-01-24 特殊调用
	
	protected String name = "";
	public DbRoleBean(String name){
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
			sb.append("<tree text=\""+FIELDS[0]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[0]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[0]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[1])) {
			sb.append("<tree text=\""+FIELDS[1]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+FIELDS[1]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[1]+"',event)\" />");
		}
		if(fieldName.equals(FIELDS[2])) {
			//sb.append(getReferencedBy(name));
		}
		if(fieldName.equals(FIELDS[3])) {
			//sb.append(getSynonym(name));
		}
		if(field[0].equals(FIELDS_PRI)) {	//单独加入privilege处理
			if (field.length == 2) {
				String TempFieldName = fieldName + "." + PRIVILEGE_FLAG;
				sb.append("<tree text=\""+FIELDS_PRI+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name+"&amp;field="+TempFieldName+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+fieldName+"',event)\" />");
			} else {
				if (field[2].equals(PRIVILEGE_FLAG))	sb.append(getPrivilege(field[1]));
			}
		}
		sb.append("</tree>");
		return sb.toString();
	}
	
	public String getMenuScript(){
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("myMenu.width = 200;");
		returnVal.append("myMenu.add(new WFXMI(\"New...\", \"\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Duplicate...\", \"\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"View\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Drop\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Drop','500px','120px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Browse\"));");
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
		}else {
			returnVal.append("myMenu.width = 150;");
			returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
			returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		}
		return returnVal.toString();
	}

	public String getPrivilege(String name) {
		StringBuffer sb = new StringBuffer();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		String sql = null;
		ResultSet rs = null;
		
		try{
			String obj = null;
			String roleObj = "role_tab_privs";
			if(ub.getDbglobal()) {
				obj = "all_tab_privs";
				sql = "select privilege from " + obj + " where table_name='" + name + "' and grantee='" + this.name.toUpperCase() + "' order by privilege";
			} else {
				obj = "user_tab_privs";
				sql = "select privilege from " + obj + " userp where table_name='" + name + "' and grantee='" + this.name.toUpperCase() + "' and exists (select 1 from " + roleObj + " rolep where rolep.role = userp.grantee and rolep.table_name = userp.table_name) order by privilege";
			}
			rs = ub.getDb().getRS(sql);
			int i = 0;
			while(rs.next()){
				i = 1;
				sb.append("<tree text=\""+CharSet.nullToEmpty(rs.getString(1))+"\" icon=\""+ ICON_PARAMTER +"\"  openIcon=\""+ ICON_PARAMTER +"\" />"); 
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
