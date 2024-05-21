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

    @GetMapping("/search/{page}")
    public PaginatedSearchResultDto searchWithLocationCode(
            @RequestParam String nfdlctcd,
            @RequestParam String placekeyword,
            @RequestParam String productCategory,
            @RequestParam String foundPlace,
            @RequestParam String ymd,
            @RequestParam String etc,
            @PathVariable int page
    ) throws ParseException {
        System.out.println(nfdlctcd+","+productCategory+","+productCategory+","+foundPlace+","+ymd+","+etc);
        int pageSize = 10; // 페이지당 아이템 수
        int offset = (page - 1) * pageSize; // 오프셋 계산

        List<InfoItem> items =  searchFilterService.searchFilter(nfdlctcd, productCategory, foundPlace, ymd, placekeyword.trim(), etc); //place 앞, 뒤 공백 제거(trim)
        Long totalResultCounts = (long) items.size();
        List<InfoItem> paginatedItems = items.subList(offset, Math.min(offset + pageSize, items.size())); // 페이지네이션

        // InfoItem을 InfoDto로 변환
        List<SearchDto> searchDtos = paginatedItems.stream().map(item -> new SearchDto(
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
