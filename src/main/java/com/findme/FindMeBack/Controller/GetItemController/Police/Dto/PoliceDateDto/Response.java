package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

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
        private int numOfRows; // numOfRows 필드 추가
        private Object items;
    }
}
