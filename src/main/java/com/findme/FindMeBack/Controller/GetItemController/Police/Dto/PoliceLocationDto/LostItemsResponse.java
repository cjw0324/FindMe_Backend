package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceLocationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LostItemsResponse {

    @Getter
    @Setter
    @JsonProperty("response")
    private static Response response;
}