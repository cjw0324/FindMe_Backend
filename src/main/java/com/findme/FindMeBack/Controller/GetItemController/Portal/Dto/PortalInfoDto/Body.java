package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalInfoDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto.Item;

public class Body {
    @JacksonXmlProperty(localName = "item")
    private Item item;

    // getters and setters
}