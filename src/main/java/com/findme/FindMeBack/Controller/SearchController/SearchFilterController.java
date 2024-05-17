package com.findme.FindMeBack.Controller.SearchController;

import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Service.SaveService.InfoItemService;
import com.findme.FindMeBack.Service.SearchService.SearchFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchFilterController {

    private final SearchFilterService searchFilterService;

    private final InfoItemService infoItemService;

    @GetMapping("/search")
    public List<InfoItem> searchWithLocationCode(
            @RequestParam String nfdlctcd,
            @RequestParam String placekeyword,
            @RequestParam String productCategory,
            @RequestParam String foundPlace,
            @RequestParam String ymd,
            @RequestParam String etc
    ) throws ParseException {

        System.out.println("place : " + placekeyword);
        return searchFilterService.searchFilter(nfdlctcd, productCategory, foundPlace, ymd, placekeyword.trim(), etc); //place 앞, 뒤 공백 제거(trim)
    }

}
