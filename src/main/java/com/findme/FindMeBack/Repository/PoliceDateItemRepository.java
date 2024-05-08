package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.PoliceDateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliceDateItemRepository extends JpaRepository<PoliceDateItem, Long> {
    Optional<PoliceDateItem> findByAtcId(String atcid);

    Optional<PoliceDateItem> findById(Long id);

    void deleteByAtcId(String atcid);

}
