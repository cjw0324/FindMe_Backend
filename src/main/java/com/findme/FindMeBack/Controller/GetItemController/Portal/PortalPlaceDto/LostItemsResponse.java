package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalPlaceDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LostItemsResponse {
    @JsonProperty("response")
    private Response response;
}
