package com.zhang.hospital.web;


import com.zhang.hospital.entity.*;
import com.zhang.hospital.service.AdminService;
import com.zhang.hospital.service.DepartService;
import com.zhang.hospital.service.DoctorService;
import com.zhang.hospital.service.UserService;
import com.zhang.hospital.util.EncryptUtil;
import com.zhang.hospital.util.GsonUtil;
import com.zhang.hospital.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DepartService departService;


    @RequestMapping("/login")
    @ResponseBody
    public ResultUtil login(String username, String password, HttpServletRequest request, HttpSession session)
            throws ParseException {
        Admin admin = adminService.login(username, EncryptUtil.encrypt(password));
        if (admin != null) {
            session.setAttribute("user", admin); //在拦截器中使用
            session.setAttribute("admin", admin); //在main.jsp中需要admin
            session.setAttribute("usertype", "1");//代表是管理员登录
            // 还有修改密码.jsp等都可以直接取出admin
            String loginIp = request.getHeader("x-forwarded-for");
            if (loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) {
                loginIp = request.getHeader("Proxy-Client-IP");//获取代理的IP
            }
            if (loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) {
                loginIp = request.getHeader("WL-Proxy-Client-IP");//获取代理的IP
            }
            if (loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) {
                loginIp = request.getRemoteAddr();
            }

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleDateFormat.format(date);
            Date loginTime = simpleDateFormat.parse(nowTime);

            Date logoutTime = loginTime;

            Integer isSafeExit = 0;

            adminService.insAdminLog(username, loginIp, loginTime, logoutTime, isSafeExit);

            session.setAttribute("adminloginTime", loginTime); //点击退出的时候用的到

            return ResultUtil.ok(admin.getId());
        } else
            return ResultUtil.error();
    }


    //获取所有的登录日志
    @RequestMapping("/getAdminLogList")
    @ResponseBody
    public ResultUtil getAdminLogList(Integer page, Integer limit, HttpSession session)
            throws ParseException {

        return adminService.getAdminLogList(page, limit);
    }


    @RequestMapping("/userList")
    public String userList() {
        return "/jsp/admin/userList";
    }

    @RequestMapping("/getAllUserList")
    @ResponseBody
    public ResultUtil getAllUserList(Integer page, Integer limit, UserSearch search) {
        return userService.getAllUserList(page, limit, search);
    }


    @RequestMapping("/doctorList")
    public String doctorList(HttpSession session) {
        List<Depart> departs = departService.getAllDeparts();
        session.setAttribute("departs", departs);
        return "/jsp/admin/doctorList";
    }

    @RequestMapping("/getAllDoctorList")
    @ResponseBody
    public ResultUtil getAllDoctorList(Integer page, Integer limit, DoctorSearch search) {

        return doctorService.getAllDoctorList(page, limit, search);
    }


    @RequestMapping("/deleteDoctorById")
    @ResponseBody
    public ResultUtil deleteDoctorById(int doctor_id) {
        return doctorService.deleteDoctorById(doctor_id);
    }

    @RequestMapping("/deleteUserById")
    @ResponseBody
    public ResultUtil deleteUserById(int user_id) {
        return userService.deleteUserById(user_id);
    }


    @RequestMapping("/editUser/{user_id}")
    public String editStudent(@PathVariable("user_id") int user_id, HttpSession session) {
        User user = userService.getUserById(user_id);
        session.setAttribute("hospital_user", user);
        return "/jsp/user/editUser";
    }


    @RequestMapping("/editDoctor/{doctor_id}")
    public String editDoctor(@PathVariable("doctor_id") int doctor_id, HttpSession session) {

        Doctor doctor = doctorService.getDoctorById(doctor_id);

        session.setAttribute("hospital_user", doctor);
        return "/jsp/admin/editDoctor";
    }

    /********Role相关*******/
    //获取角色列表 带分页
    @RequestMapping("/getRoleList")
    @ResponseBody
    public ResultUtil getRoleList(Integer page, Integer limit) {
        return adminService.getRoles(page, limit);
    }

    //删除一个角色
    @RequestMapping("/delRole/{roleId}")
    @ResponseBody
    public ResultUtil delRole(@PathVariable("roleId") Long roleId) {
        adminService.delRole(roleId);
        return ResultUtil.ok();

    }

    //得到指定角色权限树
    // 注解RequestMapping中produces属性可以设置返回数据的类型以及编码，可以是json
    @RequestMapping(value = "/xtreedata", produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String xtreeData(@RequestParam(value = "roleId", defaultValue = "-1") Long roleId) {

        return GsonUtil.entityToJson(adminService.getXtreeData(roleId));
    }

    // 检查角色是否唯一
    @RequestMapping("/checkRoleName/{roleName}/{roleId}")
    @ResponseBody
    public ResultUtil checkRoleName(@PathVariable("roleName") String roleName,
                                    @PathVariable("roleId") Long roleId) {
        Role role = adminService.getRoleByRoleName(roleName);
        if (role == null)
        {
            return new ResultUtil(0);
        } else if (role.getRoleId() == roleId) //已经有这个角色名 并且就是这个id 也可以
        {
            return new ResultUtil(0);
        } else  //此角色名已存在 别的roleId
        {
            return new ResultUtil(500, "已经存在此角色名");
        }
    }

    // 检查角色是否唯一 添加新角色的时候用这个函数
    @RequestMapping("/checkAddRoleName/{roleName}")
    @ResponseBody
    public ResultUtil checkRoleName(@PathVariable("roleName") String roleName) {
        Role role = adminService.getRoleByRoleName(roleName);
        if (role == null)//没有这个角色名 可以
        {
            return new ResultUtil(0);
        } else  //此角色名已存在
        {
            return new ResultUtil(500, "已经存在此角色名");
        }
    }

    //更新角色
    @RequestMapping("/updateRole")
    @ResponseBody
    public void updateRole(Role role, String m) {
        // System.out.println(role);
        // System.out.println(m);
        adminService.updRole(role, m);
    }

    //添加角色
    @RequestMapping("/insRole")
    @ResponseBody
    public ResultUtil insertRole(Role role, String m) {

        adminService.insRole(role, m);
        return ResultUtil.ok();

    }


    /*******管理员相关******/
    //查看管理员的个人信息
    @RequestMapping("/personalDate")
    public String personalDate(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        Admin admin1 = adminService.getAdminById(admin.getId());
        session.setAttribute("admin1", admin1);
        return "/jsp/admin/personalInfo";
    }


    @RequestMapping("/getAdminList")
    @ResponseBody
    //获取所有管理员列表 带分页
    public ResultUtil getAdminList(Integer page, Integer limit) {
        // Tomcat Localhost Log 会输出错误信息 如果下面的sql语句有问题
        ResultUtil admins = adminService.getAdminList(page, limit);
        return admins;
    }
    //获取管理员对应的菜单 用于加载后台首页
    @RequestMapping("/getMenus")
    @ResponseBody
    public List<Menu> getMenus(HttpSession session) {
        List<Menu> menus = null;
        Admin admin = (Admin) session.getAttribute("admin");
        Admin admin1 = adminService.getAdminById(admin.getId());
        if (admin1 != null) {
            menus = adminService.getMenus(admin1);
        }
        return menus;
    }

    //编辑管理员
    @RequestMapping("/editAdmin/{id}")
    public String editAdmin(HttpServletRequest request, @PathVariable("id") int id) {
        Admin admin = adminService.getAdminById(id);
        List<Role> roles = adminService.getRoles();
        request.setAttribute("admin", admin);
        request.setAttribute("roles", roles);
        return "/jsp/admin/editAdmin";
    }

    //更新管理员
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public ResultUtil updateAdmin(Admin admin) {
        try {
            adminService.updAdmin(admin);
            return ResultUtil.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    //更新管理员
    @RequestMapping("/updAdmin")
    @ResponseBody
    public ResultUtil updAdmin(Admin admin)
    {
        if (admin != null && admin.getId() == 1) {
            return ResultUtil.error("不允许修改");
        }
        try
        {
            //如果sql执行失败 会有捕获异常输出
            adminService.updAdmin(admin);
            return ResultUtil.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }

    }

    //修改密码
    @RequestMapping("/changeAdminPassword")
    @ResponseBody
    public ResultUtil changeAdminPassword(String oldPassword, String newPassword1, String username) {

        Admin admin = adminService.getAdminByUsername(username);
        if (admin != null) {
            if (admin.getPassword().equals(EncryptUtil.encrypt(oldPassword))) {
                admin.setPassword(EncryptUtil.encrypt(newPassword1));
                adminService.updAdmin(admin);
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("旧密码错误，请重新填写");
            }
        }
        return ResultUtil.error("请求出错！！");
    }


    //删除一个管理员
    @RequestMapping("/delAdminById/{id}")
    @ResponseBody
    public ResultUtil delAdminById(@PathVariable("id") Long id) {
        if (id == 1) {
            return ResultUtil.error();
        }
        try {
            adminService.delAdminById(id);
            return ResultUtil.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    //检查是否已经存在此用户名
    @RequestMapping("/checkAdminName/{username}")
    @ResponseBody
    public ResultUtil checkAdminName(@PathVariable("username") String username) {
        Admin admin = adminService.getAdminByUsername(username);
        if (admin != null) {
            return new ResultUtil(500, "管理员已存在!");
        }
        return new ResultUtil(0);
    }

    //添加新管理员
    @RequestMapping("/insAdmin")
    @ResponseBody
    public ResultUtil insAdmin(Admin admin) {
        adminService.insAdmin(admin);
        return ResultUtil.ok();
    }

    /****菜单相关******/
    //获取所有菜单
    @RequestMapping("/menuData")
    @ResponseBody
    public ResultUtil menuData() {
        List<Menu> list = adminService.getAllMenus();
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(list.size() + 0L);
        resultUtil.setData(list);
        return resultUtil;
    }


    @RequestMapping("/updMenuSortingById")
    @ResponseBody
    public ResultUtil updMenuSortingById(Long menuId, String sorting) {
        Menu menu = new Menu();
        menu.setMenuId(menuId);
        Long sortingL;
        try {
            sortingL = Long.parseLong(sorting); //将字符串变成Long
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("修改失败，只允许输入整数");
        }
        menu.setSorting(sortingL);
        adminService.updMenuSortingById(menu);
        return ResultUtil.ok();
    }

    @RequestMapping("/toSaveMenu/{menuId}/{menuLevel}")
    public String toSaveMenu(@PathVariable("menuId") Long menuId,
                             @PathVariable("menuLevel") Long menuLevel, Model model) {
        if (menuId != null && menuId != 1) {
            Menu menus = new Menu();
            menus.setMenuId(menuId);
            model.addAttribute("menuLevel", menuLevel);
            model.addAttribute("menu", menus);
            model.addAttribute("flag", "1");
            return "/jsp/menu/menuForm";
        } else {
            model.addAttribute("msg", "不允许操作！");
            return "/jsp/active";
        }

    }


    @RequestMapping("/toEditMenu/{menuId}")
    public String toEditMenu(@PathVariable("menuId") Long menuId, Model model) {
        if (menuId != null && menuId != 1) {
            Menu menus = adminService.getMenuById(menuId);
            model.addAttribute("menu", menus);
            return "/jsp/menu/menuForm";
        } else {
            model.addAttribute("msg", "不允许操作");
            return "/jsp/active";
        }
    }


    @RequestMapping("/menuForm")
    @ResponseBody
    public ResultUtil menuForm(Menu menus, String flag) {
        if (StringUtils.isBlank(flag))
        {
            menus.setSpread("false");
            adminService.updMenu(menus);
            return ResultUtil.ok("修改成功");
        } else if (menus.getMenuId() != 1)
        {

            menus.setParentId(menus.getMenuId()); //menuId parentId设成一样 情况1： 0,0  情况2： 2,2  情况3：111,111
            Menu m = adminService.getMenuById(menus.getMenuId());// 情况1 m为空 情况2 读出菜单 我的面板 情况3读出菜单 测试菜单
            if (m != null && m.getParentId() != 0)//这就是 选中第三级菜单复选框或者选中第二级菜单复选框的情况
            {
                Menu m1 = adminService.getMenuById(m.getParentId()); //获取第二级菜单或者第三级菜单的上级菜单
                if (m1 != null && m1.getParentId() != 0)//说明是情况3
                {
                    return ResultUtil.error("此菜单不允许添加子菜单");  //固定最多三级菜单
                }

            }
            List<Menu> data = adminService.checkNameSameLevel(menus); // sql为select * from menu parentId = #{parentId} and name = #{name}
            //就是判断顶级菜单 二级菜单下有没有重名的菜单
            if (data.size() > 0) {
                return ResultUtil.error("同级菜单名称不能相同");
            }
            menus.setMenuId(null);
            menus.setSpread("false"); //默认不展开
            adminService.insMenu(menus);
            return ResultUtil.ok("添加成功");
        } else {
            return ResultUtil.error("此菜单不允许操作");
        }
    }

    @RequestMapping("/delMenuById/{menuId}")
    @ResponseBody
    public ResultUtil delMenuById(@PathVariable("menuId") Long menuId) {
        try {
            if (menuId == 1) {
                return ResultUtil.error("此菜单不允许删除！");
            }
            List<Menu> data = adminService.getMenusByParentId(menuId);
            if (data != null && data.size() > 0) {
                return ResultUtil.error("包含子菜单，不允许删除！");
            }
            List<RoleMenu> roleMenus = adminService.getRoleMenuByMenuId(menuId);
            if (roleMenus != null && roleMenus.size() > 1) {
                return ResultUtil.error("此菜单已经分配给此登录角色，请先解除绑定，再尝试删除");
            }
            adminService.delMenuById(menuId);
            return ResultUtil.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("数据库sql有误查看Log输出");
        }
    }


    /********页面跳转*******/
    @RequestMapping("/logOut")
    public ModelAndView loginout(ModelAndView modelAndView, HttpSession session)
            throws ParseException {
        Date loginTime = (Date) session.getAttribute("adminloginTime");
        AdminLog adminLog = adminService.getAdminLogByLoginTime(loginTime);

        adminLog.setIsSafeExit(1);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(date);
        Date logoutTime = simpleDateFormat.parse(nowTime);

        adminLog.setLogoutTime(logoutTime);

        adminService.updateAdminLog(adminLog);

        session.invalidate();
        modelAndView.setViewName("redirect:/index.jsp");
        return modelAndView;
    }

    @RequestMapping("/allmain")
    public String index() {
        return "redirect:/admin/toallmain";
    }

    @RequestMapping("/toallmain")
    public String toallmain(ModelAndView modelAndView) {
        return "/jsp/allmain";
    }

    @RequestMapping("/main")
    public String getMain() {
        return "/jsp/main";
    }

    @RequestMapping("/adminList")
    public String adminList() {
        return "/jsp/admin/adminList";
    }

    @RequestMapping("/addAdmin")
    public String addAdmin(HttpSession session) {
        List<Role> roles = adminService.getRoles();
        session.setAttribute("roles", roles);
        return "/jsp/admin/addAdmin";
    }

    @RequestMapping("/roleList")
    public String roleList() {
        return "/jsp/role/roleList";
    }

    @RequestMapping("/editRole")
    public String editRole(Role role, Model model) {
        role = adminService.getRoleById(role.getRoleId());
        model.addAttribute("role", role);
        return "/jsp/role/editRole";
    }

    @RequestMapping("/addRole")
    public String addRole() {
        return "/jsp/role/addRole";
    }

    @RequestMapping("/menuList")
    public String menuList() {
        return "/jsp/menu/menuList";
    }

    @RequestMapping("/changePassword")
    public String changePassword() {
        return "/jsp/admin/changePassword";
    }

    @RequestMapping("/adminLoginLog")
    public String adminLoginLog() {
        return "/jsp/admin/adminLogList";
    }

    @RequestMapping("/register")
    public String register() {
        return "/jsp/register/register";
    }

}
