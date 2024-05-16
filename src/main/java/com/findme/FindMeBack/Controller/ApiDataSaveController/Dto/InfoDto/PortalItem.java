package com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.InfoDto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortalItem {
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
    private String orgId;
    private String orgNm;
    private String prdtClNm;
    private String tel;
    private String uniq;
    // getters and setters
}