package org.reddragonfly.iplsqldevj;

public class Utilities {
	
	public static String getVersion(){
		return "Version 1.3.2.0628";
	}
	
	public static String getTeam() {
		String team ="	<font color=#000000"
					+"		face=\"Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif\">Copyright"
					+"		<a href=\"http://www.reddragonfly.org\" target=\"_blank\"><font color=#000000"
					+"			face=\"Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif\">Reddragonfly"
					+"				&amp; Studio</font> </a> &copy; 2007-2013 Code By </font>"
					+"	<a href=\"http://iplsqldeveloper.sf.net\" target=\"_blank\"> <font"
					+"		color=#000000"
					+"		face=\"Verdana, Tahoma, Times New Roman, 宋体, MS Sans Serif\">iPlsqldeveloper Team</font>"
					+"	</a>";
		return team;
	}
	
	/**
	 * <p>用newValue替换src字符串中的所有targetValue，该方法不考虑正则表达式的情况
	 * @param src
	 * @param targetValue
	 * @param newValue
	 * @return src字符串的一个副本，src字符串实例将不会被改变
	 */
	public static String replaceAll(String src,String targetValue,String newValue){
		String returnVal = new String(src);
		while(returnVal.indexOf(targetValue) != -1){
			returnVal = returnVal.replaceAll(targetValue, newValue);
		}
		return returnVal;
	}
	
}
