package cn.mmdata.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalURLUtil {
	private static String[] searchUrls = { "baidu.com", "google.com", "so.com", "yahoo.com", "youdao.com", "sogou.com",
			"yhd.com", "newegg.cn", "suning.com", "taobao.com", "tmall.com", "yixun.com", "dangdang.com", "gome.com.cn",
			"jd.com", "360buy.com", "amazon.cn", "etao.com", "meituan.com", "vancl.com", "nuomi.com", "soso.com",
			"bing.com", "jumei.com", "dianping.com", "alibbw.com", "lashou.com" };

	public static HashSet<String> getSearchDomainSet() {
		HashSet<String> searchDomain = new HashSet<String>();
		searchDomain.addAll(Arrays.asList(searchUrls));
		return searchDomain;
	}

	/**
	 * 从url路径中，得到参数名对应的参数值
	 * 
	 * @param url
	 *            url
	 * @param queryKey
	 *            参数名
	 * @return 参数名对应的参数值
	 */
	public static String getQuery(String url, String queryKey) {
		String queryValue = "";
		if (url.length() == 0 || url.indexOf("?") == 0 || "".equals(queryKey)) {
			return queryValue;
		}
		String query = url.substring(url.indexOf("?") + 1).replaceAll("&amp;", "&");
		String[] queryArray = query.split("&");
		if (queryArray.length > 0) {
			for (String keyvalue : queryArray) {
				String[] kv = keyvalue.split("=");
				if (kv.length > 1) {
					String key = kv[0];
					String value = kv[1];
					if (queryKey.equals(key)) {
						queryValue = getSkeyword(value);
					}
				}
			}
		}
		return queryValue;
	}

	public static String getSearchFromDesc(String url, String refer) {
		String referDesc = "其他";
		if (refer != null && refer.length() > 0) {
			if (refer.indexOf("www.baidu.com") > 0 || refer.indexOf("m.baidu.com") > 0
					|| refer.matches("http://(m[0-9]*|wap).baidu.com.*")) {
				referDesc = "百度";
			} else if (refer.indexOf("www.google.com") > 0) {
				referDesc = "Google";
			} else if (refer.indexOf("www.sogou.com") > 0 || refer.indexOf("wap.sogou.com") > 0) {
				referDesc = "Sogou";
			} else if (refer.indexOf("www.so.com") > 0 || refer.indexOf("m.so.com") > 0) {
				referDesc = "360搜索";
			} else if (refer.indexOf("www.soso.com") > 0 || refer.indexOf("wap.soso.com") > 0) {
				referDesc = "腾讯搜索";
			} else if (refer.indexOf("www.yicha.cn") > 0 || refer.indexOf("i.yicha.cn") > 0) {
				referDesc = "易查";
			} else {
				referDesc = "其他";
			}
		} else {
			referDesc = "无";
		}
		try {
			URL source = new URL(url);
			URL refere = null;
			if (refer != null && refer.length() > 0) {
				refere = new URL(refer);
				if (source.getHost().equals(refere.getHost())) {
					referDesc = "本站";
				}
			}
		} catch (MalformedURLException e) {
		}
		return referDesc;
	}

	public static String getSkeyword(String qvalue) {
		String value = "";
		try {
			value = URLDecoder.decode(qvalue, "UTF-8");
			if (StringUtil.isMessyCode(value)) {
				value = URLDecoder.decode(qvalue, "GBK");
				if (StringUtil.isMessyCode(value)) {
					value = "";
				}
			}
		} catch (UnsupportedEncodingException e) {
			value = "";
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			value = "";
		}
		return value.replaceAll("\r", "").replaceAll("\n", "").replaceAll(",", "");
	}

	public static String getSkeyword(String qvalue, String enc) {
		String value = "";
		try {
			value = URLDecoder.decode(qvalue, enc);
			if (StringUtil.isMessyCode(value)) {
				value = "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			value = "";
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			value = "";
		}
		return value;
	}

	/**
	 * 暂时支持的搜索引擎有：0:无;11:百度;12:Google;13:360;14:雅虎;15:有道;16:搜狗;17:一号店;18:新蛋;19:
	 * 苏宁易购;20:淘宝;
	 * 21:天猫;22:腾讯易迅;23:当当;24:国美;25:京东;26:亚马逊;27:一淘网;28:美团网;29:凡客;30:糯米团;
	 * 31:搜搜;32:必应;33:聚美优品;34:大众点评;35:美团;36:阿里宝宝;37:拉手网
	 * 
	 * @param sengine
	 *            搜索引擎ID
	 * @return 获取对应的搜索引擎
	 */
	public static String getSearchEngineDesc(int sengine) {
		String sengineDesc = "";
		switch (sengine) {
		case 0:
			sengineDesc = "无";
			break;
		case 11:
			sengineDesc = "百度";
			break;
		case 12:
			sengineDesc = "Google";
			break;
		case 13:
			sengineDesc = "360";
			break;
		case 14:
			sengineDesc = "雅虎";
			break;
		case 15:
			sengineDesc = "有道";
			break;
		case 16:
			sengineDesc = "搜狗";
			break;
		case 17:
			sengineDesc = "一号店";
			break;
		case 18:
			sengineDesc = "新蛋";
			break;
		case 19:
			sengineDesc = "苏宁易购";
			break;
		case 20:
			sengineDesc = "淘宝";
			break;
		case 21:
			sengineDesc = "天猫";
			break;
		case 22:
			sengineDesc = "腾讯易迅";
			break;
		case 23:
			sengineDesc = "当当";
			break;
		case 24:
			sengineDesc = "国美";
			break;
		case 25:
			sengineDesc = "京东";
			break;
		case 26:
			sengineDesc = "亚马逊";
			break;
		case 27:
			sengineDesc = "一淘网";
			break;
		case 28:
			sengineDesc = "美团网";
			break;
		case 29:
			sengineDesc = "凡客";
			break;
		case 30:
			sengineDesc = "糯米团";
			break;
		case 31:
			sengineDesc = "搜搜";
			break;
		case 32:
			sengineDesc = "必应";
			break;
		case 33:
			sengineDesc = "聚美优品";
			break;
		case 34:
			sengineDesc = "大众点评";
			break;
		case 35:
			sengineDesc = "美团";
			break;
		case 36:
			sengineDesc = "阿里宝宝";
			break;
		case 37:
			sengineDesc = "拉手网";
			break;
		}
		return sengineDesc;
	}

	/**
	 * 暂时支持的搜索引擎有：0:无;11:百度;12:Google;13:360;14:雅虎;15:有道;16:搜狗;17:一号店;18:新蛋;19:
	 * 苏宁易购;20:淘宝;
	 * 21:天猫;22:腾讯易迅;23:当当;24:国美;25:京东;26:亚马逊;27:一淘网;28:美团网;29:凡客;30:糯米团;
	 * 31:搜搜;32:必应;33:聚美优品;34:大众点评;35:美团;36:阿里宝宝;37:拉手网
	 * 
	 * @param url
	 *            来源页面
	 * @return 获取对应的搜索引擎名称
	 */
	public static String getSearchEngineDesc(String url) {
		int sengine = getSearchEngine(url);
		return getSearchEngineDesc(sengine);
	}

	/**
	 * 暂时支持的搜索引擎有：0:无;11:百度;12:Google;13:360;14:雅虎;15:有道;16:搜狗;17:一号店;18:新蛋;19:
	 * 苏宁易购;20:淘宝;
	 * 21:天猫;22:腾讯易迅;23:当当;24:国美;25:京东;26:亚马逊;27:一淘网;28:美团网;29:凡客;30:糯米团;
	 * 31:搜搜;32:必应;33:聚美优品;34:大众点评;35:美团;36:阿里宝宝;37:拉手网
	 * 
	 * @param url
	 *            来源页面
	 * @return 获取对应的搜索引擎ID
	 */
	public static int getSearchEngine(String url) {
		int searchEngine = 0;

		if (StringUtil.isNull(url)) {
			return searchEngine;
		}
		try {
			URL surl = new URL(url);
			String host = surl.getHost();
			if (host.contains("baidu.com")) {
				searchEngine = 11;
			} else if (host.contains("google.com")) {
				searchEngine = 12;
			} else if (host.contains("so.com")) {
				searchEngine = 13;
			} else if (host.contains("yahoo.com")) {
				searchEngine = 14;
			} else if (host.contains("youdao.com")) {
				searchEngine = 15;
			} else if (host.contains("sogou.com")) {
				searchEngine = 16;
			} else if (host.contains("yhd.com")) {
				searchEngine = 17;
			} else if (host.contains("newegg.cn")) {
				searchEngine = 18;
			} else if (host.contains("suning.com")) {
				searchEngine = 19;
			} else if (host.contains("taobao.com")) {
				searchEngine = 20;
			} else if (host.contains("tmall.com")) {
				searchEngine = 21;
			} else if (host.contains("yixun.com")) {
				searchEngine = 22;
			} else if (host.contains("dangdang.com")) {
				searchEngine = 23;
			} else if (host.contains("gome.com.cn")) {
				searchEngine = 24;
			} else if (host.contains("jd.com") || host.contains("360buy.com")) {
				searchEngine = 25;
			} else if (host.contains("amazon.cn")) {
				searchEngine = 26;
			} else if (host.contains("etao.com")) {
				searchEngine = 27;
			} else if (host.contains("meituan.com")) {
				searchEngine = 28;
			} else if (host.contains("vancl.com")) {
				searchEngine = 29;
			} else if (host.contains("nuomi.com")) {
				searchEngine = 30;
			} else if (host.contains("soso.com")) {
				searchEngine = 31;
			} else if (host.contains("bing.com")) {
				searchEngine = 32;
			} else if (host.contains("jumei.com")) {
				searchEngine = 33;
			} else if (host.contains("dianping.com")) {
				searchEngine = 34;
			} else if (host.contains("meituan.com")) {
				searchEngine = 35;
			} else if (host.contains("alibbw.com")) {
				searchEngine = 36;
			} else if (host.contains("lashou.com")) {
				searchEngine = 37;
			}
		} catch (MalformedURLException e) {
			searchEngine = 0;
		}
		return searchEngine;
	}

	/**
	 * 暂时支持的搜索引擎有：baidu、google、sogou、360搜索、搜搜
	 * 
	 * @param url
	 *            来源页面
	 * @return 用户搜索的关键字
	 */
	public static String getSearchKeyWord(String url) {
		if (StringUtil.isNull(url)) {
			return "";
		}
		String queryValue = "";
		String sKeyword = "";
		if (url.contains("tieba.baidu.com")) {
			sKeyword = getQuery(url, "kw");
			queryValue = getSkeyword(sKeyword);
		} else if (url.matches("http://(m[0-9]*|wap).baidu.com.*")) {
			sKeyword = getQuery(url, "word");
			queryValue = getSkeyword(sKeyword);
		} else if (url.contains("baidu.com")) {
			sKeyword = getQuery(url, "wd");
			if (StringUtil.isNull(sKeyword)) {
				sKeyword = getQuery(url, "word");
			}
			queryValue = getSkeyword(sKeyword);
		} else if (url.contains("google.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("www.so.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "GBK");
		} else if (url.contains("m.so.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("search.yahoo.com") || url.contains("m.yahoo.com")) {
			sKeyword = getQuery(url, "p");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("youdao.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("www.sogou.com")) {
			sKeyword = getQuery(url, "query");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("wap.sogou.com")) {
			sKeyword = getQuery(url, "keyword");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("www.soso.com")) {
			sKeyword = getQuery(url, "w");
			queryValue = getSkeyword(sKeyword, "GBK");
		} else if (url.contains("wap.soso.com")) {
			sKeyword = getQuery(url, "key");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("i.yicha.cn")) {
			sKeyword = getQuery(url, "key");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("m.taobao.com") || url.contains("m.tmall.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("s.tmall.com") || url.contains("s.taobao.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "GB2312");
		} else if (url.contains("m.jd.com")) {
			sKeyword = getQuery(url, "keyword");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("i.meituan.com") || url.contains("www.meituan.com")) {
			sKeyword = getQuery(url, "w");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (url.contains("m.suning.com")) {
			sKeyword = getQuery(url, "keywords");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		}
		return queryValue.replaceAll("\r", "").replaceAll("\n", "").replaceAll(",", "");
	}

	/***
	 * 根据host，返回url其余部分相对应搜索内容
	 * 
	 * @param host
	 * @param url
	 * @return keyword
	 */
	public static String getSearchKeyWord(String host, String url) {
		if (StringUtil.isNull(host)) {
			return "";
		}
		String queryValue = "";
		String sKeyword = "";
		if (host.contains("tieba.baidu.com")) {
			sKeyword = getQuery(url, "kw");
			queryValue = getSkeyword(sKeyword);
		} else if (host.matches("http://(m[0-9]*|wap).baidu.com.*")) {
			sKeyword = getQuery(url, "word");
			queryValue = getSkeyword(sKeyword);
		} else if (host.contains("baidu.com")) {
			sKeyword = getQuery(url, "wd");
			if (StringUtil.isNull(sKeyword)) {
				sKeyword = getQuery(url, "word");
			}
			queryValue = getSkeyword(sKeyword);
		} else if (host.contains("google.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("www.so.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "GBK");
		} else if (host.contains("m.so.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("search.yahoo.com") || url.contains("m.yahoo.com")) {
			sKeyword = getQuery(url, "p");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("youdao.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("www.sogou.com")) {
			sKeyword = getQuery(url, "query");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("wap.sogou.com")) {
			sKeyword = getQuery(url, "keyword");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("www.soso.com")) {
			sKeyword = getQuery(url, "w");
			queryValue = getSkeyword(sKeyword, "GBK");
		} else if (host.contains("wap.soso.com")) {
			sKeyword = getQuery(url, "key");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("i.yicha.cn")) {
			sKeyword = getQuery(url, "key");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("m.taobao.com") || host.contains("m.tmall.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("s.tmall.com") || host.contains("s.taobao.com")) {
			sKeyword = getQuery(url, "q");
			queryValue = getSkeyword(sKeyword, "GB2312");
		} else if (host.contains("m.jd.com")) {
			sKeyword = getQuery(url, "keyword");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("i.meituan.com") || host.contains("www.meituan.com")) {
			sKeyword = getQuery(url, "w");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		} else if (host.contains("m.suning.com")) {
			sKeyword = getQuery(url, "keywords");
			queryValue = getSkeyword(sKeyword, "UTF-8");
		}
		return queryValue.replaceAll("\r", "").replaceAll("\n", "").replaceAll(",", "");
	}

	/**
	 * 根据URL得到对应的域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的域名
	 */
	public static String getTopDomain(String url) {
		return getTopDomainByURL(url);
	}

	static Pattern pattern = Pattern.compile(
			"[^\\.]+(\\.com$|\\.cn$|\\.com\\.cn$|\\.com\\.hk$|\\.sh\\.cn$|\\.net$|\\.org$|\\.net\\.cn$|\\.co\\.il$|\\.co\\.ke$|\\.co\\.ma$|\\.com\\.pe$|\\.in\\.th$|\\.co\\.th$|\\.org\\.cn$|\\.com\\.eg$|\\.co\\.ck$|\\.com\\.ng$|\\.com\\.bn$|\\.com\\.ao$|\\.co\\.ls$|\\.com\\.sv$|\\.edu\\.cn$|\\.gov\\.cn$|\\.com\\.au$|\\.com\\.tr$|\\.co\\.ve$|\\.com\\.gi$|\\.gov\\.au$|\\.com\\.sa$|\\.cc$|\\.me$|\\.tel$|\\.mobi$|\\.asia$|\\.biz$|\\.info$|\\.name$|\\.tv$|\\.hk$|\\.la$|\\.ca$|\\.club$|\\.to$|\\.travel$|\\.io$|\\.ru$|\\.vn$|\\.us$|\\.it$|\\.kr$|\\.pro$|\\.jp$|\\.lu$|\\.im$|\\.fr$|\\.pl$|\\.co$|\\.nz$|\\.win$|\\.fm$|\\.cd$|\\.tl$|\\.de$|\\.cm$|\\.pw$|\\.ga$|\\.gov$|\\.sh$|\\.ms$|\\.my$|\\.eu$|\\.xyz$|\\.ro$|\\.so$|\\.tw$|\\.loan$|\\.in$|\\.wang$|\\.no$|\\.cz$|\\.ee$|\\.ie$|\\.ws$|\\.lv$|\\.gs$|\\.pub$|\\.ch$|\\.bj$|\\.be$|\\.gg$|\\.tt$|\\.xxx$|\\.bb$|\\.ua$|\\.ml$|\\.sg$|\\.cf$|\\.xin$|\\.sexy$|\\.af$|\\.is$|\\.as$|\\.tk$|\\.pt$|\\.dk$|\\.sk$|\\.link$|\\.ag$|\\.ci$|\\.dm$|\\.al$|\\.hn$|\\.ir$|\\.lol$|\\.az$|\\.bz$|\\.ae$|\\.gy$|\\.es$|\\.am$|\\.uk$|\\.ac$|\\.date$|\\.edu$|\\.li$|\\.nu$|\\.nl$|\\.work$|\\.ren$|\\.vc$|\\.st$|\\.kz$|\\.mx$|\\.reng$|\\.moe$|\\.party$|\\.date$|\\.uz$|\\.science$|\\.at$|\\.ai$|\\.ooo$|\\.cricket$|\\.ly$|\\.tm$|\\.dz$|\\.su$|\\.cx$|\\.domain$|\\.rs$|\\.ph$|\\.se$|\\.fi$|\\.lt$|\\.tf$|\\.pics$|\\.mn$|\\.gl$|\\.sc$|\\.ec$|\\.gr$|\\.mo$|\\.gd$|\\.racing$|\\.lan$|\\.top$|\\.by$|\\.sl$|\\.pg$|\\.gw$|\\.pk$|\\.gt$|\\.tn$|\\.bo$|\\.id$|\\.mz$|\\.gi$|\\.tr$|\\.ng$|\\.sv$|\\.ls$|\\.eg$|\\.bn$|\\.il$|\\.ao$|\\.pe$|\\.ke$|\\.th$|\\.ba$|\\.bar$|\\.ceo$|\\.ma$|\\.公司$|\\.中国$|\\.网络$)");
	static Pattern ipPattern = Pattern.compile(
			"^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

	/**
	 * 根据URL得到对应的域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的域名
	 */
	private static String getTopDomainByURL(String url) {
		if (StringUtil.isNull(url)) {
			return "";
		}
		if (url.startsWith(".")) {
			url = url.substring(1);
		}
		String urlhttp = url;
		String urlhttps = "";
		if (url.startsWith(".")) {
			url = url.substring(1);
		}
		if (url.contains("&")) {
			url = url.substring(0, url.indexOf('&'));
		}
		if (!url.startsWith("http://")) {
			urlhttp = "http://" + url;
		} else if (!url.startsWith("https://")) {
			urlhttps = "https://" + url;
		}
		String host = "";
		try {
			host = new URL(urlhttp).getHost().toLowerCase();// 此处获取值转换为小写
			if (StringUtil.isNull(host)) {
				host = new URL(urlhttps).getHost().toLowerCase();// 此处获取值转换为小写
			}
		} catch (MalformedURLException e) {
			return "";
		}
		String result = "";
		Matcher matcher = pattern.matcher(host);
		while (matcher.find()) {
			result = matcher.group();
		}
		if (StringUtil.isNull(result)) {
			matcher = ipPattern.matcher(host);
			while (matcher.find()) {
				result = matcher.group();
			}
		}
		return result;
	}

	/**
	 * 根据URL得到对应的域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的域名
	 */
	public static String getTopDomainBySubString(String url) {
		String host = getSubDomainBySubString(url);
		if (StringUtil.isNull(host)) {
			return "";
		}
		return getTopDomainByHost(host);
	}

	/**
	 * 根据URL得到对应的二级域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的二级
	 */
	public static String getSubDomain(String url) {
		return getSubDomainByURL(url);
	}

	/**
	 * 根据URL得到对应的二级域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的二级
	 */
	private static String getSubDomainByURL(String url) {
		if (StringUtil.isNull(url)) {
			return "";
		}
		String host = "";
		try {
			host = new URL(url).getHost().toLowerCase();// 此处获取值转换为小写
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return host;
		}
		return host;
	}

	/**
	 * 根据URL得到对应的二级域名
	 * 
	 * @param url
	 *            来源页面
	 * @return URL对应的二级
	 */
	public static String getSubDomainBySubString(String url) {
		if (StringUtil.isNull(url) || !url.startsWith("http") || url.length() < 7) {
			return "";
		}
		if (url.contains(" ")) {
			url = url.split(" ")[0];
		}
		String host = "";
		String urlNotHttp = "";
		if (url.startsWith("https")) {
			urlNotHttp = url.substring(8).toLowerCase();
		} else if (url.startsWith("http")) {
			urlNotHttp = url.substring(7).toLowerCase();
		}
		if (urlNotHttp.indexOf("/") > 0) {
			host = urlNotHttp.substring(0, urlNotHttp.indexOf("/"));
		} else {
			host = urlNotHttp;
		}
		return host;
	}

	static Pattern topdomainpattern = Pattern.compile("[^\\.]+(\\.com$|\\.cn$|\\.net$|\\.com\\.cn$|\\.cc$|\\.org$|"
			+ "\\.edu\\.cn$|\\.gov\\.cn$|\\.net\\.cn$|\\.org\\.cn$|\\.me$|\\.tel$|"
			+ "\\.mobi$|\\.biz$|\\.info$|\\.name$|\\.tv$|\\.hk$|\\.io$|\\.co$|\\.tw$\\.uk$)");

	public static String getTopDomainByHost(String host) {
		if (host.indexOf(":") != -1) {
			host = host.substring(0, host.indexOf(":"));
		}
		Matcher matcher = pattern.matcher(host);
		String domain = "";
		while (matcher.find()) {
			domain = matcher.group();
		}
		if (StringUtil.isNull(domain)) {
			matcher = ipPattern.matcher(host);
			while (matcher.find()) {
				domain = matcher.group();
			}
		}
		return domain;
	}

	public static String getFatherHost(String host) {
		if (host.indexOf(".") < 0) {
			return host;
		}
		return host.substring(host.indexOf(".") + 1);
	}

	private static String urlRegex = "^((https|http|ftp|rtsp|mms)?://)"
			+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn't required if there is no file name
			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	private static Pattern urlPattern = Pattern.compile(urlRegex);

	public static boolean isURL(String str) {
		// 转换为小写
		str = str.toLowerCase();
		Matcher m = urlPattern.matcher(str);
		return m.matches();
	}

	private static String mobileRegix = "^[1][3,4,5,7,8][0-9]{9}$";
	private static Pattern mobilePattern = Pattern.compile(mobileRegix);

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Matcher m = mobilePattern.matcher(str);
		return m.matches();
	}

	public static boolean isIP(String str) {
		Matcher m = ipPattern.matcher(str);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(getTopDomain("http://www.eee.sh.cn"));
	}
}
