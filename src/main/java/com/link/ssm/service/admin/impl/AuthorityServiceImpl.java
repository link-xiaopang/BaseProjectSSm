package com.link.ssm.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.ssm.dao.admin.AuthorityDao;
import com.link.ssm.entity.admin.Authority;
import com.link.ssm.service.admin.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityDao dao;

	public int add(Authority authority) {
		return dao.add(authority);
	}

	public int deleteByRoleId(Long roleId) {
		return dao.deleteByRoleId(roleId);
	}

	public List<Authority> findListByRold(Long roleId) {
		return dao.findListByRold(roleId);
	}

}
