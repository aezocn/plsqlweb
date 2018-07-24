package org.reddragonfly.iplsqldevj.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Vector;

import org.reddragonfly.iplsqldevj.bean.Database;

public class UserBean {
	
	private String username;
	private String password;
	private String databaseip;
	private String listenport;
	private String servername;
	private String connecttype;
	private Database db;
	private String dbversion;
	private boolean dbglobal = false;
	
	public UserBean(String username,String password,String databaseip,String listenport,String servername,String connecttype) throws Exception{
		Connection conn = null;
		try{
			//this.username = username; //2009-06-08 phanrider 修改，移动到判断下面再赋值
			this.password = password;
			this.databaseip = databaseip;
			this.listenport = listenport;
			this.servername = servername;
			this.connecttype = connecttype;
			//2009-06-08 phanrider 加入 sysoper/sysdba
			if( connecttype.equals("1") ) {
				username = username + " as sysoper";
			} else if( connecttype.equals("2") ) {
				username = username + " as sysdba";
			}
			this.username = username;
			//======== add end ========
			String url = "jdbc:oracle:thin:@" +  databaseip + ":" + listenport + ":" + servername;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, username, password);  
			db = new Database(false,conn); //2009-06-04 phanrider 从true->false 默认不自动提交
			String sql = "select * from v$version where banner like 'Oracle%'";
			List data = QueryManage.getResult(sql, db);
			Vector v = (Vector)data.get(0);
			dbversion = (String)v.get(0);
		}catch(Exception e){
			if(conn != null) conn.close();
			throw e;
		}
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getDatabaseip() {
		return databaseip;
	}

	public String getListenport() {
		return listenport;
	}
	
	public String getServername() {
		return servername;
	}

	public String getConnecttype() {
		return connecttype;
	}
	
	public String getDbversion(){
		return dbversion;
	}
	
	public boolean getDbglobal() {
		return dbglobal;
	}

	public void setDbglobal(boolean dbglobal) {
		this.dbglobal = dbglobal;
	}
	
	/**
	 * <p>注意，通过该方法获取的Database不需要主动关闭，它将在用户退出或超时时由监控程序关闭。主动关闭该Database将会带来意想不到的诸多错误
	 * @return Database
	 */
	public Database getDb() {
		return db;
	}
	
	/**
	 * <p>该方法提供给监控程序在适当的时候关闭当前用户对应的Database
	 */
	public void closeDb(){
		db.cleanup();
	}
	
}
