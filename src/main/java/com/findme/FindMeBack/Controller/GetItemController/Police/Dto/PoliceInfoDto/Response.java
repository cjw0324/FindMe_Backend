package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto;

import lombok.Getter;
import lombok.Setter;

// API 응답의 Header 클래스
@Getter
@Setter
public class Response {
    private Header header;
    private Body body;
}