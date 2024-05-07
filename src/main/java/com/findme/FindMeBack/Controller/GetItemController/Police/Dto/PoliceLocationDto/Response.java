package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceLocationDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findme.FindMeBack.Controller.GetItemController.Police.PoliceLocationController;
import lombok.Getter;
import lombok.Setter;

// API 응답 내용 저장 클래스
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private Header header;
    private Body body;

    @Getter
    @Setter
    public class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public class Body {
        private int pageNo;
        private int totalCount;
        private int numOfRows;
        private Object items;
    }
}