package org.reddragonfly.iplsqldevj.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class QuitAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
		HttpSession session = request.getSession();
		session.invalidate();
		addActionMessage("log out successfully");
		return INPUT;
	}
}
