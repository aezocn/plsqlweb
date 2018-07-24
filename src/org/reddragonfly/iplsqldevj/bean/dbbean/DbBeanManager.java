package org.reddragonfly.iplsqldevj.bean.dbbean;

public class DbBeanManager {
	
	protected static String[] CFIELDS = 
    {"Function","Procedure","Package","Package body","Type","Type body",
	"Trigger","Java source","Job","Queue","Queue table","Library","Directory","Table","View","Materialized view",
	"Sequence","User","Profile","Role","Synonym","Database link","Tablespace","Cluster"};
	
	public static String getTreeXml(String type,String name,String field){
		if(type == null) type = "";
		if(name == null) name = "";
		if(field == null) field = "";
		DbBean dbbean = null;
		if(type.toLowerCase().equals(DbRootBean.TYPE))dbbean = new DbRootBean(name);
		else if(type.toLowerCase().equals(DbFunctionBean.TYPE)) dbbean = new DbFunctionBean(name);
		else if(type.toLowerCase().equals(DbProcedureBean.TYPE)) dbbean = new DbProcedureBean(name);
		else if(type.toLowerCase().equals(DbPackageBean.TYPE)) dbbean = new DbPackageBean(name);
		else if(type.toLowerCase().equals(DbPackagebodyBean.TYPE)) dbbean = new DbPackagebodyBean(name);
		else if(type.toLowerCase().equals(DbTypeBean.TYPE)) dbbean = new DbTypeBean(name);
		else if(type.toLowerCase().equals(DbTypebodyBean.TYPE)) dbbean = new DbTypebodyBean(name);
		else if(type.toLowerCase().equals(DbTriggerBean.TYPE)) dbbean = new DbTriggerBean(name);
		else if(type.toLowerCase().equals(DbJavaSourceBean.TYPE)) dbbean = new DbJavaSourceBean(name);
		else if(type.toLowerCase().equals(DbJobBean.TYPE)) dbbean = new DbJobBean(name);
		else if(type.toLowerCase().equals(DbQueueBean.TYPE)) dbbean = new DbQueueBean(name);
		else if(type.toLowerCase().equals(DbQueueTableBean.TYPE)) dbbean = new DbQueueTableBean(name);
		else if(type.toLowerCase().equals(DbLibraryBean.TYPE)) dbbean = new DbLibraryBean(name);
		else if(type.toLowerCase().equals(DbDirectoryBean.TYPE)) dbbean = new DbDirectoryBean(name);
		else if(type.toLowerCase().equals(DbTableBean.TYPE)) dbbean = new DbTableBean(name);
		else if(type.toLowerCase().equals(DbViewBean.TYPE)) dbbean = new DbViewBean(name);
		else if(type.toLowerCase().equals(DbMaterializedViewBean.TYPE)) dbbean = new DbMaterializedViewBean(name);
		else if(type.toLowerCase().equals(DbSequenceBean.TYPE)) dbbean = new DbSequenceBean(name);
		else if(type.toLowerCase().equals(DbUserBean.TYPE)) dbbean = new DbUserBean(name);
		else if(type.toLowerCase().equals(DbProfileBean.TYPE)) dbbean = new DbProfileBean(name);
		else if(type.toLowerCase().equals(DbRoleBean.TYPE)) dbbean = new DbRoleBean(name);
		else if(type.toLowerCase().equals(DbSynonymBean.TYPE)) dbbean = new DbSynonymBean(name);
		else if(type.toLowerCase().equals(DbDatabaseLinkBean.TYPE)) dbbean = new DbDatabaseLinkBean(name);
		else if(type.toLowerCase().equals(DbTablespaceBean.TYPE)) dbbean = new DbTablespaceBean(name);
		else if(type.toLowerCase().equals(DbClusterBean.TYPE)) dbbean = new DbClusterBean(name);
		if(dbbean == null) return "<?xml version=\"1.0\" encoding=\"utf-8\"?><tree><tree text=\"Nodata\" /></tree>";
		if(field.trim().equals("")) return dbbean.getTreeXml();
		else return dbbean.getFieldTreeXml(field);
	}
	
