package com.ssg.product.repository;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.PromotionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionItemRepository extends JpaRepository<PromotionItemEntity, Long> {

    @Query("select p from PromotionItemEntity p where p.item = ?1 and p.promotion.promotionStartDate <= ?2 and p.promotion.promotionEndDate >= ?3")
    List<PromotionItemEntity> findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(ItemEntity item, LocalDate startDate, LocalDate endDate);
}
