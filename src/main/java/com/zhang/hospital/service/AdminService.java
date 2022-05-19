package com.zhang.hospital.service;

import com.zhang.hospital.entity.*;
import com.zhang.hospital.util.ResultUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface AdminService {
    Admin login(String username, String password);

    void insAdminLog(String username, String loginIp, Date loginTime,Date logoutTime,Integer isSafeExit);

    Admin getAdminById(Integer id);

    List<Menu> getMenus(Admin admin);

    ResultUtil getAdminList(Integer page, Integer limit);

    List<Role> getRoles();
    ResultUtil getRoles(Integer page,Integer limit);

    void updAdmin(Admin admin);

    void delAdminById(Long id);

    Admin getAdminByUsername(String username);

    void insAdmin(Admin admin);

    void delRole(Long roleId);

    Role getRoleById(Long roleId);

  //  List<Menu> getXtreeData(Admin admin);
    List<Menu> getXtreeData(Long  roleId);

    Role getRoleByRoleName(String roleName);

    void updRole(Role role, String m);

    void insRole(Role role, String m);

    List<Menu> getAllMenus();

    void updMenuSortingById(Menu menu);

    Menu getMenuById(Long menuId);

    List<Menu> checkNameSameLevel(Menu menus);

    void insMenu(Menu menus);

    void updMenu(Menu menus);

    List<Menu> getMenusByParentId(Long menuId);

    void delMenuById(Long menuId);

    List<RoleMenu> getRoleMenuByMenuId(Long menuId);


    AdminLog getAdminLogByUsername(String username);

    void updateAdminLog(AdminLog adminLog);

    AdminLog getAdminLogByLoginTime(Date loginTime);

    ResultUtil getAdminLogList(Integer page, Integer limit) throws ParseException;
}
