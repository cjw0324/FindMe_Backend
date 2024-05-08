package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.Items;
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