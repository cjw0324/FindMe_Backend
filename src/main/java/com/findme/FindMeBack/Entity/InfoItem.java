package com.findme.FindMeBack.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "InfoItem")
public class InfoItem {

    @Id
    @Column(updatable = false)
    private String atcId;
    @Column
    private String csteSteNm;
    @Column
    private String depPlace; //
    @Column
    private String fdFilePathImg;
    @Column
    private String fdHor;
    @Column
    private String fdPlace;
    @Column
    private String fdPrdtNm; //
    @Column
    private Long fdSn;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;
    @Column
    private String fndKeepOrgnSeNm;
    @Column
    private String orgId;
    @Column
    private String orgNm;
    @Column
    private String prdtClNm;
    @Column
    private String tel;
    @Column
    private String uniq;
}

