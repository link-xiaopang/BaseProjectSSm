package com.link.ssm.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.ssm.dao.admin.MenuDao;
import com.link.ssm.entity.admin.Menu;
import com.link.ssm.service.admin.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	public int add(Menu menu) {
		return menuDao.add(menu);
	}

	public List<Menu> findList(Map<String, Object> queryMap) {
		return menuDao.findList(queryMap);
	}

	public List<Menu> findTopList() {
		return menuDao.findTopList();
	}

	public int getTotal(Map<String, Object> queryMap) {
		return menuDao.getTotal(queryMap);
	}

	public int edit(Menu menu) {
		return menuDao.edit(menu);
	}

	public int delete(Long ids) {
		return menuDao.delete(ids);
	}

	public List<Menu> findChildernList(Long parentId) {
		// TODO Auto-generated method stub
		return menuDao.findChildernList(parentId);
	}

	public List<Menu> findListByIds(String ids) {
		return menuDao.findListByIds(ids);
	}

}
