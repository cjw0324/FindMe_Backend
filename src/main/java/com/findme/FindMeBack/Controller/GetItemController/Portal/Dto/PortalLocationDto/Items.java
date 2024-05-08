package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalLocationDto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}