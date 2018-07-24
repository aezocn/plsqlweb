package org.reddragonfly.iplsqldevj.bean;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.reddragonfly.iplsqldevj.bean.CharSet;
import org.reddragonfly.iplsqldevj.bean.Database;

/**
 * <p>数据库操作实用类
 * @author jeanwendy
 * @date 2008.11.17
 */
public class QueryManage {

	public static List getResult(String sql,Database db) throws Exception{
		ResultSet rs = null;
		try {
			List list = new ArrayList();
			rs = db.getRS(sql);
			ResultSetMetaData rsm = rs.getMetaData();
			while (rs.next()) {
				Vector v = new Vector();
				for (int i = 0; i < rsm.getColumnCount(); i++) {
					String value = CharSet.nullToEmpty(rs.getString(i + 1));
					int type = rsm.getColumnType(i + 1);
					if(type == java.sql.Types.NUMERIC && value.startsWith(".")){
						value = "0" + value;
					}
					v.add(value);
				}
				list.add(v);
			}
			return list;
		} catch (Exception e) {
			throw e;
		}finally {
			if (rs != null) {
				db.close(rs);
			}
		}
	}
	
}
