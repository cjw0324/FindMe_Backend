package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalDateDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String atcId;
    private String depPlace;
    private String fdFilePathImg;
    private String fdPrdtNm;
    private String fdSbjt;
    private String fdSn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;
    private String prdtClNm;
    private String rnum;
    private String clrNm;

}
