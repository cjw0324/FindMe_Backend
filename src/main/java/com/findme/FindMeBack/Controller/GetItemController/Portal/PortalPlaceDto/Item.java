package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalPlaceDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String fdSbjt;
    private String rnum;
    private String atcId;
    private String fdFilePathImg;
    private String fdSn;
    private String depPlace;
    private String prdtClNm;
    private Date fdYmd;
    private String fdPrdtNm;
    private String clrNm;
}
