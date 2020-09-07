package com.link.ssm.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.link.ssm.entity.admin.Log;
import com.link.ssm.page.admin.page.Page;
import com.link.ssm.service.admin.LogService;

/**
 * 日志管理
 * 
 * @author link
 *
 */
@RequestMapping("/admin/log")
@Controller
public class LogController {

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("/log/list");
		return model;
	}

	/*
	 * 获取日志列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page, @RequestParam(value = "content", required = false, defaultValue = "") String content) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("content", content);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", logService.findList(queryMap));
		ret.put("total", logService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 添加日志
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Log log) {
		Map<String, String> ret = new HashMap<String, String>();
		if (log == null) {
			ret.put("type", "erroe");
			ret.put("msg", "请填写正确的日志信息!");
			return ret;
		}
		if (StringUtils.isEmpty(log.getContent())) {
			ret.put("type", "error");
			ret.put("msg", "请填写日志名称！");
			return ret;
		}

		log.setCreateTime(new Date());
		if (logService.add(log) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "日志添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "日志添加成功！");
		return ret;
	}

	//批量删除日志
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String id) {
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(id)) {
			ret.put("type", "erroe");
			ret.put("msg", "请选择要删除的数据!");
			return ret;
		}

		if (id.contains(",")) {
			id = id.substring(0, id.length() - 1);
		}

		if (logService.delete(id) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "日志删除成功！");
		return ret;
	}

}
