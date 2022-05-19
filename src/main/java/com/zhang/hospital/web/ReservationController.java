package com.zhang.hospital.web;

import com.zhang.hospital.entity.Reservation;
import com.zhang.hospital.service.ReservationService;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/chooseResDate/{doctor_id}")
    public String chooseResDate(@PathVariable("doctor_id")int doctor_id, HttpSession session)
    {

        session.setAttribute("res_doctor_id",doctor_id);
        return "/jsp/reservation/chooseResDate";
    }


    @RequestMapping("/addRes")
    @ResponseBody
    public ResultUtil addRes(Reservation reservation, HttpSession session) throws ParseException {

        Integer user_id= (Integer) session.getAttribute("hospital_user_id");
        Integer doctor_id=(Integer) session.getAttribute("res_doctor_id");
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=simpleDateFormat.format(date);
        Date create_time=simpleDateFormat.parse(nowTime);
        reservation.setCreate_time(create_time);
        reservation.setRes_doc_id(doctor_id);
        reservation.setRes_user_id(user_id);
        reservation.setStatus(1); //1表示预约状态

        reservationService.addReservation(reservation);
        return ResultUtil.ok();

    }


    @RequestMapping("/myReservationList")
    public String myReservationList()
    {
        return "/jsp/reservation/myReservationList";
    }

    @RequestMapping("/getAllMyReservationList")
    @ResponseBody
    public ResultUtil getAllMyReservationList(Integer page, Integer limit,HttpSession session)
    {
        Integer user_id= (Integer) session.getAttribute("hospital_user_id");
        return reservationService.getAllMyReservationList(page,limit,user_id);
    }

    @RequestMapping("/cancelRes")
    @ResponseBody
    public ResultUtil cancelRes(int res_id)
    {
        Reservation reservation=reservationService.getResByResId(res_id);
        reservation.setStatus(0);  //0为患者取消预约状态
        reservationService.updateRes(reservation);
        return ResultUtil.ok();
    }




    @RequestMapping("/doctorResList")
    public String doctorResList()
    {
        return "/jsp/reservation/doctorResList";
    }

    @RequestMapping("/getDoctorResList")
    @ResponseBody
    public ResultUtil getDoctorResList(Integer page, Integer limit,HttpSession session)
    {
        Integer doctor_id= (Integer) session.getAttribute("hospital_user_id");
        return reservationService.getDoctorResList(page,limit,doctor_id);
    }
    @RequestMapping("/acceptRes")
    @ResponseBody
    public ResultUtil acceptRes(int res_id)
    {
        Reservation reservation=reservationService.getResByResId(res_id);
        reservation.setStatus(2);  //2为接诊状态
        reservationService.updateRes(reservation);
        return ResultUtil.ok();
    }



//已接诊预约
    @RequestMapping("/doctorResDoneList")
    public String doctorResDoneList()
    {
        return "/jsp/reservation/doctorResDoneList";
    }

    @RequestMapping("/getDoctorResDoneList")
    @ResponseBody
    public ResultUtil getDoctorResDoneList(Integer page, Integer limit,HttpSession session)
    {
        Integer doctor_id= (Integer) session.getAttribute("hospital_user_id");
        return reservationService.getDoctorResDoneList(page,limit,doctor_id);
    }


}