	public static String getMenuScript(String type,String name,String field){
		if(type == null) type = "";
		if(name == null) name = "";
		if(field == null) field = "";
		DbBean dbbean = null;
		if(type.toLowerCase().equals(DbRootBean.TYPE))dbbean = new DbRootBean(name);
		else if(type.toLowerCase().equals(DbFunctionBean.TYPE)) dbbean = new DbFunctionBean(name);
		else if(type.toLowerCase().equals(DbProcedureBean.TYPE)) dbbean = new DbProcedureBean(name);
		else if(type.toLowerCase().equals(DbPackageBean.TYPE)) dbbean = new DbPackageBean(name);
		else if(type.toLowerCase().equals(DbPackagebodyBean.TYPE)) dbbean = new DbPackagebodyBean(name);
		else if(type.toLowerCase().equals(DbTypeBean.TYPE)) dbbean = new DbTypeBean(name);
		else if(type.toLowerCase().equals(DbTypebodyBean.TYPE)) dbbean = new DbTypebodyBean(name);
		else if(type.toLowerCase().equals(DbTriggerBean.TYPE)) dbbean = new DbTriggerBean(name);
		else if(type.toLowerCase().equals(DbJavaSourceBean.TYPE)) dbbean = new DbJavaSourceBean(name);
		else if(type.toLowerCase().equals(DbJobBean.TYPE)) dbbean = new DbJobBean(name);
		else if(type.toLowerCase().equals(DbQueueBean.TYPE)) dbbean = new DbQueueBean(name);
		else if(type.toLowerCase().equals(DbQueueTableBean.TYPE)) dbbean = new DbQueueTableBean(name);
		else if(type.toLowerCase().equals(DbLibraryBean.TYPE)) dbbean = new DbLibraryBean(name);
		else if(type.toLowerCase().equals(DbDirectoryBean.TYPE)) dbbean = new DbDirectoryBean(name);
		else if(type.toLowerCase().equals(DbTableBean.TYPE)) dbbean = new DbTableBean(name);
		else if(type.toLowerCase().equals(DbViewBean.TYPE)) dbbean = new DbViewBean(name);
		else if(type.toLowerCase().equals(DbMaterializedViewBean.TYPE)) dbbean = new DbMaterializedViewBean(name);
		else if(type.toLowerCase().equals(DbSequenceBean.TYPE)) dbbean = new DbSequenceBean(name);
		else if(type.toLowerCase().equals(DbUserBean.TYPE)) dbbean = new DbUserBean(name);
		else if(type.toLowerCase().equals(DbProfileBean.TYPE)) dbbean = new DbProfileBean(name);
		else if(type.toLowerCase().equals(DbRoleBean.TYPE)) dbbean = new DbRoleBean(name);
		else if(type.toLowerCase().equals(DbSynonymBean.TYPE)) dbbean = new DbSynonymBean(name);
		else if(type.toLowerCase().equals(DbDatabaseLinkBean.TYPE)) dbbean = new DbDatabaseLinkBean(name);
		else if(type.toLowerCase().equals(DbTablespaceBean.TYPE)) dbbean = new DbTablespaceBean(name);
		else if(type.toLowerCase().equals(DbClusterBean.TYPE)) dbbean = new DbClusterBean(name);
		if(dbbean == null) return "";
		if(field.trim().equals("")) return dbbean.getMenuScript();
		else return dbbean.getFieldMenuScript(field);
	}
	
	public static String getChildMenuIcon(String subType, String status){
		String icon = "dbimages/notype.png";
		String inValidIcon = "";
		if (subType.equals(CFIELDS[0].toUpperCase())) {		//Functions
			icon = DbFunctionBean.ICON_VALID;
			inValidIcon = DbFunctionBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[1].toUpperCase())) {	//Procedures
			icon = DbProcedureBean.ICON_VALID;
			inValidIcon = DbProcedureBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[2].toUpperCase())) {	//Packages
			icon = DbPackageBean.ICON_VALID;
			inValidIcon = DbPackageBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[3].toUpperCase())) {	//Package Bodies
			icon = DbPackagebodyBean.ICON_VALID;
			inValidIcon = DbPackagebodyBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[4].toUpperCase())) {	//Types
			icon = DbTypeBean.ICON_VALID;
			inValidIcon = DbTypeBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[5].toUpperCase())) {	//Type Bodies
			icon = DbTypebodyBean.ICON_VALID;
			inValidIcon = DbTypebodyBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[6].toUpperCase())) {	//Triggers
			icon = DbTriggerBean.ICON_VALID;
			inValidIcon = DbTriggerBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[7].toUpperCase())) {	//Procedure
			icon = DbJavaSourceBean.ICON_VALID;
			inValidIcon = DbJavaSourceBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[8].toUpperCase())) {	//Jobs
			icon = DbJobBean.ICON_VALID;
			inValidIcon = DbJobBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[9].toUpperCase())) {	//Queues
			icon = DbQueueBean.ICON_VALID;
			inValidIcon = DbQueueBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[10].toUpperCase())) {	//Queue Tables
			icon = DbQueueTableBean.ICON_VALID;
			inValidIcon = DbQueueTableBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[11].toUpperCase())) {	//Libraries
			icon = DbLibraryBean.ICON_VALID;
			inValidIcon = DbLibraryBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[12].toUpperCase())) {	//Directories
			icon = DbDirectoryBean.ICON_VALID;
			inValidIcon = DbDirectoryBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[13].toUpperCase())) {	//Tables
			icon = DbTableBean.ICON_VALID;
			inValidIcon = DbTableBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[14].toUpperCase())) {	//Views
			icon = DbViewBean.ICON_VALID;
			inValidIcon = DbViewBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[15].toUpperCase())) {	//Materialized views
			icon = DbMaterializedViewBean.ICON_VALID;
			inValidIcon = DbMaterializedViewBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[16].toUpperCase())) {	//Sequences
			icon = DbSequenceBean.ICON_VALID;
			inValidIcon = DbSequenceBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[17].toUpperCase())) {	//Users
			icon = DbUserBean.ICON_VALID;
			inValidIcon = DbUserBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[19].toUpperCase())) {	//Roles
			icon = DbRoleBean.ICON_VALID;
			inValidIcon = DbRoleBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[20].toUpperCase())) {	//Synonyms
			icon = DbSynonymBean.ICON_VALID;
			inValidIcon = DbSynonymBean.ICON_INVALID;
		} else if (subType.equals(CFIELDS[23].toUpperCase())) {	//Clusters
			icon = DbClusterBean.ICON_VALID;
			inValidIcon = DbClusterBean.ICON_INVALID;
		}
		if("INVALID".equals(status)) icon = inValidIcon;
		return icon;
	}
}
