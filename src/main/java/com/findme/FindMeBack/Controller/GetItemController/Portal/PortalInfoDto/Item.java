package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalInfoDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// 분실물 항목에 대한 정보 저장 클래스
@Getter
@Setter
public class Item {
    private String atcId;            // 관리 ID
    private String csteSteNm;        // 보관 상태명
    private String depPlace;         // 보관장소
    private String fdFilePathImg;    // 사진 파일 경로
    private String fdHor;            // 습득 시간
    private String fdPlace;          // 습득 장소
    private String fdPrdtNm;         // 물품명
    private String fdSn;             // 습득 순번
    private Date fdYmd;              // 습득 일자
    private String fndKeepOrgnSeNm; // 습득물 보관 기관 구분명
    private String orgId;            // 기관 아이디
    private String orgNm;            // 기관명
    private String prdtClNm;         // 물품 분류명
    private String tel;              // 전화번호
    private String uniq;             // 특이사항
}
