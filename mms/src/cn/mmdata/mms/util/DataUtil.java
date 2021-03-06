package cn.mmdata.mms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;  
	
public class DataUtil
{
	private static final int _NORMAL_PART = 1;
	private static final int _INVALID_TRUNK_PART = 2;
	private static final int _PURE_DIGIT_PART = 3;

	private static final int _INVALID_CHAR_LIMIT = 3;
	private static final int _INVALID_DIGIT_LIMIT = 8;
	private static final int _INVALID_MISC_DIGIT_LIMIT = 6;
	private static final int _INVALID_MISC_LEN_LIMIT = 15;

	private static final String _SEPA = "/";

	public static void debug(String info)
	{
		System.out.println(info + ", " + getNowStr());
	}

	/**
	* 写入调试日志
	*/
	public static void debug2(String info)
	{
		final String _LOG_FILE = "/tmp/host-figure.log";
		writeFile(_LOG_FILE, info);
	}

	/**
	* 获取增加日期的文件名
	*/
	public static String getDateName(String file)
	{
		SimpleDateFormat df = new SimpleDateFormat("MMdd");
		String date = df.format(new Date());

		int pos = file.indexOf(".");
		if (pos >= 0) {
			return file.substring(0, pos) + "-" + date + file.substring(pos);
		} else {
			return file + "-" + date;
		}
	}

	/**
	* 获取增加日期的文件名
	* @file 待调整的文件名称，例如 host-out.txt
	* @date 补充的日期信息，例如 20180819
	*/
	public static String getDateName(String file, String date)
	{
		int pos = file.lastIndexOf(".");
		if (pos >= 0) {
			return file.substring(0, pos) + "-" + date + file.substring(pos);
		} else {
			return file + "-" + date;
		}
	}

	/**
	* 获取精简的日期格式
	* @date  示例形式: 20
	*/
	public static String shortDate(String date)
	{
		final int _MIN_LEN = 5;
		
		if (date == null) { return null; }
		if (date.length() < _MIN_LEN) { return date; }

		return date.substring(_MIN_LEN);
	}
	
