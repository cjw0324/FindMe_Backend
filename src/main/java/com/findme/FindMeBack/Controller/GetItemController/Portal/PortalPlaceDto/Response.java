package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalPlaceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalPlaceController;
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
