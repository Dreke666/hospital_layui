package com.zhang.hospital.web;

import com.zhang.hospital.entity.Depart;
import com.zhang.hospital.entity.Doctor;
import com.zhang.hospital.service.DepartService;
import com.zhang.hospital.service.DoctorService;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/depart")
public class DepartController {

    @Autowired
    private DepartService departService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping("/departList")
    public String departList(){
        return "/jsp/depart/departList";
    }

    @RequestMapping("/getDepartList")
    @ResponseBody
    public ResultUtil getDepartList(Integer page, Integer limit)
    {
        return departService.getDepartList(page,limit);
    }


    @RequestMapping("/addDepart")
    public String  addDepart()
    {
        return "/jsp/depart/addDepart";
    }
    // 检查角色是否唯一 添加新角色的时候用这个函数
    @RequestMapping("/checkDepartName/{depart}")
    @ResponseBody
    public ResultUtil checkDepartName(@PathVariable("depart") String depart)
    {
        Depart depart1=departService.getDepartByDepartName(depart);
        if(depart1==null)
        {
            return new ResultUtil(0);
        }
        else  //此角色名已存在
        {
            return new ResultUtil(500,"已经存在此科室名称");
        }
    }

    @RequestMapping("/insDepart")
    @ResponseBody
    public ResultUtil insDepart(Depart depart)
    {
        departService.addDepart(depart);
        return ResultUtil.ok();
    }


    @RequestMapping("/editDepart/{depart_id}")
    public String editDepart(@PathVariable("depart_id") Integer depart_id, Model model)
    {
        Depart depart=departService.getDepartById(depart_id);
        model.addAttribute("depart",depart);
        return "/jsp/depart/editDepart";
    }

    @RequestMapping("/updateDepart")
    @ResponseBody
    public void updateDepart(Depart depart)
    {

        departService.updateDepart(depart);

    }




    @RequestMapping("/delDepartById/{depart_id}")
    @ResponseBody
    public ResultUtil delDepartById(@PathVariable("depart_id")Integer depart_id)
    {

        //先检查此科室下有没有医生
        List<Doctor> doctors=doctorService.getDoctorByDepartId(depart_id);
        if(doctors.size()==0)//此科室下没有医生可以删除
        {
            departService.delDepartById(depart_id);
            return ResultUtil.ok("此科室删除成功");
        }

       // departService.delDepartById(depart_id);
        return ResultUtil.error("此科室下有医生，不可以删除");
    }



}
