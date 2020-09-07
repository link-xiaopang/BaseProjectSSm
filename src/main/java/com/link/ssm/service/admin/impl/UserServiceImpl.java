package com.link.ssm.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.ssm.dao.admin.UserDao;
import com.link.ssm.entity.admin.User;
import com.link.ssm.service.admin.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public int add(User user) {
		return userDao.add(user);
	}

	public int edit(User user) {
		return userDao.edit(user);
	}

	public int editPassword(User user) {
		return userDao.editPassword(user);
	}

	public int delete(String ids) {
		return userDao.delete(ids);
	}

	public List<User> findList(Map<String, Object> queryMap) {
		return userDao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		return userDao.getTotal(queryMap);
	}
}
