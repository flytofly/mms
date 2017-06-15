package cn.mmdata.commons.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final String replace(String line, String oldString,
			String newString) {
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			switch (ch) {
			case '<':
				buf.append("&lt;");
				break;
			case '>':
				buf.append("&gt;");
				break;
			case '&':
				buf.append("&amp;");
				break;
			case '"':
				buf.append("&quot;");
				break;
			default:
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static final String escapeJS(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			switch (ch) {
			case '<':
				buf.append("&lt;");
				break;
			case '>':
				buf.append("&gt;");
				break;
			case '&':
				buf.append("&amp;");
				break;
			case '"':
				buf.append("&quot;");
				break;
			case '\\':
				buf.append("\\\\");
				break;
			case '\r':
				buf.append("\\r");
				break;
			case '\n':
				buf.append("\\n");
				break;
			default:
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static final String escapeJsHtml(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			switch (ch) {
			case '<':
				buf.append("&lt;");
				break;
			case '>':
				buf.append("&gt;");
				break;
			case '&':
				buf.append("&amp;");
				break;
			case '"':
				buf.append("&quot;");
				break;
			case '\\':
				buf.append("\\\\");
				break;
			case '\r':
				buf.append("\\r");
				break;
			case '\n':
				buf.append("\\n");
				break;
			case '\'':
				buf.append("\\'");
				break;
			default:
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	private static MessageDigest digest = null;

	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. ");
				nsae.printStackTrace();
			}
		}
		digest.update(data.getBytes());
		return toHex(digest.digest());
	}

	public static final String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		String stmp = "";
		for (int i = 0; i < hash.length; i++) {
			stmp = (java.lang.Integer.toHexString(hash[i] & 0XFF));
			if (stmp.length() == 1)
				buf.append(0).append(stmp);
			else
				buf.append(stmp);
		}
		return buf.toString();
	}

	public static final String notNull(String text, String defaultText) {
		return isNull(text) ? defaultText : text;
	}

	public static final boolean isNull(String text) {
		if ("".equals(text) || text == null) {
			return true;
		} else {
			return false;
		}
	}

	public static final byte[] hexToBytes(String hex) {
		if (null == hex)
			return new byte[0];
		int len = hex.length();
		byte[] bytes = new byte[len / 2];
		String stmp = null;
		try {
			for (int i = 0; i < bytes.length; i++) {
				stmp = hex.substring(i * 2, i * 2 + 2);
				bytes[i] = (byte) Integer.parseInt(stmp, 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
		return bytes;
	}

	public static final String escapeForXML(String string) {
		if (string == null || string.length() == 0) {
			return string;
		}
		char[] sArray = string.toCharArray();
		StringBuffer buf = new StringBuffer(sArray.length);
		char ch;
		for (int i = 0; i < sArray.length; i++) {
			ch = sArray[i];
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	public static final String arrToString(String[] str) {
		String x = "";
		for (int i = 0; i < str.length; i++) {
			x = x + str[i];
			if (i < str.length - 1) {
				x = x + ",";
			}
		}
		return x;
	}

	public static final String listToString(List<String> list) {
		StringBuffer str = new StringBuffer("");
		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			str.append(iter.next());
			if (iter.hasNext()) {
				str.append(",");
			}
		}
		return str.toString();
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isString(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String formatTitle(int len, String value) throws Exception {
		if (value == null) {
			return "";
		}
		if (value.getBytes("GBK").length > len && len - 3 > 0) {
			return value.substring(0, len - 3) + "...";
		}
		return value;
	}

	public static String nullToVarchar(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否乱码
	 * @param strName
	 * @return
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
//		float chLength = ch.length;
//		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
//					count = count + 1;
					return true;
				}
			}
		}
//		float result = count / chLength;
//		if (result > 0.4) {
//			return true;
//		} else {
//			return false;
//		}
		return false;
	}

    /**
     * 进行字符串替换
     * @param source 要进行替换操作的字符串
     * @param from   要替换的字符串
     * @param to     要替换成的字符串
     * @return 替换后的字符串
     */
    static public String replaceAll(String source, String from, String to)
	 {
	  if ( (source == null) || source.equals("") || (from == null) ||
	      (to == null) || from.equals("") || from.equals(to)) {
	       return source;
	  }

	  StringBuffer sb = new StringBuffer(source.length());
	  String s = source;
	  int index = s.indexOf(from);
	  int fromLen = from.length();

	  while (index != -1) {
	       sb.append(s.substring(0, index));
	       sb.append(to);
	       s = s.substring(index + fromLen);
	       index = s.indexOf(from);
	  }

	  return sb.append(s).toString();
    }

	 /**
	 * 对字符串做UTF8编码
	 */
	 static public String utf8Enc(String src)
	 {
		 String result = "";
		 try {
		 	result = URLEncoder.encode(src, "utf-8");
		 }
		 catch (Exception ex) {
		 }
		 return result;
	 }

	public static int parseInt(String str, int defaultValue){
		if(str==null){
			return defaultValue;
		}
		try{
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e){
			return defaultValue;
		}
	}

	public static double parseDouble(String str, double defaultValue){
		if(str==null){
			return defaultValue;
		}
		try{
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e){
			return defaultValue;
		}
	}

	public static String parseString(String str, String defaultValue) {
		if (str == null) {
			return defaultValue;
		}

		return str;
	}

	/**
	* 返回独立的String列表
	*/
	public static String[] getDistinctString(String[] res)
	{
		HashSet<String> hs = new HashSet<String>();
		ArrayList<String> results = new ArrayList<String>();

		for (int i=0; i < res.length; i++)
		{
			String str = res[i];
			if (!hs.contains(str)) {
				hs.add(str);
				results.add(str);
			}
		}

		return (String[]) results.toArray(new String[0]);
	}

	/**
	* 获取百分数格式数据
	*/
	public static String getDecimalFormat(float value, int digitNum)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("0.");
		for (int i=0; i < digitNum; i++) {
			sb.append("0");
		}

		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(value*100) + "%";
	}

	/**
	* 获取百分数格式数据
	*/
	public static String getDecimalFormat(String value, int digitNum)
	{
		Float f = 0.0f;

		try {
			f = Float.parseFloat(value);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return getDecimalFormat(f, digitNum);
	}
}
