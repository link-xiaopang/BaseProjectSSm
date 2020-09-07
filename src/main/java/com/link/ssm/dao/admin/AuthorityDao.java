package com.link.ssm.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.link.ssm.entity.admin.Authority;

@Repository
public interface AuthorityDao {
	public int add(Authority authority);

	public int deleteByRoleId(Long roleId);

	public List<Authority> findListByRold(Long roleId);
}
