package com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PolicePlaceDto;

import lombok.Getter;
import lombok.Setter;

// 분실물 정보 조회 시 필요한 파라미터들 저장 클래스
@Getter
@Setter
public class SearchItemsWithPlace {
    public String PRDT_NM;      // 물품명
    public String DEP_PLACE;    // 보관장소
}
