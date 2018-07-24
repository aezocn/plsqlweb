package org.reddragonfly.iplsqldevj.action.login;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.action.BaseAction;
import org.reddragonfly.iplsqldevj.bean.UserBean;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String databaseip;
	private String listenport;
	private String servername;
	private String connect;
	private String relogin;
	
	public String getUsername() {
		return username == null?"":username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password==null?"":password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDatabaseip() {
		return databaseip==null?"":databaseip;
	}
	
	public void setDatabaseip(String databaseip) {
		this.databaseip = databaseip;
	}
	
	public String getListenport() {
		return listenport==null?"1521":listenport;
	}
	
	public void setListenport(String listenport) {
		this.listenport = listenport;
	}
	
	public String getServername() {
		return servername==null?"":servername;
	}
	
	public void setServername(String servername) {
		this.servername = servername;
	}
	
	public String getConnect() {
		return connect==null?"0":connect;
	}
	
	public void setConnect(String connect) {
		this.connect = connect;
	}
	
	public String getRelogin() {
		return relogin==null?"":relogin;
	}

	public void setRelogin(String relogin) {
		this.relogin = relogin;
	}

	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		PrintWriter out = response.getWriter();
		UserBean ub = null;
		try{
			ub = new UserBean(username,password,databaseip,listenport,servername,connect);
		}catch(Exception e){
			if(getRelogin().equals("true")){
				out.print("<script type=\"text/javascript\">window.opener.myreload();window.close();</script>");
				out.flush();
			}
			addActionError(e.getLocalizedMessage());
			return INPUT;
		}
		UserBean oldUb = (UserBean)session.getAttribute("user");
		if(oldUb != null) oldUb.closeDb();
		session.setAttribute("user", ub);
		if(getRelogin().equals("true")){
			out.print("<script type=\"text/javascript\">window.opener.myreload();window.close();</script>");
			out.flush();
		}
		return SUCCESS;
	}
	
}
