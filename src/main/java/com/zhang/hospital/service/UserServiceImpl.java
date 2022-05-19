package com.zhang.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.hospital.dao.MenuDao;
import com.zhang.hospital.dao.RoleMenuDao;
import com.zhang.hospital.dao.UserDao;
import com.zhang.hospital.entity.*;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional

public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public void insUser(User user) {
        userDao.insUser(user);
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username,password);
    }

    @Override
    public List<Menu> getMenus(User user1)
    {
        List<Menu> menuList=new ArrayList<>();
        Long roleId=user1.getRoleId();
        List<RoleMenu> roleMenus=roleMenuDao.selMenusByRoleId(roleId);
        //rolemenu是一个中间表 两个字段 roleId menuId 关联起来
        if(roleMenus!=null&&roleMenus.size()>0)
        {
            List<Menu> noChildrenMenus=new ArrayList<>();
            for(int i=0;i<roleMenus.size();i++)
            {
                Menu menu=menuDao.getMenuById(roleMenus.get(i).getMenuId()); //获取rolemenu中的menuId
                noChildrenMenus.add(menu); //根据登陆者的roleid 所拥有的菜单取出来
            }
            for(int i=0;i<noChildrenMenus.size();i++)
            {
                if(noChildrenMenus.get(i).getParentId()==0)
                //说明这个菜单是一级菜单 没有上一级菜单
                //如果这里不为0 表示这个菜单是二级菜单 或者三级菜单 ParentId对应了属于哪个上级菜单
                {
                    Menu menu=new Menu();
                    menu=noChildrenMenus.get(i); //把这个一级菜单取出来
                    List<Menu> menus=new ArrayList<>();
                    for(int j=0;j<noChildrenMenus.size();j++) //把所有菜单过一遍
                    {
                        //如果有菜单属于这个一级菜单
                        if(noChildrenMenus.get(j).getParentId()==noChildrenMenus.get(i).getMenuId())
                        {
                            Menu menu2=new Menu();
                            menu2=noChildrenMenus.get(j);//取出这个二级菜单
                            menus.add(menu2);
                        }
                    }
                    menu.setChildren(menus); //存放了属于这个一级菜单的所有二级菜单
                    menuList.add(menu);//存放了所有的一级菜单和其对应的所有二级菜单
                }
            }

        }
        //下面是根据menu的sorting进行排序 升序排列 这样左侧菜单就会按照升序排列
        Collections.sort(menuList, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {

                return o1.getSorting().compareTo(o2.getSorting());
            }
        });

        return menuList;
    }

    @Override
    public User getUserById(Integer user_id) {
        return userDao.getUserById(user_id);
    }

    @Override
    public ResultUtil updateUser(User user) {
        userDao.updateUser(user);
        return ResultUtil.ok();
    }

    @Override
    public User getUsertByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    public ResultUtil getAllUserList(Integer page, Integer limit, UserSearch search) {
        PageHelper.startPage(page,limit);
        List<User> users=userDao.getAllUserList(search);
        PageInfo<User> pageInfo=new PageInfo<>(users);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(pageInfo.getTotal());
        resultUtil.setData(pageInfo.getList());
        return resultUtil;
    }

    @Override
    public ResultUtil updateUserStatusById(int user_id, int status) {
         userDao.updateUserStatusById(user_id,status);
         return ResultUtil.ok();
    }

    @Override
    public ResultUtil deleteUserById(int user_id) {
        userDao.deleteUserById(user_id);
        return ResultUtil.ok();
    }

}
