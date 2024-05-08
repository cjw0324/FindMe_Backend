package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PolicePlaceDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {
    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("resultMsg")
    private String resultMsg;

    // getters and setters
}