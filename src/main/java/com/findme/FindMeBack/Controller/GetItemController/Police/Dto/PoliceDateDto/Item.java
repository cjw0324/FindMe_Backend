package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String fdSbjt; // 제목
    private String rnum;   // 순번
    private String atcId;  // 아이디
    private String fdFilePathImg;  // 이미지 파일 경로
    private String fdSn;   // 일련번호
    private String depPlace;  // 습득 장소
    private String prdtClNm;  // 재품 분류명
    private Date fdYmd;       // 분실 일자
    private String fdPrdtNm;  // 제품 이름
}
