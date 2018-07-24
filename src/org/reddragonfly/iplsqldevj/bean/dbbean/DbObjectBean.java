package org.reddragonfly.iplsqldevj.bean.dbbean;

import java.sql.ResultSet;

import org.directwebremoting.WebContextFactory;
import org.reddragonfly.iplsqldevj.bean.Database;
import org.reddragonfly.iplsqldevj.bean.QueryManage;
import org.reddragonfly.iplsqldevj.bean.UserBean;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import java.util.*;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DbObjectBean {

	//by phanrider 2011-4-13 
	//新增表的属性
	private static String getTablespace = null;
	private static String getInitExtent = null;
	private static String getFree = null;
	private static String getNextExtent = null;
	private static String getUsed = null;
	private static String getIncrease = null;
	private static String getIniTrans = null;
	private static String getMinExtent = null;
	private static String getMaxTrans = null;
	private static String getMaxExtent = null;
	private static String getClusterName = null;
	private static String getClusterColumns = null;
	private static String getComment = null;
	
	public List getObject(String sql, Vector v1) {
		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();
		ResultSet rs = null;
		List list = new ArrayList();
		list.add(v1);
		try {
			rs = db.getRS(sql);

			ResultSetMetaData rsm = rs.getMetaData();
			int colCount = rsm.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= colCount; i++) {
					String properties = "";
					String value = "";
					Vector v = new Vector();
					if (rsm.getColumnType(i) == java.sql.Types.NUMERIC) {
						properties = rsm.getColumnName(i);
						// System.out.print(properties+" ");
						value = CharSet.nullToEmpty(rs.getString(i));
						// System.out.println(value);
						if (value.startsWith("."))
							value = "0" + value;
					} else {
						properties = rsm.getColumnName(i);
						value = CharSet.nullToEmpty(rs.getString(i));
						// System.out.println(value);
					}
					v.add(properties);
					v.add(value);
					list.add(v);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List getOther(String sql, String column) throws Exception {
		String[] columnName = column.split(",");
		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();

		List list = new ArrayList();
		ResultSet rs = null;
		String value = "";
		try {
			rs = db.getRS(sql);
			if (columnName.length == 1) {
				while (rs.next()) {
					value = CharSet.nullToEmpty(rs.getString(columnName[0]));
					list.add(value);
				}
			} else {
				while (rs.next()) {
					Vector v = new Vector();
					for (int i = 0; i < columnName.length; i++) {
						value = CharSet
								.nullToEmpty(rs.getString(columnName[i]));
						v.add(value);
					}
					list.add(v);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	public List getOther2(String sql, Vector v1) throws Exception {
		// String[] columnName=column.split(",");
		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();

		List list = new ArrayList();
		list.add(v1);
		ResultSet rs = null;
		String value = "";
		try {
			rs = db.getRS(sql);
			if (v1.size() == 1) {
				while (rs.next()) {
					value = rs.getString(1);
					list.add(value);
				}
			} else {
				ResultSetMetaData rsm = rs.getMetaData();
				//System.out.println(rsm.getColumnCount());
				while (rs.next()) {
					Vector v = new Vector();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						if (rsm.getColumnType(i) == java.sql.Types.NUMERIC) {
							value = CharSet.nullToEmpty(rs.getString(i));

							if (value.startsWith("."))
								value = "0" + value;
						} else {
							value = CharSet.nullToEmpty(rs.getString(i));
						}
						if (i == 1 && value.equals("")) {
							value = "(Result)";
						}
						v.add(value);

					}
					list.add(v);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public StringBuffer getKeyID(String sql, Database db) throws Exception {
		StringBuffer KeyValue = new StringBuffer();
		ResultSet rs = null;
		try{
			rs = db.getRS(sql);
			int i = 0;
			while (rs.next()) {
				if (i > 0) KeyValue.append(",");
				KeyValue.append(rs.getString(1));
				i++;
			}
			return KeyValue;
		} catch (Exception e) {
		throw e;
		}
	}
	
	//得到 TABLE 的各种约束
	public List getKeys(String sql, Vector v1) throws Exception {
		// String[] columnName=column.split(",");
		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();
		
		List list = new ArrayList();
		list.add(v1);
		ResultSet rs = null;
		String value = "";
		try {
			rs = db.getRS(sql);
			if (v1.size() == 1) {
				while (rs.next()) {
					value = rs.getString(1);
					list.add(value);
				}
			} else {
				ResultSetMetaData rsm = rs.getMetaData();
				//System.out.println(rsm.getColumnCount());
				while (rs.next()) {
					Vector v = new Vector();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						if (rsm.getColumnType(i) == java.sql.Types.NUMERIC) {
							value = CharSet.nullToEmpty(rs.getString(i));

							if (value.startsWith("."))
								value = "0" + value;
						} else {
							value = CharSet.nullToEmpty(rs.getString(i));
						}
						if (i == 1 && value.equals("")) {
							value = "(Result)";
						}
						if ( i == 2) { //第二列之后补上一列
							
							v.add(value);
							//此处只有用户态数据,没有全局DBA数据,功能需后补,无法得到全局约束所有者名称
							String keyIdSql = "select ucc.column_name from user_cons_columns ucc " +
											" where ucc.owner = '" + ub.getUsername().toUpperCase() + "' " +
											" and ucc.constraint_name = '" + CharSet.nullToEmpty(rs.getString(1)) + "'";
							value = CharSet.nullToEmpty(getKeyID(keyIdSql, db).toString());
						}
						v.add(value);

					}
					list.add(v);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public List getIndexs(String sql, Vector v1) throws Exception {
		// String[] columnName=column.split(",");
		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();
		
		List list = new ArrayList();
		list.add(v1);
		ResultSet rs = null;
		String value = "";
		try {
			rs = db.getRS(sql);
			if (v1.size() == 1) {
				while (rs.next()) {
					value = rs.getString(1);
					list.add(value);
				}
			} else {
				ResultSetMetaData rsm = rs.getMetaData();
				//System.out.println(rsm.getColumnCount());
				while (rs.next()) {
					Vector v = new Vector();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						if (rsm.getColumnType(i) == java.sql.Types.NUMERIC) {
							value = CharSet.nullToEmpty(rs.getString(i));

							if (value.startsWith("."))
								value = "0" + value;
						} else {
							value = CharSet.nullToEmpty(rs.getString(i));
						}
						if (i == 1 && value.equals("")) {
							value = "(Result)";
						}
						if ( i == 3) {  //第三列之后补上一列
							
							v.add(value);
							//此处只有用户态数据,没有全局DBA数据,功能需后补,无法得到全局索引所有者名称
							String IndexIdSql = "select column_name from user_ind_columns where index_name='" + CharSet.nullToEmpty(rs.getString(2)) + "'";
							/*
							String returnvalue = getKeyID(IndexIdSql, db1).toString();
							if(returnvalue.indexOf("SYS_NC") != -1) {
								IndexIdSql = "select column_expression from user_ind_expressions where index_name='" + CharSet.nullToEmpty(rs.getString(2)) + "'";
							}
							*/
							value = CharSet.nullToEmpty(getKeyID(IndexIdSql, db).toString());
							//System.out.println(value);
						}
						v.add(value);

					}
					list.add(v);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * <p>该方法得到当前库中所有用户对象名称与类型
	 */
	public List getUserObject(){
		String sql = "select object_name,object_type from user_objects";
		HttpServletRequest request = WebContextFactory.get()
		.getHttpServletRequest();
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		Database db = ub.getDb();

		List list = new ArrayList();
		try {
			
			list = QueryManage.getResult(sql, db);
			
		} catch (Exception e) {
			//throw e;
		}
		return list;
	}
	
	
	/*
	 * select tt.tablespace_name, tt.initial_extent/1024/1024 initial_extent, tt.pct_free, tt.next_extent, tt.pct_used, tt.pct_increase,
	tt.ini_trans, tt.min_extents, tt.max_trans, tt.max_extents from user_tables tt where table_name='T_CORP'
	 */
	public String getAllpropperty(String sql, Database db) {
		try {
			List list = QueryManage.getResult(sql, db);
			for (int i=0; i< list.size(); i++) {
				 Vector vector = (Vector) list.get(0);
				 getTablespace = (String) vector.get(0);
				 getInitExtent =  (String) vector.get(1);
				getFree =  (String) vector.get(2);
				getNextExtent =  (String) vector.get(3);
				getUsed =  (String) vector.get(4);
				getIncrease =  (String) vector.get(5);
				getIniTrans =  (String) vector.get(6);
				getMinExtent =  (String) vector.get(7);
				getMaxTrans =  (String) vector.get(8);
				getMaxExtent =  (String) vector.get(9);
				getClusterName =  null;
				getClusterColumns = null;
				getComment =  (String) vector.get(10);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getGetTablespace() {
		return getTablespace;
	}

	public void setGetTablespace(String getTablespace) {
		this.getTablespace = getTablespace;
	}

	public String getGetInitExtent() {
		return getInitExtent;
	}

	public void setGetInitExtent(String getInitExtent) {
		this.getInitExtent = getInitExtent;
	}

	public String getGetFree() {
		return getFree;
	}

	public void setGetFree(String getFree) {
		this.getFree = getFree;
	}

	public String getGetNextExtent() {
		return getNextExtent;
	}

	public void setGetNextExtent(String getNextExtent) {
		this.getNextExtent = getNextExtent;
	}

	public String getGetUsed() {
		return getUsed;
	}

	public void setGetUsed(String getUsed) {
		this.getUsed = getUsed;
	}

	public String getGetIncrease() {
		return getIncrease;
	}

	public void setGetIncrease(String getIncrease) {
		this.getIncrease = getIncrease;
	}

	public String getGetIniTrans() {
		return getIniTrans;
	}

	public void setGetIniTrans(String getIniTrans) {
		this.getIniTrans = getIniTrans;
	}

	public String getGetMinExtent() {
		return getMinExtent;
	}

	public void setGetMinExtent(String getMinExtent) {
		this.getMinExtent = getMinExtent;
	}

	public String getGetMaxTrans() {
		return getMaxTrans;
	}

	public void setGetMaxTrans(String getMaxTrans) {
		this.getMaxTrans = getMaxTrans;
	}

	public String getGetMaxExtent() {
		return getMaxExtent;
	}

	public void setGetMaxExtent(String getMaxExtent) {
		this.getMaxExtent = getMaxExtent;
	}

	public String getGetClusterName() {
		return getClusterName;
	}

	public void setGetClusterName(String getClusterName) {
		this.getClusterName = getClusterName;
	}

	public String getGetClusterColumns() {
		return getClusterColumns;
	}

	public void setGetClusterColumns(String getClusterColumns) {
		this.getClusterColumns = getClusterColumns;
	}

	public String getGetComment() {
		return getComment;
	}

	public void setGetComment(String getComment) {
		this.getComment = getComment;
	}
	
}
