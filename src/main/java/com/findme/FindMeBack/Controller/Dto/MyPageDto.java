package com.findme.FindMeBack.Controller.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MyPageDto {
    public String username;
    public String email;

    public String picture;
}
