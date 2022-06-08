package com.ssg.product.repository;

import com.ssg.product.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {

//    List<PromotionEntity> findByPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

}
