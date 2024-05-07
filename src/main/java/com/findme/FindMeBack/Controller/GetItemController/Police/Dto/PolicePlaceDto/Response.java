package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PolicePlaceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findme.FindMeBack.Controller.GetItemController.Police.PolicePlaceController;
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
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private int pageNo;
        private int totalCount;
        private int numOfRows;
        private Object items;
    }
}