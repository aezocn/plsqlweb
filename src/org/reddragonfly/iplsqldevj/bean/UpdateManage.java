package org.reddragonfly.iplsqldevj.bean;

import org.reddragonfly.iplsqldevj.bean.Database;

/**
 * <p>数据库操作实用类
 * @author jeanwendy
 * @date 2008.11.17
 */
public class UpdateManage {
	
	public static int doUpdate(String sql,Database db) throws Exception{
		return db.execSqlUpdate(sql);
	}
	
	public static int doUpdate(String sql,Object[] objs,Database db) throws Exception{
		return db.execPrepareSqlUpdate(sql, objs);
	}
	
}

