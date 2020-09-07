package com.link.ssm.interceptor.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.link.ssm.entity.admin.Menu;
import com.link.ssm.util.MenuUtil;

import net.sf.json.JSONObject;

//后台登陆拦截器

public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURL = request.getRequestURI();
		System.out.println(requestURL);
		Object admin = request.getSession().getAttribute("admin");
		if (admin == null) {

			//表示未登陆或者登陆失效
			String header = request.getHeader("X-Requested-With");
			//判断是否时ajax请求
			if ("XMLHttpRequest".equals(handler)) {
				//表实ajax请求
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("type", "error");
				ret.put("mag", "登陆会话超时或还未登录，请重新登陆！");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			//表示是普通链接跳转，直接重定向到登录页面
			response.sendRedirect(request.getServletContext().getContextPath() + "/system/login");
			return false;
		}
		//获取菜单id
		String mid = request.getParameter("_mid");
		if (!StringUtils.isEmpty(mid)) {
			List<Menu> thirdMenuList = MenuUtil.getAllThirdMenu((List<Menu>) request.getSession().getAttribute("userMenus"), Long.valueOf(mid));
			request.setAttribute("thirdMenuList", thirdMenuList);
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}
