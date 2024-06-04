//package com.findme.FindMeBack.Controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/loginSuccess")
//    public String loginSuccess() {
//        return "redirect:http://localhost:3000/loginSuccess";  // 로그인 성공 후 리다이렉트할 URL
//    }
//
//    @GetMapping("/loginFailure")
//    public String loginFailure() {
//        return "redirect:http://localhost:3000/loginFailure";
//    }
//}