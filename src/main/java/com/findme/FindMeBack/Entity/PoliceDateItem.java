package com.findme.FindMeBack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "PoliceDateItem")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PoliceDateItem {

    @Id
    @Column(updatable = false)
    private String atcId; //아이디 ex) F2024050300003462


    @Column
    private String fdSbjt; // 제목 ex) "지갑(기타색)을 습득하여 보관하고 있습니다."

    @Column
    private String rnum;   // 순번 ex) 1
    @Column
    private String fdFilePathImg;  // 이미지 파일 경로 ex)"https://www.lost112.go.kr/lostnfs/images/uploadImg/20240503/r_20240503085840234.jpg"
    @Column
    private String fdSn;   // 일련번호 ex) 1
    @Column
    private String depPlace;  // 습득 장소 ex) "서울강서경찰서"
    @Column
    private String prdtClNm;  // 재품 분류명 ex) "지갑 > 남성용 지갑"
    @Column
    private Date fdYmd;       // 분실 일자 ex) "2024-04-11"
    @Column
    private String fdPrdtNm;  // 제품 이름 ex) "지갑"


}
