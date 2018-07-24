package org.reddragonfly.iplsqldevj.interceptor;

import com.opensymphony.xwork2.ActionInvocation;

public class MyInterceptor extends BaseInterceptor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		return arg0.invoke();
	}

}
