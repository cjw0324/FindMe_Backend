package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Repository.InfoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InfoItemService {
    private final InfoItemRepository infoItemRepository;
    public void save(InfoItem infoItem) {
        infoItemRepository.save(infoItem);
    }

    public Optional<List<InfoItem>> findAll() {
        return Optional.of(infoItemRepository.findAll());
    }

    public void delete(String atcid) {
        infoItemRepository.deleteByAtcId(atcid);
    }


}
