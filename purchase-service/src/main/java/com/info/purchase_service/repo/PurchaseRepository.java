package com.info.purchase_service.repo;

import com.info.purchase_service.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByUserId(Long userId);
    Optional<PurchaseOrder> findByTransactionId(String transactionId);
    boolean existsByUserIdAndCourseIdAndStatus(Long userId, Long courseId, String status);
}
