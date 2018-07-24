package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbTriggerBean extends DbBean {
	
	public static String TYPE = "trigger";
	public static String ICON_INVALID = "dbimages/err_trigers.png";
	public static String ICON_VALID = "dbimages/ena_trigers.png";
	public static String ICON_PARAMTER = "dbimages/parameter.png";
	
	protected static String[] FIELDS = 
	    {"References"};
	
	protected String name = "";
	public DbTriggerBean(String name){
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
			sb.append(getReference(name));
		}
		sb.append("</tree>");
		return sb.toString();
	}
	
	public String getMenuScript(){
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("myMenu.width = 200;");
		returnVal.append("myMenu.add(new WFXMI(\"New...\", \"\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Refresh\", \"javascript:tree.getSelected().reload();\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Copy comma separated\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Properties\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Properties','500px','312px');\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"View\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Edit\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Drop\",\"javascript:showCommon('"+TYPE+"','"+name+"','','Drop','500px','120px');\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Browse\"));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Recompile\"));");
		returnVal.append("myMenu.add(new WFXMI(\"Add debug information\"));");
		returnVal.append("var sub1 = new WebFXMenu;");
		returnVal.append("sub1.width = 100;");
		returnVal.append("sub1.add(new WFXMI(\"DDL\"));");
		returnVal.append("sub1.add(new WFXMI(\"XML\"));");
		returnVal.append("myMenu.add(new WFXMI(\"DBMS_Metadata\",null,null,sub1));");
		returnVal.append("myMenu.add(new WebFXMenuSeparator());");
		returnVal.append("myMenu.add(new WFXMI(\"Disable\"));");
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
		}
		return returnVal.toString();
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
}
