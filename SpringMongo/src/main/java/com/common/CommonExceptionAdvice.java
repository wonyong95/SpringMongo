package com.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;
/*스프링의 예외 처리 방법
 * [1] @ExceptionHandler를 이용하는 방법
 * [2] @ControllerAdvice를 이용하는 방법
 * [3] @ResponseStatus 를 이용해서 HTTP상태코드 처리하는 방법
 * */
//servlet-context.xml에 component-scan에 패키지 등록해야함
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception ex, Model m) {
		//ex.printStackTrace();
		//log.error(ex);
		m.addAttribute("exception", ex);
		return "login/errorAlert";
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String exceptionHandler2(Exception ex,Model m) {
		//log.error(ex);
		m.addAttribute("exception",ex);
		return "login/errorAlert";
	}
	
}