	/**
	* 获取当前时间
	*/
	public static String getNowDate()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	* 获取当前时段
	*/
	public static String getNowHour()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
		return df.format(new Date());
	}

	/**
	* 获取当前时间
	*/
	public static String getNowStr()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return df.format(new Date());
	}

	/**
	* 获取唯一时间标记
	*/
	public static String getNowSign()
	{
		SimpleDateFormat df = new SimpleDateFormat("ddHHmmss");
		return df.format(new Date());	
	}


	/**
	* 解析行内容获取字段值
	*/
	public static String getValue(String line, String field)
	{
		String tmp = field + "=\"";

		int pos1 = line.indexOf(tmp);
		if (pos1 < 0) { return null; }

		int pos2 = line.indexOf("\"", pos1 + tmp.length());
		if (pos2 < 0) { return null; }

		return line.substring(pos1 + tmp.length(), pos2);
	}


	public static int strToInt(String str)
	{
		if (str == null) { return 0; }

		Integer obj;
		try {
			obj = new Integer(str);		
		}
		catch (Exception ex) {
			return 0;
		}

		return obj.intValue();
	}

	/**
	* MD5加密
	*/
    public static String string2MD5(String inStr)
	{
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }

	public static void writeFile(String fileName, String content)
	{
		boolean withReturn = true;
		boolean appending = true;
		writeFile(fileName, content, "utf-8", withReturn, appending);
	}

	public static void writeFileWithoutAppend(String fileName, String content)
	{
		boolean withReturn = true;
		boolean appending = false;
		writeFile(fileName, content, "utf-8", withReturn, appending);
	}

	public static void writeFileWithoutReturn(String fileName, String content)
	{
		boolean withReturn = false;
		boolean appending = true;
		writeFile(fileName, content, "utf-8", withReturn, appending);
	}

	/**
	* 判断文件是否存在
	*/
	public static boolean existFile(String fileName)
	{
		File file = new File(fileName);
		return file.exists();
	}

	/**
	* 判断文件所在目录是否存在
	*/
	private static boolean existFileDir(String fileName)
	{
		File file = new File(fileName);
		File parent = file.getParentFile();
		if (parent == null) {
			return true;
		}

		return parent.exists();
	}

	/**
	* 创建文件所述的上级父目录
	*/
	private static void createFileDir(String fileName)
	{
		try
		{
			File file = new File(fileName);
			File parent = file.getParentFile();
			parent.mkdirs();			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	* 删除文件
	*/
	public static void deleteFile(String fileName)
	{
		try
		{
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	* 写文件
	*/
	public static void writeFile(String fileName, String content, String encoding, boolean withReturn, boolean appending)
	{
		if (!existFileDir(fileName)) {
			createFileDir(fileName);
		}

		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter( new FileOutputStream(fileName, appending), encoding) );
			if (withReturn) {
				pw.println(content);
			} else {
				pw.print(content);
			}
			pw.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void writeFile(String fileName, String[] contentList)
	{
		writeFile(fileName, contentList, "utf-8");
	}
	
	/**
	* 写文件
	*/
	public static void writeFile(String fileName, String[] contentList, String encoding)
	{
		if (!existFileDir(fileName)) {
			createFileDir(fileName);
		}

		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter( new FileOutputStream(fileName, true), encoding) );
			for (int i=0; i < contentList.length; i++) {
				pw.println(contentList[i]);
			}
			pw.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	* 获取UTF-8编码的文件内容
	*/
	public static String readFile(String fileName)
	{
		return readFile(fileName, "utf-8");
	}

	/**
	* 获取文件内容
	*/
	public static String readFile(String fileName, String encoding)
	{
		if (fileName == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), encoding));
			
			String bufferLine;
			while ( (bufferLine = br.readLine()) != null) {
				sb.append(bufferLine).append("\n");
			}
			br.close();
		}
		catch (Exception e) {
			return null;
		}

		return sb.toString();
	}

	/**
	* 获取文件内容数组
	*/
	public static String[] readFileArray(String fileName, String encoding)
	{
		if (fileName == null) {
			return null;
		}

		ArrayList<String> results = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), encoding));
			
			String bufferLine;
			while ( (bufferLine = br.readLine()) != null) {
				results.add(bufferLine.trim());
			}
			br.close();
		}
		catch (Exception e) {
			return null;
		}

		return (String[]) results.toArray(new String[0]);
	}
	

	/**
	* 读取文件，按照日期信息做过滤
	*/
	public static String readFileWithFilter(String fileName, String filter)
	{
		String encoding = "utf-8";
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), encoding));
			
			String line;
			while ( (line = br.readLine()) != null)
			{
				if (line.indexOf(filter) < 0) { continue; }

				sb.append(line).append("\n");
			}
			br.close();
		}
		catch (Exception e) {
			return null;
		}

		return sb.toString();
	}


	/**
	* 从文件名获取日期路径
	*/
	public static String getDatePath(String name)
	{
		int start = name.lastIndexOf("_");
		if (start < 0) { return null; }
		
		int end = name.indexOf(".");
		if (end < 0) {
			return name.substring(start + 1);
		} else {
			return name.substring(start + 1, end);
		}
	}

	/**
	* 从文件名获取省份路径
	*/
	public static String getProvincePath(String name)
	{
		int start = name.indexOf("_");
		if (start < 0) { return null; }
		
		int end = name.lastIndexOf("_");
		if (end < 0) { return null; }
		
		return name.substring(start + 1, end);
	}

	/**
	* 从文件名获取前缀部分
	*/
	public static String getFileBase(String name)
	{
		int pos = name.indexOf(".");
		if (pos < 0 ) { return null; }
		
		return name.substring(0, pos);
	}

	/**
	* 获取规范化的定义路径
	*/
	public static String getRightPath(String path)
	{
		if (path == null) { return null; }
	
		String endChar = "/";
		/*
		if (!DataConfig.getOS().equalsIgnoreCase("linux")) {
			endChar = "\\";
		}
		*/

		if (!path.endsWith(endChar)) { 
			return path += endChar;
		} else {
			return path;
		}
	}

	/**
	* 获取结合后的路径
	*/
	public static String getAbsPath(String path, String file)
	{
		if (path.endsWith("/")) {
			return path + file;
		} else {
			return path + "/" + file;
		}
	}
	

	/**
	* 从Host解析一级域名
	*/
	public static String parseDomainByHost(String host)
	{
		String[] suffixArray = {".com.cn", ".com", ".cn", ".net", ".org", ".se", ".im", ".mobi", ".me"};

		int pos = -1;
		host = host.toLowerCase();
		for (int i=0; i < suffixArray.length; i++)
		{
			String suffix = suffixArray[i];
			if (host.endsWith(suffix)) {
				pos = host.length() - suffix.length();
				break;
			}
		}
		if (pos < 0) { return host; }

		int start = host.lastIndexOf(".", pos - 1);
		if (start < 0) {
			return host;
		} else {
			return host.substring(start+1);
		}
	}


	/**
	* 解析一级域名
	*/
	public static String parseDomain(String url)
	{
		String host = trimPort(parseHost(url));
		return parseDomainByHost(host);
	}

	/**
	* 去除端口号
	*/
	public static String trimPort(String host)
	{
		if (host == null) { return null; }
		
		int pos = host.indexOf(":");
		if (pos >= 0) {
			return host.substring(0, pos);
		} else {
			return host;
		}
	}

	/**
	* 去除串中的所有空格
	*/
	private static String clearSpace(String url)
	{
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < url.length(); i++)
		{
			char ch = url.charAt(i);
			if (ch == ' ' || ch == '\t') {
				continue;
			}
			sb.append(ch);
		}

		return sb.toString();
	}

	/**
	* 从URL内容中获取Host信息
	*/
	public static String parseHost(String url)
	{
		final String _PREFIX = "http://";

		// 去除空格再分析
		url = clearSpace(url);

		int start = 0;
		String tmp = url.toLowerCase();
		if (tmp.startsWith(_PREFIX)) {
			start += _PREFIX.length();
		}

		int end = url.indexOf("/", start);
		if (end < 0) {
			end = url.indexOf("?", start);
			if (end < 0) {
				end = url.indexOf("&", start);
				if (end < 0){
					return url.substring(start);
				}
			}
		}

		String host = url.substring(start, end);
		int pos = host.indexOf(":");
		if (pos >= 0) {
			return host.substring(0, pos);
		} else {
			return host;
		}
	}

	/**
	* 判断URL是否合规
	*/
	public static boolean invalidUrl(String url)
	{
		if (url == null || url.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	* 判断手机号合法性
	*/
	public static boolean invalidMobile(String mobile)
	{
		if (mobile == null || mobile.trim().length() <= 0) { return true; }
		if (mobile.startsWith("106") || mobile.startsWith("145")) { return true; }

		return false;
	}

	/**
	* 获取前一天日期
	* @date 指定日期，格式20160820
	*/
	public static String getBeforeDay(String day)
	{
		return getDay(day, -1);
	}

	/**
	* 获取后一天日期
	* @date 指定日期，格式20160820
	*/
	public static String getNextDay(String day)
	{
		return getDay(day, 1);
	}

	/**
	* 获取指定日期
	* @date 指定日期
	* @distance 目标日期与当前日期的差值
	*/
	public static String getDay(String dayStr, int distance)
	{
		// 创建当前day的日期对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = null; 
		try { 
			date = sdf.parse(dayStr); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 

		// 获取指定日期字符串
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.set(Calendar.DATE, c.get(Calendar.DATE) + distance); 

		return sdf.format(c.getTime());
	}

	/**
	* 获取startDate和endDate之间的日期距离
	*/
	public static int getDayDistance(String startDate, String endDate)
	{
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			Date startDateObj = sdf.parse(startDate);
			Date endDateObj = sdf.parse(endDate);

			int days = (int) (endDateObj.getTime()- startDateObj.getTime())/(1000*3600*24);
			return days;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}
	
	/**
	* 判断ch是否是字母
	*/
	public static boolean isAlpha(char ch)
	{
		if (ch >= 'a' && ch <= 'z') { return true; }
		if (ch >= 'A' && ch <= 'Z') { return true; }
		return false;
	}

	/**
	* 判断ch是否是字母
	*/
	public static boolean isAlphaOrDigit(char ch)
	{
		if (isAlpha(ch)) { return true; }
		if (ch >= '0' && ch <= '9') { return true; }
		if (ch == ' ' || ch == '\t') { return true; }

		return false;
	}

	/**
	* 检查域名是否合理
	*/
	public static boolean invalidHost(String host)
	{
		if (host == null) { return true; }

		if (host.indexOf(".") < 0 || host.indexOf("in-addr") >= 0 ) { return true; }

		// 如果host是IP地址，则不做处理
		for (int i=0; i < host.length(); i++)
		{
			char ch = host.charAt(i);
			if (isAlpha(ch)) {
				return false;
			}
		}
		return true;
			
	}

	/**
	* 判断标签是否合理
	*/
	public static boolean validTag(String tag)
	{
		if (tag == null) { return false; }
		if (tag.trim().equals("null") || tag.indexOf("@") >= 0) { return false; }

		// 判断是否全部为字母或者数字
		for (int i=0; i < tag.length(); i++) {
			char ch = tag.charAt(i);
			if (!isAlphaOrDigit(ch)) {
				return true;
			}
		}

		return false;
	}

	
	/**
	* 对时长做格式化处理
	*/
	public static String formatDuration(int duration)
	{
		final int _UNIT = 60;
	
		// 计算分钟和秒数
		int hour = duration / (_UNIT * _UNIT);
		int minute = (duration - hour * (_UNIT * _UNIT)) / _UNIT;
		int second = duration - hour * (_UNIT *_UNIT) - minute * _UNIT;

		String result = "";
		if (hour > 0) {
			result = hour + "小时";
		}

		if (minute > 0) {
			result = result + minute + "分钟";
		}

		if (second > 0) {
			result = result + second + "秒";
		}

		return result;	
	}


	/**
	* 获取增加日期的文件名
	* @fileName 文件名称， 示例: host-out-2015-11-02.csv.txt
	*/
	public static String getDateFromFile(String fileName)
	{
		// 取尾部文件名
		int pos = fileName.lastIndexOf("\\");
		if (pos < 0) {
			pos = fileName.lastIndexOf("/");
		}
		fileName = fileName.substring(pos + 1);
		
		// 找寻第一个数字
		int i;
		for (i=0; i < fileName.length(); i++) {
			char ch = fileName.charAt(i);
			if (ch >= '0' && ch <= '9') {
				break;
			}
		}

		int start = i;

		// 找寻最后一个数字
		int j;
		for (j=fileName.length()-1; j>=0; j--) {
			char ch = fileName.charAt(j);
			if (ch >= '0' && ch <= '9') {
				break;
			}
		}
		int end = j;

		if (end < start ) {
			return null;
		}

		return fileName.substring(start, end + 1);
		
	}

	/**
	* 从尾部提取文件名中的日期信息
	*/
	public static String getDateFromFileFromTail(String file)
	{
		if (file == null) { return null; }
		
		// 找寻最后一个数字
		int i;
		for (i=file.length()-1; i>=0; i--) {
			char ch = file.charAt(i);
			if (ch >= '0' && ch <= '9') {
				break;
			}
		}
		int end = i;

		// 往前寻找数字
		int j;
		for (j=end; j>=0; j--) {
			char ch = file.charAt(j);
			if ((ch >= '0' && ch <= '9') || ch == '-') {
				continue;
			}
			break;
		}
		int start = j;

		return file.substring(start + 1, end + 1);
	}

	/**
	* 判断host是否顶级域名
	*/
	public static boolean isDomain(String host)
	{
		if (host == null) { return false; }
		
		String domain = parseDomainByHost(host);
		return domain.trim().equalsIgnoreCase(host.trim());
	}

	/**
	* 判断字符串是否全部由数字构成
	*/
	public static boolean isDigitString(String str)
	{
		if (str == null) { return false; }

		int len = str.length();
		for (int i=0; i < len; i++) {
			char ch = str.charAt(i);
			if (ch < '0' || ch > '9') {
				return false;
			}
		}

		return true;
	}

	/**
	* 获取字符串集合Map
	*/
	public static HashMap<String,String> getStringMap(String[] strArray)
	{
		if (strArray == null) { return null; }
		
		HashMap<String,String> map = new HashMap<String,String>();
		for (int i=0; i < strArray.length; i++) {
			map.put(strArray[i], "");
		}

		return map;
	}

	/**
	* 判断URL是否详情页
	*/
	public static boolean urlIsDetailPage(String url)
	{
		String[] _SUFFIX_ARRAY = {".htm", ".html"};
		for (int i=0; i < _SUFFIX_ARRAY.length; i++) {
			if (url.endsWith(_SUFFIX_ARRAY[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	* 获取URL主干
	*/
	public static String getUrlTrunk(String url)
	{
		final String _HTTP_PREFIX = "http://";

		// 去除HTTP前缀
		if (url == null) { return null; }
		if (url.startsWith(_HTTP_PREFIX)) {
			url = url.substring(_HTTP_PREFIX.length()); 
		}

		// 取问号前面部分
		int pos = url.indexOf("?");
		if (pos >= 0) {
			url = url.substring(0, pos);
		} else {
			if (urlIsDetailPage(url)) {
				pos = url.lastIndexOf("/");
				if (pos >= 0) {
					url = url.substring(0, pos);
				}
			}
		}

		return cleanTrunk(url);
	}

	/**
	* 判断URL命令是否非法
	*/
	public static boolean invalidTrunk(String trunk)
	{
		final String[] _INVALID_TRUNK_ARRAY = {"*.com"};

		for (int i=0; i < _INVALID_TRUNK_ARRAY.length; i++) {
			if (trunk.indexOf(_INVALID_TRUNK_ARRAY[i]) >= 0) {
				return true;
			}
		}

		return false;
	}

	/**
	* 获取URL中的参数值信息
	*/
	public static String getParaValue(String url, String para)
	{
		String paraStr = para + "=";
		int start = url.indexOf(paraStr);
		if (start < 0) { return null; }

		start += paraStr.length();
		int end = url.indexOf("&", start);
		if (end < 0) {
			return url.substring(start);
		} else {
			return url.substring(start, end);
		}
	}

	/**
	* 获取子段类型
	*/
	private static int getTrunkPartType(String part)
	{
		if (part == null) { return _INVALID_TRUNK_PART; }
	
		int digitCount = 0;
		int invalidCount = 0;
		int len = part.length();
		for (int i=0; i < len; i++)
		{
			char ch = part.charAt(i);
			if (ch >= '0' && ch <= '9') {
				digitCount++;
			}
	
			if (ch == '%') {
				invalidCount++;
			}
		}
	
		if (invalidCount >= _INVALID_CHAR_LIMIT) { return _INVALID_TRUNK_PART; }
		if (digitCount >= _INVALID_DIGIT_LIMIT) { return _INVALID_TRUNK_PART; }
		if (digitCount >= _INVALID_MISC_DIGIT_LIMIT && len >= _INVALID_MISC_LEN_LIMIT) { return _INVALID_TRUNK_PART; }
		if (digitCount == len) { return _PURE_DIGIT_PART; }
		return _NORMAL_PART;
	}
	
	/**
	* 对URL命令做去噪处理
	*/
	public static String cleanTrunk(String trunk)
	{
		if (trunk == null) { return null; }
	
		String[] parts = trunk.split(_SEPA);
		if (parts.length <= 1) { return trunk; }
		
		int i = 0;
		for (; i < parts.length; i++)
		{
			int type = getTrunkPartType( trimPort(parts[i]) );
			if (type == _INVALID_TRUNK_PART) {
				break;
			}
		}
	
		return packParts(parts, i);
	}
	
	/**
	* 组合各模块串
	*/
	private static String packParts(String[] parts, int index)
	{
		if (parts == null) { return null; }
		if (index > parts.length) { return null; }
	
		StringBuffer sb = new StringBuffer();
		int m = index - 1;
		for (;	m >= 0; m--)
		{
			String part = parts[m];
			if (getTrunkPartType(part) != _PURE_DIGIT_PART) {
				break;
			}
		}
	
		// 组装内容串
		for (int i=0; i <= m; i++)
		{
			sb.append(parts[i]);
	
			if (i < m) {
				sb.append(_SEPA);
			}
		}
	
		if (index < parts.length) {
			int pos = parts[index].lastIndexOf(".");
			if (pos >= 0) {
				sb.append(_SEPA).append("*").append( parts[index].substring(pos) );
			}
			
		}
	
		return sb.toString();
	}

	/**
	* 获取时段
	* _time格式：2015-04-18 15:12:03
	* 返回格式: 2015041815
	*/
	public static String parseHour(String time)
	{
		if (time == null) { return null; }

		int pos = time.indexOf(":");
		if (pos < 0) { return time; }
		time = time.substring(0, pos);

		// 组装数字串
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < time.length(); i++)
		{
			char ch = time.charAt(i);
			if (ch >= '0' && ch <= '9') {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	* 获取时段
	* _time格式：2015-04-18 15:12:03
	* 返回格式: 20150418
	*/
	public static String parseDay(String time)
	{
		if (time == null) { return null; }

		int pos = time.indexOf(" ");
		if (pos < 0) { return time; }
		time = time.substring(0, pos);

		// 组装数字串
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < time.length(); i++)
		{
			char ch = time.charAt(i);
			if (ch >= '0' && ch <= '9') {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	* 计算时间差，以秒为单位
	*/
	public static int getTimePeriod(String currTime, String lastTime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long period = 0;
		try {
			Date currDate = df.parse(currTime);
			Date lastDate = df.parse(lastTime);
			period = currDate.getTime() - lastDate.getTime();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return 0;
		}
		
		return (int) (period / 1000);
	}

	/**
	* 获取该时间段的时长值
	* 示例形式: 09:32, 09:34, 09:48, 09:57
	* 访问时长为 2分钟 + 1分钟 + 1分钟 = 4分钟
	*/
	public static int getDuration(String[] visitTimeArray)
	{
		final int _DEFAULT_TIME = 5;	// 以秒为单位，第一条访问时间为5秒
		final int _SESSION_TIME = 300;	// 以秒为单位，默认会话时间为5分钟
		final int _FIX_TIME = 60;	// 以秒为单位，默认停留时间为1分钟

		if (visitTimeArray == null || visitTimeArray.length <= 0) { return 0; }

		// 对访问时间做排序
		String[] rankTimeArray = sort(visitTimeArray);
		String lastTime = rankTimeArray[0];
		int total = _DEFAULT_TIME;
				
		// 计算连续访问的时长
		for  (int i=1; i < rankTimeArray.length; i++) {
			String currTime = rankTimeArray[i];
			int period = getTimePeriod(currTime, lastTime); 			
			if (period <= _SESSION_TIME) {
				total += period;
			} else {
				total += _FIX_TIME;
			}

			lastTime = currTime;
		}

		return total;
	}

	
	/**
	* 对时段做排序处理
	*/
	public static String[] sort(String[] timeArray)
	{
		int limit = (timeArray == null ? 0 : timeArray.length);
		return sort(timeArray, limit);
	}

	/**
	* 对时段做排序处理
	*/
	public static String[] sort(String[] timeArray, int limit)
	{
		final int _MAX_LIMIT = 10000000;
		if (limit < 0) { limit = _MAX_LIMIT; }
		
		// 初始化排序指针数组
		int length = timeArray.length;
		int[] group = new int[length];
		for (int i=0; i < length; i++) {
			group[i] = i;
		}

		// 对数组排序
		for (int m=0; m < length && m < limit; m++) {
			for (int n=m+1; n < length; n++) {
				if (timeArray[group[m]].compareTo( timeArray[group[n]] ) < 0) {
					int tmp = group[m];
					group[m] = group[n];
					group[n] = tmp;
				}
			}
		}

		// 返回排序后对象集合
		ArrayList<String> results = new ArrayList<String>();
		for (int k=0; k < length && k < limit; k++) {
			results.add(timeArray[group[k]]);
		}

		return (String[]) results.toArray(new String[0]);
	}

	/**
	* 获取域名列表字符串
	*/
	public static String getArrayString(String[] strArray, String sepa)
	{
		if (strArray == null) { return ""; }

		StringBuffer sb = new StringBuffer();
		for (int i=0; i < strArray.length; i++)
		{
			sb.append(strArray[i]);
			if (i < strArray.length - 1) {
				sb.append(sepa);
			}
		}

		return sb.toString();
	}

	/**
	* 获取时间格式
	*/
	public static String getTimeFormat(int time)
	{
		int hour = time / 3600;
		time = time - hour * 3600;
	
		int minute = time / 60;
		time = time - minute * 60;
	
		int seconds = time;

		StringBuffer sb = new StringBuffer();
		if (hour > 0 ) {
			sb.append(hour).append("小时");
		}

		if (minute > 0) {
			sb.append(minute).append("分");
		}

		if (seconds > 0) {
			sb.append(seconds).append("秒");
		}

		return sb.toString();
	}

	/**
	* 获取该时间段的会话数
	* 示例形式: 09:32, 09:34, 09:48, 09:57
	* 访问时长为 2分钟 + 1分钟 + 1分钟 = 4分钟
	*/
	public static int getSessionCount(String[] visitTimeArray)
	{
		final int _SESSION_TIME = 300;	// 以秒为单位，默认会话时间为5分钟
//		final int _FIX_TIME = 60;	// 以秒为单位，默认停留时间为1分钟

		if (visitTimeArray == null || visitTimeArray.length <= 0) { return 0; }

		// 对访问时间做排序
		String[] rankTimeArray = sort(visitTimeArray);
		String lastTime = rankTimeArray[0];
		int session = 1;
				
		// 计算连续访问的时长
		for  (int i=1; i < rankTimeArray.length; i++) {
			String currTime = rankTimeArray[i];
			int period = getTimePeriod(currTime, lastTime); 			
			if (period > _SESSION_TIME) {
				session++;
				lastTime = currTime;
			}
		}

		return session;
	}


	/**
	* 获取每个域名的顶级域名再返回
	*/
	public static String[] getDomainParts(String domainList)
	{
		ArrayList<String> results = new ArrayList<String>();

		String[] parts = domainList.split(",");
		for (int i=0; i < parts.length; i++) {
			results.add( parseDomainByHost(parts[i]) );
		}

		return (String[]) results.toArray(new String[0]);
	}

	
	/**
	* 判断字符串了列表是否包含某个子串
	*/
	public static boolean contains(String[] partArray, String part)
	{
		for (int i=0; i < partArray.length; i++)
		{
			if (part.indexOf(partArray[i]) >= 0) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args)
	{
		/*
		String[] urlArray = {"http://app.api.che168.com/phone/v54/Push/regPush.ashx", "http://supersocket3.youxinpai.com:8001/socket", "http://apps.api.che168.com/phone/v50/Cars/GetHotSeriesList.ashx?_appid=2scapp.ios&pid=210000&cid=210100&udid=gVuTdxo7t6Nkh085qxXZP57c%2FoyMOiOjGI0Ym4xIbfjRlTbEcNkPw4q%2B40UxnliX6Ji8b4QFEiqf%2F903NxPqjqf7Glr8tfF5&_sign=98aa70bab018eab4b0e32c3e0a5265dc&channelid=App%20Store&appversion=5.5.6"};
		// String[] urlArray = {"http://app.api.che168.com/phone/v54/Push/regPush.ashx"};
		for (int i=0; i < urlArray.length; i++) {
			System.out.println( DataUtil.getUrlTrunk(urlArray[i]) );
		}
		*/

		/*
		System.out.println( DataUtil.getDateFromFileFromTail("F:\\BigData\\config\\user-rank\\data\\2017-02-19.csv.gz"));
		System.out.println( DataUtil.getDateFromFileFromTail("F:\\BigData\\config\\user-rank\\data\\2.csv.gz"));
		*/

		System.out.println( DataUtil.parseDomainByHost("req.adsmogo.mobi"));
		System.out.println( DataUtil.parseDomainByHost("map.yixin.im"));
	}
}
