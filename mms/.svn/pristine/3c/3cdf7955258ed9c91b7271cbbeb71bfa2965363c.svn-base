package cn.mmdata.commons.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @project WeiboAutoShibie
 * @description:$<p>读取配置文件信息</p>$
 * @author  $杨彦召<Adair>$
 * @version $id:PropertiesUtils.java,Revision:v1.0,Date:2012-12-30 12:04:51 $
 * @Modification：$Date:2012-12-30,
 */
public class PropertiesUtils {
	private Properties config = new Properties();
	private String configPath = "jdbc.properties";
	private Hashtable<String, Properties> instanceCache = new Hashtable<String, Properties>();
	
	public PropertiesUtils(){
	}
	public PropertiesUtils(String configPath){
		this.setConfigPath(configPath);
	}
	/**
	 * @return the configPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @param configPath the configPath to set
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * 加载配置文件
	 * @return
	 */
	public Properties loadConfig(String propName) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(configPath);
		try {
			config.load(in);
			instanceCache.put(propName, config);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return config;
	}

	/**
	 * 获取配置文件
	 * 
	 * @param propFileName
	 * @return
	 */
	private Properties getProperty(String propFileName) {
		if (instanceCache.containsKey(propFileName)) {
			return (Properties) instanceCache.get(propFileName);
		} else {
			return null;
		}
	}

	public String getPropertiesValue(String propertiesName,String key){
		if(propertiesName != null && !"".equals(propertiesName)){
			this.setConfigPath(propertiesName);
		}
		return getPropertiesValue(key);
	}
	
	public String getPropertiesValue(String key){
		Properties config = getProperty(configPath);  
		if (config == null) {  
		    config = loadConfig(configPath);  
		}  
		return config.getProperty(key);
	}
	
	
	public static void main(String [] args){
		PropertiesUtils utils = new PropertiesUtils();
		String tempDir = utils.getPropertiesValue("jdbc.driverClassName");
		System.out.println(tempDir);
		String tempDir2 = utils.getPropertiesValue("jdbc.properties","jdbcUrl");
		System.out.println(tempDir2);
	}
}
