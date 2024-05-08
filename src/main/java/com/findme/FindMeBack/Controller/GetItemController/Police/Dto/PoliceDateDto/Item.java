package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto;

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
    private Long fdSn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;
    private String prdtClNm;
    private Long rnum;
    private String clrNm;
}