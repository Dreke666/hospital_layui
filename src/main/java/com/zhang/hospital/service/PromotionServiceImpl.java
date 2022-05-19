package com.zhang.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.hospital.dao.PromotionDao;
import com.zhang.hospital.entity.Promotion;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService
{

    @Autowired
    private PromotionDao promotionDao;

    @Override
    public ResultUtil getPromotionList(Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Promotion> promotionList=promotionDao.getPromotionList();
        PageInfo<Promotion> promotionPageInfo=new PageInfo<>(promotionList);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(promotionPageInfo.getTotal());
        resultUtil.setData(promotionPageInfo.getList());
        return resultUtil;
    }

    @Override
    public void insPromotion(Promotion promotion)
    {
        promotionDao.insPromotion(promotion);
    }

    @Override
    public void delPromotionById(Integer promotion_id)
    {
        promotionDao.delPromotionById(promotion_id);
    }
}
