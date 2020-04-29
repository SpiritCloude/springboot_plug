package com.security.study.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface RoleMapper {

	@Select("select permission from users_roles left join role on role.id=users_roles.role_id where user_id=#{id}")
	String findRolesById(Long id);

	@Select("select menus.permission from role" + 
			" left join roles_menus on role.id=roles_menus.role_id" + 
			" left join menus on menus.id=roles_menus.menu_id" + 
			" where role.permission=#{roles}")
	List<String> findAuthByRoles(String roles);
}
