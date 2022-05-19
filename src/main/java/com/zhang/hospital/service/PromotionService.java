package com.zhang.hospital.service;

import com.zhang.hospital.entity.Promotion;
import com.zhang.hospital.util.ResultUtil;

public interface PromotionService {
    ResultUtil getPromotionList(Integer page, Integer limit);

    void insPromotion(Promotion promotion);

    void delPromotionById(Integer promotion_id);
}
