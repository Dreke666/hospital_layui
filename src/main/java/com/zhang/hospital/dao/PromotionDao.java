package com.zhang.hospital.dao;

import com.zhang.hospital.entity.Promotion;

import java.util.List;

public interface PromotionDao {
    List<Promotion> getPromotionList();

    void insPromotion(Promotion promotion);

    void delPromotionById(Integer promotion_id);
}
