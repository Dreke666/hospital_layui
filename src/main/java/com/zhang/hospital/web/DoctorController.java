package com.zhang.hospital.web;

import com.zhang.hospital.entity.Admin;
import com.zhang.hospital.entity.Doctor;
import com.zhang.hospital.entity.Menu;
import com.zhang.hospital.service.DoctorService;
import com.zhang.hospital.util.EncryptUtil;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @RequestMapping("/login")
    @ResponseBody
    public ResultUtil login(String username, String password, HttpServletRequest request, HttpSession session) {
        Doctor doctor = doctorService.login(username, EncryptUtil.encrypt(password));

        if (doctor != null) {

            Integer depart_id = doctor.getDepart_id();
            if (depart_id != 0) //说明已经分配科室 可以登录
            {
                Admin admin = new Admin();
                session.setAttribute("user", admin); //拦截器中用 跳过拦截器
                session.setAttribute("hospital_user", doctor);
                session.setAttribute("usertype", 3);//代表医生登陆
                session.setAttribute("hospital_user_id", doctor.getDoctor_id());
                session.setAttribute("hospital_user_photo", doctor.getPhoto_path());//获得头像图片名
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("对不起，请先联系管理员分配科室");
            }

        } else {
            return ResultUtil.error("用户名或者密码错误，请重新输入");
        }

    }


    @RequestMapping("/info")
    public String info(HttpSession session) {
        //覆盖一下之前的hospital_user 不然显示的还是之前的hospital_user
        Integer doctor_id = (Integer) session.getAttribute("hospital_user_id");
        Doctor doctor = doctorService.getDoctorById(doctor_id);
        session.setAttribute("hospital_user", doctor);
        return "/jsp/doctor/editDoctor";
    }

    @RequestMapping("/updateDoctor")
    @ResponseBody
    public ResultUtil updateDoctor(Doctor doctor) throws ParseException {
        return doctorService.updateDoctor(doctor);
    }

    @RequestMapping("/changePassword")
    public String changePassword() {
        return "/jsp/doctor/changePassword";
    }

    @RequestMapping("/changeDoctorPassword")
    @ResponseBody
    public ResultUtil changeDoctorPassword(String username, String oldPassword, String newPassword1) {

        Doctor doctor = doctorService.getDoctorByUserName(username);

        if (doctor != null) {
            if (doctor.getPassword().equals(EncryptUtil.encrypt(oldPassword))) {
                doctor.setPassword(EncryptUtil.encrypt(newPassword1));
                doctorService.updateDoctor(doctor);
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("旧密码错误，请重新填写");
            }
        }
        return ResultUtil.error("请求出错！！");

    }

    //医生注册
    @RequestMapping("/newUser")
    @ResponseBody
    public ResultUtil newUser(Doctor doctor) {
        try {
            doctor.setRoleId(3); //角色里表示医生
            doctor.setDepart_id(1);//新注册医生 未分配科室
            doctor.setRank(String.valueOf(5)); //新注册的医生默认是实习
            doctor.setPassword(EncryptUtil.encrypt(doctor.getPassword()));
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleDateFormat.format(date);
            Date create_time = simpleDateFormat.parse(nowTime);

            doctor.setCreateTime(create_time);
            doctorService.insDoctor(doctor);
            return ResultUtil.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }

    }

    @RequestMapping("/getMenus")
    @ResponseBody
    public List<Menu> getMenus(HttpSession session) {
        List<Menu> menus = null;
        Doctor doctor = (Doctor) session.getAttribute("hospital_user");
        Doctor doctor1 = doctorService.getDoctorById(doctor.getDoctor_id());
        if (doctor1 != null) {
            menus = doctorService.getMenus(doctor1);
        }
        return menus;
    }

    @RequestMapping("/logOut")
    public ModelAndView logOut(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("redirect:/index.jsp");
        return modelAndView;
    }
}
