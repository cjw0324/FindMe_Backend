package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalPlaceDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}