package org.reddragonfly.iplsqldevj.bean;

/**
 * 字符转换类
 */
public class CharSet {

  /**
   * 转换成简体中文gb2312
   * @param unicodeStr - 待转换的字符串
   * @return String
   */
  public static final String toGB2312(String unicodeStr) {
    try {
      if (unicodeStr == null) {
        return null;
      }
      else {
        return new String(unicodeStr.getBytes("ISO8859_1"), "Gb2312");
      }
    }
    catch (Exception e) {
      return null;
    }

  }

  public static final String toGBK(String unicodeStr) {
    try {
      if (unicodeStr == null) {
        return null;
      }
      else {
        return new String(unicodeStr.getBytes("ISO8859_1"), "GBK");
      }
    }
    catch (Exception e) {
      return null;
    }

  }

  /**
   * 转换成简体中文gb2312
   * @param unicodeStr - 待转换的字符串
   * @return String
   */
  public static final String toISO8859(String str) {
    try {
      if (str == null) {
        return null;
      }
      else {
        return new String(str.getBytes("Gb2312"), "ISO8859_1");
      }
    }
    catch (Exception e) {
      return null;
    }

  }

  /**
   * 将字符串转换为unicode码
   * @param str
   * @return
   */
  public static String toUnicode(String str) {
    try {
      if (str == null) {
        return null;
      }
      else {
        return new String(str.getBytes("Unicode"), "Unicode");
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  /**
   * 空值转换成空避免空值异常的发生,和填写数据库的时候使用
   * @param str
   * @return String
   */
  public static final String nullToEmpty(String str) {
    if (str == null || str.trim().equals("")) {
      return "";
    }
    else {
      return str;
    }

  }

  /**
   * 把空值转换成空格使用,适合于网页的填写使用
   * @param str - 需要转换的字符
   * @return String
   */
  public static final String nullToSpace(String str) {
    if (str == null || str.trim().equals("")) {
      return " ";
    }
    else {
      return str;
    }

  }

  /**
   * boolean型转换成数字型的(主要是数据库,oracle中没有boolean型的字段)
   * 所有的java中使用boolean型的
   * oracle数据库中使用1代表true使用0代表false
   * @param boolean - boolean型的变量(true, false)
   * @return int
   * @throws Exception
   */
  public static final int boolean2number(boolean Boolean) {
    if (Boolean) {
      return 1;
    }
    else {
      return 0;
    }
  }

  /**
   * 数字型的转换成boolean型的
   * 1转成true
   * 0转成false
   *
   * @param number - 数字
   * @return Boolean
   * @throws Exception
   */
  public static final boolean number2boolean(int number) {
    if (number == 1) {
      return true;
    }
    else {
      return false;
    }
  }
}