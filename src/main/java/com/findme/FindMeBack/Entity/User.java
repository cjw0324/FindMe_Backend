package com.findme.FindMeBack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    // 구글 로그인 추가 정보
    @Column
    private String googleId;

    @Column
    private String picture;

    // Optional password field for non-google users
    @Column(nullable = true)
    private String password;

    // If you want to use password, add getPassword method
    public String getPassword() {
        return password;
    }
}
