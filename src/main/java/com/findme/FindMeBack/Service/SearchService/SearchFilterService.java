package com.findme.FindMeBack.Service.SearchService;

import com.findme.FindMeBack.Entity.DateItem;
import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Repository.DateItemRepository;
import com.findme.FindMeBack.Repository.InfoItemRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class SearchFilterService {
    private final DateItemRepository dateItemRepository;
    private final InfoItemRepository infoItemRepository;

    public List<InfoItem> searchFilter(String place, String productCategory, String foundPlace, Date ymd) {
        // DateItem을 조건에 맞게 필터링하여 찾습니다.
        List<DateItem> dateItems = dateItemRepository.findByNFdLctCd(place)
                .stream()
                .filter(item -> item.getPrdtClNm().contains(productCategory)
                        && !item.getFdYmd().before(ymd)) // ymd와 동일하거나 이후 날짜 필터링
                .collect(Collectors.toList());

        // 찾은 DateItem 객체들로부터 atcId 리스트를 추출합니다.
        List<String> atcIds = dateItems.stream().map(DateItem::getAtcId).collect(Collectors.toList());

        // atcId 리스트를 기반으로 각 atcId에 해당하는 InfoItem을 찾아서 리스트로 반환합니다.
        List<InfoItem> infoItems = atcIds.stream()
                .map(infoItemRepository::findByAtcId) // Optional<InfoItem> 반환
                .filter(Optional::isPresent) // 값이 존재하는 Optional만 필터링
                .map(Optional::get) // Optional에서 실제 InfoItem 추출
                .filter(infoItem -> infoItem.getFdPlace().equals(foundPlace) && infoItem.getCsteSteNm().equals("보관중")) // InfoItem에서 fdPlace 조건 필터링
                .collect(Collectors.toList()); // InfoItem 객체를 List에 수집

        return infoItems;
    }

}
