package com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Items {
    @JsonProperty("item")
    private List<Item> item;

    // getters and setters
}