package cn.mmdata.commons.util;

public class RuleUtil {

	public static String formatRule(String rule) {
		if (rule.startsWith("http://")) {
			rule = rule.substring(7);
		} else if (rule.startsWith("https://")) {
			rule = rule.substring(8);
		}
		if (rule.startsWith("m.")) {
			rule = rule.substring(1);
		} else if (rule.startsWith("3g.")) {
			rule = rule.substring(2);
		} else if (rule.startsWith("wap.") || rule.startsWith("www.")) {
			rule = rule.substring(3);
		} else if (rule.startsWith(".")) {
			rule = rule.substring(1);
		}
		rule = rule.replaceAll(" ", "").replaceAll("　", "");
		String host = LocalURLUtil.getTopDomainByHost(getHostFromRule(rule));

		if (rule.startsWith(host) && !rule.startsWith(".") && !StringUtil.isNull(host) && !LocalURLUtil.isIP(host)) {
			rule = "." + rule;
		}
		return verifyRule(rule) == true ? rule : "";
	}

	public static boolean verifyRule(String rule) {
		String host = LocalURLUtil.getTopDomainByHost(getHostFromRule(rule));
		if (StringUtil.isNull(host) || (LocalURLUtil.isIP(host) && rule.startsWith("."))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据URL规则，获取对应的Host
	 * 
	 * @param rule
	 * @return
	 */
	public static String getHostFromRule(String rule) {
		if (rule.startsWith("http://")) {
			rule = rule.substring(7);
		} else if (rule.startsWith("https://")) {
			rule = rule.substring(8);
		}
		if (rule.length() == 0) {
			return "";
		}
		rule = rule.trim();
		int start = rule.startsWith(".") ? 1 : 0;
		int endIndex = rule.length();

		int index1 = rule.indexOf("/");
		int index2 = rule.indexOf("&");
		if (index1 != -1 & index2 != -1) {
			if (index1 > index2) {
				endIndex = index2;
			} else {
				endIndex = index1;
			}
		} else if (index1 != -1) {
			endIndex = index1;
		} else if (index2 != -1) {
			endIndex = index2;
		}
		return rule.substring(start, endIndex).trim();
	}

	public static void main(String[] args) {
		String rule = " 58 .c o　m ";
		System.out.println(verifyRule(rule));
		System.out.println(formatRule(rule));
	}
}
