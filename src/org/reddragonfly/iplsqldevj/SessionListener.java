package org.reddragonfly.iplsqldevj;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.reddragonfly.iplsqldevj.bean.UserBean;

public class SessionListener implements HttpSessionListener{
	
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}
	
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession session = se.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		if(ub != null) ub.closeDb();   //关闭当前用户所对应的数据库连接
	}
	
}