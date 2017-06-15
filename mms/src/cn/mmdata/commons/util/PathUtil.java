package cn.mmdata.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PathUtil {
	/**
	 * 得到应用的ClasPath，如果是windows，则从盘符开始，如果是非windows，则是从/开始
	 * @return 应用的ClasPath
	 */
	public static String getAppClassPath(){
		String appClassPath = PathUtil.class.getResource("/").toString();
		String os = getOs();
		if(os.startsWith("win") || os.startsWith("Win")){
			appClassPath = appClassPath.substring(6).replaceAll("%20", " ");
		}else{
			appClassPath = appClassPath.substring(5).replaceAll("%20", " ");
		}
		return appClassPath;
	}
	
	/**
	 * 得到class文件的URI目录（不包括自己）
	 * @param clazz 类文件
	 * @return 类文件的URI目录
	 */
	public static String getClassFilePath(Class<?> clazz){
		String classFilePath = clazz.getResource("").getPath().replaceAll("%20", " ");;
		String os = getOs();
		if(os.startsWith("win") || os.startsWith("Win")){
			classFilePath = classFilePath.substring(1);
		}
		return classFilePath;
	}
	
	private static String getOs(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		return os;
	}
	
	/**
	 * 测试输出文件内容
	 * @param filePath 文件路径
	 */
	public static void outFileText(String filePath){
		File file = new File(filePath);    
        try {    
            FileReader fr = new FileReader(file);    
            BufferedReader reader = new BufferedReader(fr);    
            String str = reader.readLine();    
            while (str != null) {    
                System.out.println(str);    
                str = reader.readLine();    
            }
            reader.close();
        } catch (FileNotFoundException e) {   
            //当抛出多个异常时，子异常当在父异常前抛出。  
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("os.name = " + System.getProperty("os.name"));
		String appPath =PathUtil.getAppClassPath();
		System.out.println("appPath = " + appPath);
		System.out.println("getClassFilePath = " + getClassFilePath(PathUtil.class));
		System.out.println("File.separator = " + File.separator);
		
		PathUtil.outFileText(appPath + "jdbc.properties");
	}

}
