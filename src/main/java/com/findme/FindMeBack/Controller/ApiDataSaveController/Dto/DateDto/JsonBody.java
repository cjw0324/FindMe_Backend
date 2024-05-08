package com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonBody {
    public String START_YMD;      // 검색 시작일
    public String END_YMD;        // 검색 종료일
}
