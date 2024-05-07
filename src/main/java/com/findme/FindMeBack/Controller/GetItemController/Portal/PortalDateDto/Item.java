package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalDateDto;

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
    private Date fdYmd;
    private String prdtClNm;
    private String rnum;

}
