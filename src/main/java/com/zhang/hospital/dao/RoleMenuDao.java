package com.zhang.hospital.dao;

import com.zhang.hospital.entity.RoleMenu;

import java.util.List;

public interface RoleMenuDao
{
    List<RoleMenu> selMenusByRoleId(Long roleId);

    void deleteMenusByRoleId(Long roleId);

    void insert(RoleMenu roleMenu);

    void deleteMenuByMenuId(Long menuId);

    List<RoleMenu> getRoleMenuByMenuId(Long menuId);
}
