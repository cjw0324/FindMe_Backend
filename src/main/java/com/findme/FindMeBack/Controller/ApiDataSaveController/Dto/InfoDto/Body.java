package com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.InfoDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
public class Body {
    @JacksonXmlProperty(localName = "item")
    private Item item;

    // getters and setters
}