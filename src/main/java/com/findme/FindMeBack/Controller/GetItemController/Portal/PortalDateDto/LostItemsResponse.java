package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LostItemsResponse {
    @JsonProperty("response")
    private Response response;

    public Items getItems() {
        return response.getBody().getItems();
    }
}
