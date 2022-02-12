package com.example.poetryserver.controller;

import com.example.poetryserver.domain.User;
import com.example.poetryserver.service.UserService;
import com.example.poetryserver.util.WxHelper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    final WxHelper wxHelper;
    final UserService userService;
    public UserController(WxHelper wxHelper, UserService userService) {
        this.wxHelper = wxHelper;
        this.userService = userService;
    }

    @GetMapping("getWxOpenId/{uid}")
    private Map<String, Object> getWxOpenId(@PathVariable("uid") String uid){
        return wxHelper.getOpenIdByCode(uid);
    }

    @GetMapping("getUserInfo/{uid}")
    private User getUserInfo(@PathVariable String uid){
        return userService.findUserById(uid);
    }

    @GetMapping("register/{uid}/{nickName}/{avatar}")
    private User register(@PathVariable String uid,@PathVariable("nickName") String nickname,@PathVariable("avatar") String avatar){
        // TODO: 2022/2/12 进行注册验证 
        if(userService.findUserById(uid) != null) return userService.findUserById(uid);
        return userService.register(uid,nickname,avatar);
    }

    @GetMapping("addUserExp/{uid}/{num}")
    private void addUserExp(@PathVariable("uid") String uid,@PathVariable("num") int num){
       User user = userService.findUserById(uid);
       user.setExp(user.getExp() + num);
       userService.updateUser(user);
    }
}
