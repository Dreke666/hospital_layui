package com.zhang.hospital.web;

import com.zhang.hospital.entity.Promotion;
import com.zhang.hospital.service.PromotionService;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/promotion")
public class PromotionController
{

    @Autowired
    private PromotionService promotionService;

    @RequestMapping("/promotionList")
    public String promotionList()
    {
        return "/jsp/promotion/promotionList";
    }


    @RequestMapping("/AdminPromotionList")
    public String AdminPromotionList()
    {
        return "/jsp/promotion/AdminPromotionList";
    }
    @RequestMapping("/getPromotionList")
    @ResponseBody
    public ResultUtil getPromotionList(Integer page,Integer limit)
    {
        return promotionService.getPromotionList(page,limit);
    }
    @RequestMapping("/addPromotion")
    public String addPromotion()
    {
        return "/jsp/promotion/addPromotion";
    }
    @RequestMapping("/insPromotion")
    @ResponseBody
    public ResultUtil insPromotion(Promotion promotion)
    {
        try
        {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleDateFormat.format(date);
            Date pub_date = simpleDateFormat.parse(nowTime);
            promotion.setPub_date(pub_date);
            promotionService.insPromotion(promotion);
            return ResultUtil.ok();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @RequestMapping("/delPromotionById/{promotion_id}")
    @ResponseBody
    public ResultUtil delPromotionById(@PathVariable("promotion_id")Integer promotion_id)
    {

        promotionService.delPromotionById(promotion_id);
        return ResultUtil.ok();
    }

}
