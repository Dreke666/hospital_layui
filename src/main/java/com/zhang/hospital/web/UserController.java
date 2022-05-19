package com.zhang.hospital.web;

import com.zhang.hospital.entity.*;
import com.zhang.hospital.service.DepartService;
import com.zhang.hospital.service.DoctorService;
import com.zhang.hospital.service.UserService;
import com.zhang.hospital.util.EncryptUtil;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private DepartService departService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping("/login")
    @ResponseBody
    public ResultUtil login(String username, String password, HttpServletRequest request, HttpSession session)
    {
        User user=userService.login(username,EncryptUtil.encrypt(password));

        if(user!=null)
        {
            Integer status=user.getStatus();
            if(status==0)
            {
                 return ResultUtil.error("对不起，此用户被禁用，请联系超级管理员");
            }
            Admin admin=new Admin();
            session.setAttribute("user",admin); //拦截器中用 跳过拦截器
            session.setAttribute("hospital_user",user);
            session.setAttribute("usertype",2);//代表患者登陆
            session.setAttribute("hospital_user_id",user.getUser_id());
            session.setAttribute("hospital_user_photo",user.getPhoto_path());//获得头像图片名
            return ResultUtil.ok();
        }
        else
        {
            return ResultUtil.error("用户名或者密码错误，请重新输入");
        }

    }

    @RequestMapping("/getMenus")
    @ResponseBody
    public List<Menu> getMenus(HttpSession session)
    {
        //System.out.println("学生获取菜单进来了");
        List<Menu> menus=null;
        User user= (User) session.getAttribute("hospital_user");
        User user1=userService.getUserById(user.getUser_id());
        if(user1!=null)
        {
            menus=userService.getMenus(user1);
        }
        return menus;
    }

    @RequestMapping("/logOut")
    public ModelAndView logOut(ModelAndView modelAndView, HttpSession session)
    {
        session.invalidate();
        modelAndView.setViewName("redirect:/index.jsp");
        return modelAndView;
    }


    @RequestMapping("/info")
    public String info(HttpSession session)
    {
        //覆盖一下之前的hospital_user 不然显示的还是之前的hospital_user
        Integer user_id= (Integer) session.getAttribute("hospital_user_id");
        User user=userService.getUserById(user_id);
        session.setAttribute("hospital_user",user);
        return "/jsp/user/editUser";
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public ResultUtil updateUser(User user)throws ParseException
    {


        return userService.updateUser(user);
    }

    @RequestMapping("/changePassword")
    public  String changePassword()
    {
        return "/jsp/user/changePassword";
    }
    @RequestMapping("/changeUserPassword")
    @ResponseBody
    public ResultUtil changeStudentPassword(String oldPassword,String newPassword1,String username)
    {
        User user=userService.getUsertByUserName(username);
        if(user!=null)
        {
            if(user.getPassword().equals(EncryptUtil.encrypt(oldPassword)))
            {
                user.setPassword(EncryptUtil.encrypt(newPassword1));
                userService.updateUser(user);
                return ResultUtil.ok();
            }
            else
            {
                return ResultUtil.error("旧密码错误，请重新填写");
            }
        }
        return ResultUtil.error("请求出错！！");

    }

    @RequestMapping("/newUser")
    @ResponseBody
    public  ResultUtil newUser(User user)
    {
        try
        {
            user.setStatus(1); //1表示正常
            user.setRoleId((long) 2); //角色里表示一般用户
            user.setLevelId(1); //普通用户
            user.setPassword(EncryptUtil.encrypt(user.getPassword()));
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime=simpleDateFormat.format(date);
            Date create_time=simpleDateFormat.parse(nowTime);

            user.setCreateTime(create_time);
            userService.insUser(user);
            return ResultUtil.ok();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultUtil.error();
        }

    }


    //下面是用户注册的时候 上传的头像
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> image(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        String filename = "";

        String localPath = "D:\\IdeaWorkSpace\\hospital_img\\"; //存放我们上传的头像
        try {
            if (file != null) {
                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType = file.getContentType();
                //获得文件后缀名
                String suffixName = contentType.substring(contentType.indexOf("/") + 1);
                //得到 文件名(随机数+uuid+后缀)
                filename = (int)((Math.random())*100000000)+uuid + "." + suffixName;

//            判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {

                    new File(localPath).mkdirs();
                }
//            复制到当前文件夹
                file.transferTo(new File(localPath + filename));

            }
        } catch (Exception e) {

            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", filename);
            return map;

        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", filename);
        return map;
    }

    @RequestMapping("/doctorList")
    public String doctorList(HttpSession session)
    {
        List<Depart> departs=departService.getAllDeparts();
        session.setAttribute("departs",departs);
        return "/jsp/user/doctorList";
    }

}
