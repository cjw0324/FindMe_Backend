package com.findme.FindMeBack.Controller.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@RequiredArgsConstructor
public class MainPageDto {
    public Long dateTableCount;
    public Long infoTableCount;
}
