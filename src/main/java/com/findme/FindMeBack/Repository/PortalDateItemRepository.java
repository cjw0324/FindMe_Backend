package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.PortalDateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortalDateItemRepository extends JpaRepository<PortalDateItem, Long> {
    Optional<PortalDateItem> findByAtcId(String atcid);

    Optional<PortalDateItem> findById(Long id);

    List<PortalDateItem> findAll();

    void deleteByAtcId(String atcid);

}
