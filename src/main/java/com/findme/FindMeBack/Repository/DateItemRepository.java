package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.DateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DateItemRepository extends JpaRepository<DateItem, Long> {

    Optional<DateItem> findById(Long id);

    List<DateItem> findAll();


    void deleteByAtcId(String atcid);

}
