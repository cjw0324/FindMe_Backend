package com.findme.FindMeBack.Controller.GetItemController.Portal.PortalLocationDto;

import lombok.Getter;
import lombok.Setter;

// 분실물 정보 조회 시 필요한 파라미터들 저장 클래스
@Getter
@Setter
public class SearchItemsWithLocation {
    public String PRDT_NM;      // 물품명
    public String ADDR;         // 기관 도로명 주소
}
