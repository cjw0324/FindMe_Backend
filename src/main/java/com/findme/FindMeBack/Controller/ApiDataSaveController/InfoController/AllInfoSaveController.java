package com.findme.FindMeBack.Controller.ApiDataSaveController.InfoController;

import com.findme.FindMeBack.Entity.DateItem;
import com.findme.FindMeBack.Service.SaveService.DateItemService;
import com.findme.FindMeBack.Service.SaveService.InfoItemService;
import lombok.RequiredArgsConstructor;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.InfoDto.JsonBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class AllInfoSaveController {
    private final InfoItemService infoItemService;
    private final DateItemService dateItemService;

    @Value("${my.api.key}")
    public String apiKey;

    @Value("${my.admin.key}")
    private String key;

    @PostMapping("/info/save")
    public String InfoSave(@RequestBody JsonBody jsonBody) throws IOException {
        if ((jsonBody.getADMIN_KEY()).equals(key) == false) {
            return "non valid admin key!!!";
        }

//        infoItemService.InfoSave("F2024050100000029");
        List<DateItem> dateAtcIdList = dateItemService.findAll().get();

        for (DateItem dateItem: dateAtcIdList) {

            infoItemService.InfoSave(dateItem.getAtcId());
            System.out.println("dateItem.getAtcId() = " + dateItem.getAtcId());
        }
        return "Info Table Save Done";

    }
}
