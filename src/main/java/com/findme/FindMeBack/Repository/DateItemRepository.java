package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.DateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DateItemRepository extends JpaRepository<DateItem, String> {

    List<DateItem> findAll();


    void deleteByAtcId(String atcid);

    List<DateItem> findByNFdLctCd(String n_fd_lct_cd);
}
