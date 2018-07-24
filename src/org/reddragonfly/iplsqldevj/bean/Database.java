/*
 * @author phanrider
 * @version 1.0, 2007-11-20
 *
 * @source: DbConnect.java Create on 2007-11-20 下午11:04:22
 */
package org.reddragonfly.iplsqldevj.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.Vector;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;



public class Database {

    private Connection conn;
	    public Database()throws Exception{
	        String url = "jdbc:oracle:thin:@192.168.1.1:1521:gsdb";
	        String driverName = "oracle.jdbc.driver.OracleDriver";
	        String user = "test";
	        String password = "test";
	        Driver dbDriver = (Driver) Class.forName(driverName).newInstance();
	        DriverManager.registerDriver(dbDriver);
	        this.conn = DriverManager.getConnection(url, user, password);        
	    }
	    public Database(String url, String user, String password )throws Exception{
	        String driverName = "oracle.jdbc.driver.OracleDriver";
	        Driver dbDriver = (Driver) Class.forName(driverName).newInstance();
	        DriverManager.registerDriver(dbDriver);
	        this.conn = DriverManager.getConnection(url, user, password);        
	    }

	    public Connection getConnection() throws Exception{
	        return this.conn;      
	    }
	    
	    public Database(boolean isAutoCommit,Connection conn) throws Exception{
	        this.conn = conn;
	        this.conn.setAutoCommit(isAutoCommit);
	    }

