package com.findme.FindMeBack.Controller.UserPageController;

import com.findme.FindMeBack.Controller.Dto.MainPageDto;
import com.findme.FindMeBack.Service.SaveService.DateItemService;
import com.findme.FindMeBack.Service.SaveService.InfoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MainPageController {
    private final InfoItemService infoItemService;

    @GetMapping("/main")
    public MainPageDto MainPage() {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setFoundItemCount(infoItemService.getFoundItemCount());
        mainPageDto.setNotFoundItemCount(infoItemService.getNotFoundItemCount());
        return mainPageDto;
    }
}
