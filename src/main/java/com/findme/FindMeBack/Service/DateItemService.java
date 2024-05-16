package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.DateItem;
import com.findme.FindMeBack.Repository.DateItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DateItemService {
    private final DateItemRepository dateItemRepository;
    public void save(DateItem dateItem){
        dateItemRepository.save(dateItem);
    }

    public Optional<List<DateItem>> findAll() {
        return Optional.ofNullable(dateItemRepository.findAll());
    }

    public void delete(String atcid) {
        dateItemRepository.deleteByAtcId(atcid);
    }



}