	    public ResultSet getRS(String sql) throws Exception {
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            stmt = conn.createStatement();
	            //stmt.setFetchSize(50);
	            rs = stmt.executeQuery(sql);
	        }
	        catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	        return rs;
	    }

	    /**
	     * 执行插入和更新语句
	     * @param sql - sql语句
	     * @throws Exception
	     * @return int
	     */
	    public int execSqlUpdate(String sql) throws Exception {
	        Statement stmt = null;
	        try {
	            int executeResult;
	            stmt = conn.createStatement();
	            executeResult = stmt.executeUpdate(sql);
	            stmt.close();
	            return executeResult;
	        }
	        catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	        finally {
	            try {
	                stmt.close();
	            }
	            catch (Exception e) {
	            }
	        }
	    }

	    /**
	     * prepare执行插入和更新语句
	     * @param sql - sql语句
	     * @param param - 参数
	     * @throws Exception
	     * @return int
	     * @see 操作oracle数据库的时候，date型数据通过java.sql.Timestamp写入，操作实例如下：
	        Database db = new Database(true);
	        Vector v=new Vector();
	        v.add(new Integer(1));
	        v.add(java.sql.Timestamp.valueOf("2003-03-10 13:23:00"));
	        v.add(new String("343"));
	        db.execPrepareSqlUpdate("insert into czh_a(a,b,c) values (?,?,?)",v);
	     */
	    public int execPrepareSqlUpdate(String sql, Vector params) throws Exception {
	      return execPrepareSqlUpdate(sql, params.toArray());
	    }

	    public int execPrepareSqlUpdate(String sql, Object[] params) throws Exception {
	      PreparedStatement pstmt = null;
	      try {
	        int executeResult;
	        pstmt = conn.prepareStatement(sql);
	        for (int i = 0; i <= params.length - 1; i++) {
	          pstmt.setObject(i + 1, params[i]);
	        }
	        executeResult = pstmt.executeUpdate();
	        return executeResult;
	      }
	      catch (Exception e) {
	    	throw new Exception(e.getMessage());
	      }
	      finally {
	        try {
	          pstmt.close();
	        }
	        catch (Exception e) {
	        }
	      }
	    }
	    
	    
	    
	    public ResultSet getDesc(String sql) throws Exception {
	        Statement stmt = null;
	        ResultSet rs = null;
	        boolean exc;
	        try {
	            stmt = conn.createStatement();
	            //stmt.setFetchSize(50);
	            exc= stmt.execute(sql);
	            if(exc){
	            	rs=stmt.getResultSet();
	            }
	        }
	        catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	        return rs;
	    }
	    
	    /**
	     * 得到Clob字段内容
	     * @param rs
	     * @param columnName 列名称
	     * @return
	     * @throws Exception
	     */
	    public String getClob(ResultSet rs, String columnName) throws Exception {
	      Clob clob = null;
	      String str = "";
	      Reader is = null;
	      try {
	        clob = rs.getClob(columnName);
	        if (clob != null) {
	          is = clob.getCharacterStream();
	          BufferedReader br = new BufferedReader(is);
	          String s = br.readLine();
	          while (s != null) {
	            str += s + "\n";
	            s = br.readLine();
	          }
	          is.close();
	        }
	      }
	      catch (Exception e) {
	        throw new Exception(e.getMessage());
	      }
	      return str;
	    }

	    /**
	     * 得到Clob字段内容
	     * @param rs
	     * @param columnIndex 列索引
	     * @return
	     * @throws Exception
	     */
	    public String getClob(ResultSet rs, int columnIndex) throws Exception {
	      Clob clob = null;
	      String str = "";
	      Reader is = null;
	      try {
	        clob = rs.getClob(columnIndex);
	        if (clob != null) {
	          is = clob.getCharacterStream();
	          BufferedReader br = new BufferedReader(is);
	          String s = br.readLine();
	          while (s != null) {
	            str += s + "\n";
	            s = br.readLine();
	          }
	          is.close();
	        }
	      }
	      catch (Exception e) {
	        throw new Exception(e.getMessage());
	      }
	      return str;

	    }
	    
	    private byte[] getBlob(BLOB blob) throws Exception {
	        if (blob == null) {
	          return new byte[0];
	        }
	        InputStream is = blob.getBinaryStream();
	        int upMaxByte = 1024;
	        byte[] buffer = new byte[upMaxByte];
	        int readByte = 0;
	        int flength = (int) blob.length();
	        byte[] rtn = new byte[flength];

	        int i = 0;
	        while (i < flength) {
	          if (i + upMaxByte > flength) {
	            readByte = (int) (flength - i);
	          }
	          else {
	            readByte = upMaxByte;
	          }

	          is.read(buffer, 0, readByte);
	          System.arraycopy(buffer,0,rtn,i,readByte);
	          i += upMaxByte;
	        }
	        is.close();
	        return rtn;

	      }
	    
	    /**
	     * 得到Blob字段
	     * @param rs
	     * @param columnIndex
	     * @return
	     * @throws Exception
	     */
	    public byte[] getBlob(ResultSet rs, int columnIndex) throws Exception {
	      return this.getBlob( ( (OracleResultSet) rs).getBLOB(columnIndex));
	    }

	    /**
	     * 获得Blob
	     * @param rs
	     * @param columnName
	     * @return
	     * @throws Exception
	     */
	    public byte[] getBlob(ResultSet rs, String columnName) throws Exception {
	      return this.getBlob( ( (OracleResultSet) rs).getBLOB(columnName));
	    }

	    
	    
	    
	    public void cleanup() {
	        try {
	            if (this.conn != null) {
	                if (!conn.isClosed()) {
	                    this.conn.close();
	                }
	            }
	        }
	        catch (Exception e) {
	        }
	    }

	    public void close(ResultSet rs) {
	        try {
	            if (rs != null) {
	                rs.getStatement().close();
	                rs.close();
	                rs = null;
	            }
	        }
	        catch (Exception e) {
	        }
	    }

	    public void rollback() throws Exception {
	        try {
	            this.conn.rollback();
	        }
	        catch (Exception e) {
	        	throw new Exception(e.getMessage());
	        }
	    }

	    public void commit() throws Exception {
	        try {
	            this.conn.commit();
	        }
	        catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	    }

	    public long getSeqNextVal(String sequenceName) throws Exception {
	        ResultSet rs = null;
	        try {
	            String sql = "select " + sequenceName + ".nextval from dual";
	            rs = this.getRS(sql);
	            if (rs.next()) {
	            	long nextval = rs.getLong(1);
	            	return nextval;
	            }else {
	                throw new Exception("can't get " + sequenceName + " nextval!");
	            }
	        }catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }finally {
	        	if(rs != null) this.close(rs);
	        }
	    }

	    
		//测试用
		public static void main(String args[]){          	
		      try{
		    	  Database db=new Database();    	  
		        String sql="select * from t_dict_man --where state='A'";
		        ResultSet rs=db.getRS(sql);
		        while(rs.next()){
		          System.out.println(rs.getString("code")+" "+rs.getString("name"));
		        }
		        db.close(rs);
		        db.cleanup();
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }
		    }
	    
}


