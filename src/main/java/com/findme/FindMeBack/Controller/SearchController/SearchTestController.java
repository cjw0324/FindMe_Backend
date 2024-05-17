package com.findme.FindMeBack.Controller.SearchController;

import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Service.SaveService.InfoItemService;
import com.findme.FindMeBack.Service.SearchService.SearchFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchTestController {

    private final SearchFilterService searchFilterService;

    private final InfoItemService infoItemService;

    @GetMapping("/test/search")
    public List<InfoItem> searchWithLocationCode(
            @RequestParam String place,
            @RequestParam String productCategory,
            @RequestParam String foundPlace,
            @RequestParam String ymd) throws ParseException {

        // 문자열로 전달된 ymd를 Date 객체로 변환
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateYmd = formatter.parse(ymd);

        return searchFilterService.searchFilter(place, productCategory, foundPlace, dateYmd);
    }

}
