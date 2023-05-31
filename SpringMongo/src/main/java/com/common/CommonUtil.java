package com.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CommonUtil {
   
   public String addMsgLoc(Model m, String msg, String loc) {
      m.addAttribute("msg",msg);
      m.addAttribute("loc",loc);
      return "message";
   }
   
   public String addMsgBack(Model m, String msg) {
      m.addAttribute("msg",msg);
      m.addAttribute("loc","javascript:history.back()");
      return "message";
   }
   
   public static String getDateTime(String fm) {
	   Date today=new Date();
	   SimpleDateFormat sdf=new SimpleDateFormat(fm);
	   return sdf.format(today);
   }
   public static String getDateTime() {
	   String str=getDateTime("yyyy-MM-dd");
	   return str;
   }
 //null값을 빈문자열로 치환
 	public static String nvl(String str, String chg_str) {
 		String res = "";
 		if (str == null) {
 			res = chg_str;
 		} else if (str.equals("")) {
 			res = chg_str;
 		} else {
 			res = str;
 		}
 		return res;
 	}//----------------------------------------


}