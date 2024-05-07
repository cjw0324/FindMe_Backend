package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchItemsWithDate {
    public String PRDT_CL_CD_01;  // 대분류 코드
    public String PRDT_CL_CD_02;  // 중분류 코드
    public String START_YMD;      // 검색 시작일
    public String END_YMD;        // 검색 종료일
    public String N_FD_LCT_CD;    // 습득지역 코드
}
