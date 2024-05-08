package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.PortalDateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortalDateItemRepository extends JpaRepository<PortalDateItem, Long> {
    Optional<PortalDateItem> findByAtcId(String atcid);

    Optional<PortalDateItem> findById(Long id);

    void deleteByAtcId(String atcid);

}
