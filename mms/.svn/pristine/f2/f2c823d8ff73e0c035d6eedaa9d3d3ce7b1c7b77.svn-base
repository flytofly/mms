package cn.mmdata.commons.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/*** 
     * Compatible with GET and POST 
     *  
     * @param request 
     * @return : <code>byte[]</code> 
     * @throws IOException 
     */  
    public static byte[] getRequestQuery(HttpServletRequest request)  
            throws IOException {  
        String submitMehtod = request.getMethod();  
        String queryString = null;  
  
        if (submitMehtod.equals("GET")) {// GET  
            queryString = request.getQueryString();  
            String charEncoding = request.getCharacterEncoding();// charset  
            if (charEncoding == null) {  
                charEncoding = "UTF-8";  
            }  
            return queryString.getBytes(charEncoding);  
        } else {// POST  
            return getRequestPostBytes(request);  
        }  
    }  
	/*** 
     * Get request query string, form method : post 
     *  
     * @param request 
     * @return byte[] 
     * @throws IOException 
     */  
    public static byte[] getRequestPostBytes(HttpServletRequest request)  
            throws IOException {  
        int contentLength = request.getContentLength();  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        }  
        return buffer;  
    } 
	/*** 
     * Get request query string, form method : post 
     *  
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public static String getRequestPostStr(HttpServletRequest request)  
            throws IOException {  
        byte buffer[] = getRequestPostBytes(request);  
        if(buffer == null || buffer.length == 0){
        	return "";
        }
        String charEncoding = request.getCharacterEncoding();  
        if (charEncoding == null) {  
            charEncoding = "UTF-8";  
        } 
        return new String(buffer, charEncoding);  
    }
}
