package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalLocationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

// API 응답 전체 내용 저장 클래스
@Getter
@Setter
public class LostItemsResponse {
    @JsonProperty("response")
    private Response response;
}
