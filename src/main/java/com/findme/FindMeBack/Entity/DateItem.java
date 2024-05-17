package com.findme.FindMeBack.Entity;

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
@Entity(name = "DateItem")
public class DateItem {

    @Id
    @Column(updatable = false)
    public String atcId; //아이디 ex) F2024050300003462


    @Column
    public String fdSbjt; // 제목 ex) "지갑(기타색)을 습득하여 보관하고 있습니다."

    @Column
    public Long rnum;   // 순번 ex) 1
    @Column
    public String fdFilePathImg;  // 이미지 파일 경로 ex)"https://www.lost112.go.kr/lostnfs/images/uploadImg/20240503/r_20240503085840234.jpg"
    @Column
    public Long fdSn;   // 일련번호 ex) 1
    @Column
    public String depPlace;  // 습득 장소 ex) "서울강서경찰서"
    @Column
    public String prdtClNm;  // 재품 분류명 ex) "지갑 > 남성용 지갑"
    @Column
    public Date fdYmd;       // 분실 일자 ex) "2024-04-11"
    @Column
    public String fdPrdtNm;  // 제품 이름 ex) "지갑"

    @Column
    public String clrNm;

    @Column(name = "n_fd_lct_cd")
    public String NFdLctCd;

    @ManyToOne
    @JoinColumn(name = "infoItemAtcId", referencedColumnName = "atcId")
    private InfoItem infoItem;


}
