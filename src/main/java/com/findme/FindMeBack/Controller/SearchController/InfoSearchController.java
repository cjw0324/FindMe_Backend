package com.findme.FindMeBack.Controller.SearchController;

import com.findme.FindMeBack.Controller.Dto.InfoItemDto;
import com.findme.FindMeBack.Controller.Dto.SearchDto;
import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Service.SaveService.InfoItemService;
import com.findme.FindMeBack.Service.SearchService.SearchFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class InfoSearchController {
    private final InfoItemService infoItemService;

    @GetMapping("/info/{atcid}")
    public InfoItemDto searchWithLocationCode(
            @PathVariable String atcid
    ) throws ParseException {

        InfoItem infoItem = infoItemService.findByAtcId(atcid).orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + atcid));
        InfoItemDto infoItemDto = new InfoItemDto(
                infoItem.getAtcId(),
                infoItem.getCsteSteNm(),
                infoItem.getDepPlace(),
                infoItem.getFdFilePathImg(),
                infoItem.getFdHor(),
                infoItem.getFdPlace(),
                infoItem.getFdPrdtNm(),
                infoItem.getFdSn(),
                infoItem.getFdYmd(),
                infoItem.getFndKeepOrgnSeNm(),
                infoItem.getOrgId(),
                infoItem.getOrgNm(),
                infoItem.getPrdtClNm(),
                infoItem.getTel(),
                infoItem.getUniq()
        );

        return infoItemDto;
    }

}
