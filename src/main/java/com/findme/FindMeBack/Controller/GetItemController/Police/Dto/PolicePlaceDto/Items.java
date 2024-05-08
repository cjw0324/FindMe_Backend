package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PolicePlaceDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceLocationDto.Item;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}
