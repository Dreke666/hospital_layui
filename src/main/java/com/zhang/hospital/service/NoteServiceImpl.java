package com.zhang.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.hospital.dao.NoteDao;
import com.zhang.hospital.entity.Note;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//病历
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteDao noteDao;

    @Override
    public void insNote(Note note) {
        noteDao.insNote(note);
    }

    @Override
    public ResultUtil getNoteList(Integer page, Integer limit,Integer doctor_id) {
        PageHelper.startPage(page,limit);
        List<Note> notes=noteDao.getNoteList(doctor_id);
        PageInfo<Note> pageInfo=new PageInfo<>(notes);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(pageInfo.getTotal());
        resultUtil.setData(pageInfo.getList());
        return resultUtil;
    }

    @Override
    public Note getNoteById(int note_id) {
        return noteDao.getNoteById(note_id);
    }

    @Override
    public void deleteNoteById(Integer note_id) {
        noteDao.deleteNoteById(note_id);
    }

    @Override
    public void updateNote(Note note) {
        noteDao.updateNote(note);
    }
}
