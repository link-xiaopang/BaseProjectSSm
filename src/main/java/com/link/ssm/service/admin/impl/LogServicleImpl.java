package com.link.ssm.service.admin.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.ssm.dao.admin.LogDao;
import com.link.ssm.entity.admin.Log;
import com.link.ssm.service.admin.LogService;

@Service
public class LogServicleImpl implements LogService {

	@Autowired
	private LogDao dao;

	public int add(Log log) {
		return dao.add(log);
	}

	public List<Log> findList(Map<String, Object> queryMap) {
		return dao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		return dao.getTotal(queryMap);
	}

	public int delete(String id) {
		return dao.delete(id);
	}

	public int add(String content) {
		Log log = new Log();
		//		log.setContent("用户名为" + user.getUsername() + "的用户登录时输入的验证码错误！");
		log.setContent(content);
		log.setCreateTime(new Date());
		return dao.add(log);
	}
}
