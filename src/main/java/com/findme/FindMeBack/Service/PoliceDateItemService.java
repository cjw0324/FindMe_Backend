package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.PoliceDateItem;
import com.findme.FindMeBack.Repository.PoliceDateItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PoliceDateItemService {
    private final PoliceDateItemRepository policeDateItemRepository;

    public void save(PoliceDateItem policeDateItem) {
        policeDateItemRepository.save(policeDateItem);
    }

    public void saveAll(List<PoliceDateItem> policeDateItemList) {
        policeDateItemRepository.saveAll(policeDateItemList);
    }

    public Optional<PoliceDateItem> findByAtcId(String atcid) {
        return Optional.ofNullable(policeDateItemRepository
                .findByAtcId(atcid).orElseThrow(() -> new IllegalArgumentException("NOT FOUND: " + atcid)));
    }

    public Optional<List<PoliceDateItem>> findAll() {
        return Optional.of(policeDateItemRepository.findAll());
    }

    public void delete(String atcid) {
        policeDateItemRepository.deleteByAtcId(atcid);
    }

}
