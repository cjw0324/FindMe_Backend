package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.Item;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}