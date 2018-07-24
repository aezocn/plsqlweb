package org.reddragonfly.iplsqldevj;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.reddragonfly.iplsqldevj.bean.UserBean;

public class LoginFilter implements Filter {
	
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		UserBean ub = (UserBean)session.getAttribute("user");
		if(ub == null){
			String url = httpRequest.getRequestURI();
			if(url.endsWith("/") || url.indexOf("/login/index.jsp") != -1 || url.indexOf("/login/login.action") != -1) chain.doFilter(request, response);
			else{
				request.setAttribute("overdue", "your session has been overdue");
				request.getRequestDispatcher("/login/index.jsp").forward(request, response);
				return;
			}
		}else{
			chain.doFilter(request, response);
		}
	}
	
}
