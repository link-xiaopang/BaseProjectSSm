package com.link.ssm.util;

import java.util.ArrayList;
import java.util.List;

import com.link.ssm.entity.admin.Menu;

public class MenuUtil {

	//获取一级菜单
	public static List<Menu> getAllTopMenu(List<Menu> menuList) {
		List<Menu> ret = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if (menu.getParentId() == 0) {
				ret.add(menu);
			}
		}
		return ret;

	}

	//获取所有二级级菜单
	public static List<Menu> getAllSecondMenu(List<Menu> menuList) {
		List<Menu> ret = new ArrayList<Menu>();
		List<Menu> allTopMenu = getAllTopMenu(menuList);
		for (Menu menu : menuList) {
			for (Menu topMenu : allTopMenu) {
				if (menu.getParentId() == topMenu.getId()) {
					ret.add(menu);
					break;
				}
			}
		}
		return ret;
	}

	//获取某个url下的菜单按钮
	public static List<Menu> getAllThirdMenu(List<Menu> menuList, Long secondMenuId) {
		List<Menu> ret = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if (menu.getParentId() == secondMenuId) {
				ret.add(menu);
			}
		}
		return ret;
	}
}
