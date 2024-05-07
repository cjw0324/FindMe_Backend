package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Items {
    @JsonProperty("item")
    private List<Item> item;

}
