package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.InfoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoItemRepository extends JpaRepository<InfoItem, Long> {
    Optional<InfoItem> findById(Long id);
    List<InfoItem> findAll();

    void deleteByAtcId(String atcid);
}
