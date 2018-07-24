/**
 * @<p>Struts2操作的实用类
 * @author jeanwendy,2009.03.10
 */
package org.reddragonfly.iplsqldevj;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.OgnlValueStack;

public final class Struts2Utilities {
	
	public static ActionSupport getActionSupport(HttpServletRequest request){
		Object o = request.getAttribute("struts.valueStack");
		if(o instanceof OgnlValueStack){
			OgnlValueStack valueStack = (OgnlValueStack)o;
			ActionSupport actionSupport = null;
			for(int i = 0;i < valueStack.size();i++){
				Object oo = valueStack.peek();
				if(oo instanceof ActionSupport){
					actionSupport = (ActionSupport)oo;
					break;
				}
			}
			return actionSupport;
		}else{
			return null;
		}
	}
	
	public static List getFieldErrors(HttpServletRequest request,String fieldname){
		ActionSupport actionSupport = getActionSupport(request);
		if(actionSupport != null){
			Map map = actionSupport.getFieldErrors();
			return (List)map.get(fieldname);
		}
		return null;
	}
	
	public static String getFieldFirstError(HttpServletRequest request,String fieldname){
		List list = getFieldErrors(request,fieldname);
		if(list != null && list.size() > 0){
			return (String)list.get(0);
		}
		return null;
	}
	
	public static Collection getActionErrors(HttpServletRequest request){
		ActionSupport actionSupport = getActionSupport(request);
		if(actionSupport != null){
			Collection coll = actionSupport.getActionErrors();
			return coll;
		}
		return null;
	}
	
	public static String getActionFirstError(HttpServletRequest request){
		Collection coll = getActionErrors(request);
		if(coll != null && coll.size() > 0){
			return (String)(coll.iterator().next());
		}
		return null;
	}
	
	public static Collection getActionMessages(HttpServletRequest request){
		ActionSupport actionSupport = getActionSupport(request);
		if(actionSupport != null){
			Collection coll = actionSupport.getActionMessages();
			return coll;
		}
		return null;
	}
	
	public static String getActionFirstMessage(HttpServletRequest request){
		Collection coll = getActionMessages(request);
		if(coll != null && coll.size() > 0){
			return (String)(coll.iterator().next());
		}
		return null;
	}
	
}
