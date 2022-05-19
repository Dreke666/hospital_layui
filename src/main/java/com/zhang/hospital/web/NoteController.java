package com.zhang.hospital.web;


import com.zhang.hospital.entity.Note;
import com.zhang.hospital.entity.Reservation;
import com.zhang.hospital.entity.User;
import com.zhang.hospital.service.NoteService;
import com.zhang.hospital.service.ReservationService;
import com.zhang.hospital.service.UserService;
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
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/addNote/{user_id}/{res_id}")
    public String addNote(@PathVariable("user_id")int user_id,@PathVariable("res_id")int res_id, HttpSession session)
    {
        User user=userService.getUserById(user_id);
        session.setAttribute("hos_user",user);
        session.setAttribute("user_id",user_id); //这里保存下患者id
        session.setAttribute("res_id",res_id); //这里保存下患者id
        return "/jsp/note/addNote";
    }

    @RequestMapping("/insNote")
    public ResultUtil insNote(Note note,HttpSession session) throws ParseException {
        Integer user_id= (Integer) session.getAttribute("user_id");
        Integer doctor_id= (Integer) session.getAttribute("hospital_user_id");
        Integer res_id= (Integer) session.getAttribute("res_id");

        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=simpleDateFormat.format(date);
        Date create_time=simpleDateFormat.parse(nowTime);
        note.setCreate_time(create_time);
        note.setDoctor_id(doctor_id);
        note.setUser_id(user_id);

        Reservation reservation=reservationService.getResByResId(res_id);
        reservation.setStatus(3); //此预约已经是接诊状态
        reservationService.updateRes(reservation);

        noteService.insNote(note);
        return ResultUtil.ok("添加病历成功");
    }

    @RequestMapping("/noteList")
    public  String noteList()
    {
        return "/jsp/note/noteList";
    }
    @RequestMapping("/getNoteList")
    @ResponseBody
    public ResultUtil getNoteList(Integer page, Integer limit,HttpSession session)
    {
        Integer doctor_id= (Integer) session.getAttribute("hospital_user_id");
        return noteService.getNoteList(page,limit,doctor_id);
    }


    @RequestMapping("/editNote/{note_id}")
    public  String editNote(@PathVariable("note_id")int note_id, HttpSession session)
    {
        Note note=noteService.getNoteById(note_id);
        session.setAttribute("note",note);
        return "/jsp/note/editNote";
    }

    @RequestMapping("/updateNote")
    @ResponseBody
    public ResultUtil updateNote(Note note){
        System.out.println(note);
        noteService.updateNote(note);
        return ResultUtil.ok();
    }

    @RequestMapping("/deleteNoteById")
    @ResponseBody
    public ResultUtil deleteNoteById(Integer note_id){
        noteService.deleteNoteById(note_id);
        return ResultUtil.ok();
    }


}
