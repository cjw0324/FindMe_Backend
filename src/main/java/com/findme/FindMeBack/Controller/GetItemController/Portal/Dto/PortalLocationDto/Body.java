package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalLocationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {
    @JsonProperty("items")
    private Items items;
    @JsonProperty("numOfRows")
    private int numOfRows;
    @JsonProperty("pageNo")
    private int pageNo;
    @JsonProperty("totalCount")
    private int totalCount;

    // getters and setters
}