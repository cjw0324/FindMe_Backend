package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}