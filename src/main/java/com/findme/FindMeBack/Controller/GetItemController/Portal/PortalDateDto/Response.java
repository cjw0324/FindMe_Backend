package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalDateDto;

import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalDateController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
        private Items items;
    }
}
