package com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalLocationDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// 분실물 항목에 대한 정보 저장 클래스
@Getter
@Setter
public class Item {
    private String fdSbjt; // 제목
    private String rnum;   // 순번
    private String atcId;  // 아이디
    private String addr;   // 기관도로명주소
    private String fdSn;   // 일련번호
    private String depPlace;  // 습득 장소
    private String prdtClNm;  // 재품 분류명
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fdYmd;       // 분실 일자
    private String fdPrdtNm;  // 제품 이름
}
