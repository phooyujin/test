package com.ch.mybatis.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
// 로그인 안 하면 main으로 못 가게 하려고 만듬
public class SessionChk extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session == null || 
				session.getAttribute("id") == null ||
				session.getAttribute("id").equals("")) {
			//loginForm으로 다시 보냐라
			response.sendRedirect("loginForm.do");
			return false;
		}return true;
	}
}