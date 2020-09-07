package com.link.ssm.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.link.ssm.entity.admin.Authority;

//权限service接口
@Service
public interface AuthorityService {

	public int add(Authority authority);

	public int deleteByRoleId(Long roleId);

	public List<Authority> findListByRold(Long roleId);
}
