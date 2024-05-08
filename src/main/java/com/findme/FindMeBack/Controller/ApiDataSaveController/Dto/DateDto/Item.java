package com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String atcId; //ok
    private String depPlace; //ok
    private String fdFilePathImg; //ok
    private String fdPrdtNm; //ok
    private String fdSbjt; //ok
    private Long fdSn; //ok
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;  //ok
    private String prdtClNm; //ok
    private Long rnum; //ok
    private String clrNm; //ok
}