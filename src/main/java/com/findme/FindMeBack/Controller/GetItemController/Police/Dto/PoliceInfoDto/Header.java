package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto;

import lombok.Getter;
import lombok.Setter;

// API 응답의 Header 내부 클래스
@Getter
@Setter
public class Header {
    private String resultCode;
    private String resultMsg;
}