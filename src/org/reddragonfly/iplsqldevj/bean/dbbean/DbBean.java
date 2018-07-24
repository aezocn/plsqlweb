package org.reddragonfly.iplsqldevj.bean.dbbean;

public abstract class DbBean {
	
	public abstract String getTreeXml();
	
	public abstract String getFieldTreeXml(String fieldName);
	
	public abstract String getMenuScript();
	
	public abstract String getFieldMenuScript(String fieldName);
}
