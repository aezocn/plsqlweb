package org.reddragonfly.iplsqldevj.action.tree;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.reddragonfly.iplsqldevj.action.BaseAction;
import org.reddragonfly.iplsqldevj.bean.dbbean.DbBeanManager;
import com.opensymphony.xwork2.ActionContext;

public class ShowTreeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String name;
	private String field;
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();
		//System.out.println("type:"+getType()+" name:"+getName()+" field:"+getField());
		out.println(DbBeanManager.getTreeXml(getType(),getName(),getField()));
		out.flush();
		return null;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
