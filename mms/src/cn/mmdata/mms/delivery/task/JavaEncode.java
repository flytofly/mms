package cn.mmdata.mms.delivery.task;

import java.io.UnsupportedEncodingException;
/**
 * 14 e + ascii(letter) - 35 + f + ascii(letter) - 43
 * 0,1,2,3,4,5,6,7,8,9,a,b,c,d 
 * e,f,g,h,i,j,k,l,m,n,o,p,q,r         +35
 * s,t,u,v,w,x,y,z                     +43
 *
 * @author newteinltg001
 *
 */
public class JavaEncode {
   public static char[] a1 = new char[]{'0','1','2','3','4','5','6','7','8','9','a','b','c','d'};
   public static char[] a2 = new char[]{'e','f','g','h','i','j','k','l','m','n'};
   public static char[] a3 = new char[]{'o','p','q','r'};
   public static char[] a4 = new char[]{'s','t','u','v','w','x','y','z'};
  
   public static String encode(String sourceStr){
	   sourceStr = sourceStr.toLowerCase();
	   char[] sourceCharArray = sourceStr.toCharArray();
	   String outputStr = "";
       loop : for(char c : sourceCharArray){
		  for(char x : a1){
			 if(x == c){
				 outputStr += c;
				 continue loop;
			 }
		  }
		  for(char y : a2){
				 if(y == c){
					 outputStr += ("e" + (char)(Integer.valueOf(c) - 35));
					 continue loop;
				 }
			  }
		  for(char z : a3){
			  if(z == c){
				  outputStr += ("e" + (char)(Integer.valueOf(c) - 35));
				  continue loop;
			  }
		  }
		  for(char i : a4){
			  if(i == c){
				  outputStr += ("f" + (char)(Integer.valueOf(c) - 43));
				  continue loop;
			  }
		  }
	   }
	   return outputStr;
   }
   
   public static String dencode(String encodeStr){
	   return null;
   }
  
   public static void main(String[] args) throws UnsupportedEncodingException {
	   String sourceStr = "abcvbhgtYuigsreeefDvfg23fes";
	   System.out.println(encode(sourceStr));
	   /*System.out.println(dencode(sourceStr));*/
    }
}
