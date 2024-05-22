package com.findme.FindMeBack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    @Column(nullable = false)
    private String ProductClassifyName; // 상품분류

    @Column(nullable = false)
    private String content; // 게시글 내용

    @Column(nullable = false)
    private String productName; // 상품명

    @Column(nullable = false)
    private String foundPlace; // 습득장소

    @Column(nullable = false)
    private String adress; // 습득주소지

//    @Column(nullable = false)
//    private LocalDateTime date; // 작성일자

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now(); // 작성일자, 현재 시각으로 초기화


    @Column(nullable = false)
    private int views; // 조회수

    @Column
    private String imgPath; // 이미지 경로

    @Enumerated(EnumType.STRING)
    @Column(name = "postType", nullable = false)
    private PostType postType; // 게시글 유형

    @Column(nullable = false)
    private boolean shown; //노출여부

    // 기본값 설정 메서드
    @PrePersist
    protected void onCreate() {
        if (this.shown == false) {
            this.shown = true;
        }
    }
}
