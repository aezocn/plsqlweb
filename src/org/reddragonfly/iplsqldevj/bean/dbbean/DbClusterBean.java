package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.UserBean;

import com.opensymphony.xwork2.ActionContext;

public class DbClusterBean extends DbBean {
	
	public static String TYPE = "cluster";
	public static String ICON_INVALID = "dbimages/clusters.png";
	public static String ICON_VALID = "dbimages/clusters.png";
	public static String ICON_PARAMTER = "dbimages/valid_queue_tables.png";
	
	protected static String[] FIELDS = 
	    {"Tables"};
	
	protected String name = "";
	public DbClusterBean(String name){
		this.name = name;
	}
	
	public String getTreeXml() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		for(int i = 0;i < FIELDS.length;i++){
			//客户端脚本已经重写了onmouseover事件，事实上在客户端为onmouseup事件，这是出于鼠标右键的考虑
			sb.append("<tree text=\""+FIELDS[i]+"\" src=\"showTree.action?type="+TYPE+"&amp;name="+name.replaceAll("#","%23")+"&amp;field="+FIELDS[i]+"\" onblur=\"hideMenu()\" onmouseover=\"showAppointedMenu('"+TYPE+"','"+name+"','"+FIELDS[i]+"',event)\" />");
		}
		sb.append("</tree>");
		return sb.toString();
	}
	
	public String getFieldTreeXml(String fieldName) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<tree>");
		if(fieldName.equals(FIELDS[0])) {
			sb.append(getTable(name));
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
			String[] field = name.split("\\.",2);
			if(ub.getDbglobal()) {
				obj = "all_tables";
				if (field[0].equals(name)) {
					sql = "select owner,table_name from " + obj + " where cluster_name='" + name + "' and cluster_owner='" + ub.getUsername().toUpperCase() + "' order by owner asc, table_name asc";
				} else {
					sql = "select owner,table_name from " + obj + " where cluster_name='" + field[1].toUpperCase() + "' and cluster_owner='" + field[0].toUpperCase() + "' order by owner asc, table_name asc";
				}
			}else {
				//暂于all_objects时相同的处理方式
				obj = "all_tables";
				if (field[0].equals(name)) {
					sql = "select owner,table_name from " + obj + " where cluster_name='" + name + "' and cluster_owner='" + ub.getUsername().toUpperCase() + "' order by owner asc, table_name asc";
				} else {
					sql = "select owner,table_name from " + obj + " where cluster_name='" + field[1].toUpperCase() + "' and cluster_owner='" + field[0].toUpperCase() + "' order by owner asc, table_name asc";
				}
			}
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
}
