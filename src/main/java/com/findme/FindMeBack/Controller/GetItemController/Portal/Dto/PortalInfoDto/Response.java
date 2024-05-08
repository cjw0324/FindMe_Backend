package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalInfoDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto.Body;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto.Header;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "response")
public class Response {
    @JacksonXmlProperty(localName = "header")
    private Header header;

    @JacksonXmlProperty(localName = "body")
    private Body body;

    // getters and setters
}