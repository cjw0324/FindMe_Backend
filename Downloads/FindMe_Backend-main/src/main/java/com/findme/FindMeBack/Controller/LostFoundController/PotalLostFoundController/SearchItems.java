package com.findme.FindMeBack.Controller.LostFoundController.PotalLostFoundController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchItems {
    private String serviceKey;
    private int numOfRows;
    private int pageNo;
    private String disUseYn;
    private String resClCd;
    private String serialNumber;
    private String sFndYmd;
    private String eFndYmd;
}
