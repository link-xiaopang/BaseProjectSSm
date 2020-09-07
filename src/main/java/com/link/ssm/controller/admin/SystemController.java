package com.link.ssm.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.link.ssm.entity.admin.Authority;
import com.link.ssm.entity.admin.Menu;
import com.link.ssm.entity.admin.Role;
import com.link.ssm.entity.admin.User;
import com.link.ssm.service.admin.AuthorityService;
import com.link.ssm.service.admin.LogService;
import com.link.ssm.service.admin.MenuService;
import com.link.ssm.service.admin.RoleService;
import com.link.ssm.service.admin.UserService;
import com.link.ssm.util.CpachaUtil;
import com.link.ssm.util.MenuUtil;

//系统操作类控制器
@Controller
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private LogService logService;

	//登陆后的主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request) {
		List<Menu> userMenus = (List<Menu>) request.getSession().getAttribute("userMenus");
		model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
		model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
		model.setViewName("system/index");
		return model;
	}

	//系统登陆页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}

	//系统登陆后的欢迎页
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model) {
		model.setViewName("system/welcome");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginAct(User user, String cpache, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if (user == null) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写用户信息！");
			return ret;
		}
		if (StringUtils.isEmpty(cpache)) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写验证码！");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写用户名！");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写密码！");
			return ret;
		}

		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		if (loginCpacha == null) {
			ret.put("type", "erroe");
			ret.put("msg", "页面加载超时，请重新登陆！");
			return ret;
		}
		if (!cpache.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
			ret.put("type", "erroe");
			ret.put("msg", "验证码错误！");
			logService.add("用户名为" + user.getUsername() + "的用户登录时输入的验证码错误！");
			return ret;
		}

		User findByusername = userService.findByUsername(user.getUsername());
		if (findByusername == null) {
			ret.put("type", "erroe");
			ret.put("msg", "该用户名不存在！");
			logService.add("用户名为" + user.getUsername() + "的用户不存在！");
			return ret;
		}
		if (!user.getPassword().equals(findByusername.getPassword())) {
			ret.put("type", "erroe");
			ret.put("msg", "密码错误！");
			logService.add("用户名为" + user.getUsername() + "的用户登录时输入的密码错误！");
			return ret;
		}

		Role role = roleService.find(findByusername.getRoleId());
		List<Authority> authorityList = authorityService.findListByRold(role.getId());
		String menuIds = "";
		for (Authority authority : authorityList) {
			menuIds += authority.getMenuId() + ",";
		}
		if (!StringUtils.isEmpty(menuIds)) {
			menuIds = menuIds.substring(0, menuIds.length() - 1);
		}
		List<Menu> userMenus = menuService.findListByIds(menuIds);
		request.getSession().setAttribute("admin", findByusername);
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", userMenus);
		logService.add("用户名为{" + user.getUsername() + "},角色为{" + role.getName() + "}的用户登录成功！");
		ret.put("type", "success");
		ret.put("msg", "登陆成功");
		return ret;
	}

	@RequestMapping(value = "/get_cpacha", method = RequestMethod.GET)
	public void generateCpacha(@RequestParam(value = "vl", required = false, defaultValue = "4") Integer vcodeLen, @RequestParam(value = "w", required = false, defaultValue = "100") Integer width,
			@RequestParam(value = "h", required = false, defaultValue = "30") Integer height, @RequestParam(value = "type", required = true, defaultValue = "loginCpacha") String cpacheType,
			HttpServletRequest request, HttpServletResponse response) {

		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute(cpacheType, generatorVCode);
		BufferedImage gereatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(gereatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//后台退出注销功能
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		session.setAttribute("role", null);
		request.getSession().setAttribute("userMenus", null);
		return "redirect:login";
	}

	/**
	 * 修改密码页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit_password", method = RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model) {
		model.setViewName("system/edit_password");
		return model;
	}

	@RequestMapping(value = "/edit_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPasswordAct(String newPassword, String oldPassword, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(newPassword)) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写新密码");
			return ret;
		}
		User user = (User) request.getSession().getAttribute("admin");
		if (!user.getPassword().equals(oldPassword)) {
			ret.put("type", "erroe");
			ret.put("msg", "原密码错误！");
			return ret;
		}

		user.setPassword(newPassword);
		if (userService.editPassword(user) <= 0) {
			ret.put("type", "erroe");
			ret.put("msg", "密码修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "密码修改成功");
		logService.add("用户名为{" + user.getUsername() + "},的用户成功修改密码！");
		return ret;
	}

}
