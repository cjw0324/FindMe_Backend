package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.Body;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.Header;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("body")
    private Body body;
}