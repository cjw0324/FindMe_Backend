package com.findme.FindMeBack.Controller.SearchController;

import com.findme.FindMeBack.Controller.Dto.PaginatedSearchResultDto;
import com.findme.FindMeBack.Controller.Dto.SearchDto;
import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Service.SearchService.SearchFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SearchFilterController {

    private final SearchFilterService searchFilterService;

    @GetMapping("/search/")
    public PaginatedSearchResultDto searchWithLocationCode(
            @RequestParam String nfdlctcd,
            @RequestParam String placekeyword,
            @RequestParam String productCategory,
            @RequestParam String foundPlace,
            @RequestParam String ymd,
            @RequestParam String placeEtc,
            @RequestParam String itemEtc
    ) throws ParseException {
        System.out.println(nfdlctcd+","+placekeyword+","+productCategory+","+foundPlace+","+ymd+","+placeEtc+", "+itemEtc);
        List<InfoItem> items = searchFilterService.searchFilter(nfdlctcd, productCategory, foundPlace, ymd, placekeyword.trim(), placeEtc, itemEtc);
        Long totalResultCounts = (long) items.size();

        // InfoItem을 InfoDto로 변환
        List<SearchDto> searchDtos = items.stream().map(item -> new SearchDto(
                item.getAtcId(),
                item.getFdPrdtNm(),
                item.getDepPlace(),
                item.getFdFilePathImg(),
                item.getFdYmd(),
                item.getFdPlace()
        )).toList();

        return new PaginatedSearchResultDto(totalResultCounts, searchDtos);
    }

}
