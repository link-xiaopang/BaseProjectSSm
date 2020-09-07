package com.link.ssm.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.ssm.dao.admin.RoleDao;
import com.link.ssm.entity.admin.Role;
import com.link.ssm.service.admin.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;

	public int add(Role role) {
		return dao.add(role);
	}

	public int edit(Role role) {
		return dao.edit(role);
	}

	public int delete(Long id) {
		return dao.delete(id);
	}

	public List<Role> findList(Map<String, Object> queryMap) {
		return dao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		return dao.getTotal(queryMap);
	}

	public Role find(Long id) {
		return dao.find(id);
	}
}
