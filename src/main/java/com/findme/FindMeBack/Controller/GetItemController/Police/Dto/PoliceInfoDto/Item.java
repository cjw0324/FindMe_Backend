package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String atcId;
    private String csteSteNm;
    private String depPlace;
    private String fdFilePathImg;
    private String fdHor;
    private String fdPlace;
    private String fdPrdtNm;
    private Long fdSn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;
    private String fndKeepOrgnSeNm;
    private String orgId;
    private String orgNm;
    private String prdtClNm;
    private String tel;
    private String uniq;

    // getters and setters
}
