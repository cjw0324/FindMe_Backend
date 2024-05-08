package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.PortalDateItem;
import com.findme.FindMeBack.Repository.PortalDateItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortalDateItemService {
    private final PortalDateItemRepository portalDateItemRepository;

    public void save(PortalDateItem portalDateItem) { portalDateItemRepository.save(portalDateItem); }

    public void saveAll(List<PortalDateItem> portalDateItemList) {
        portalDateItemRepository.saveAll(portalDateItemList);
    }

    public Optional<PortalDateItem> findByAtcId(String atcid) {
        return Optional.ofNullable(portalDateItemRepository
                .findByAtcId(atcid).orElseThrow(() -> new IllegalArgumentException("NOT FOUND: " + atcid)));
    }

    public Optional<List<PortalDateItem>> findAll() {
        return Optional.of(portalDateItemRepository.findAll());
    }

    public void delete(String atcid) {
        portalDateItemRepository.deleteByAtcId(atcid);
    }

}
