package com.example.poetryserver.controller;

import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.domain.User;
import com.example.poetryserver.service.UserService;
import com.example.poetryserver.utils.WxHelper;
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
    private R getWxOpenId(@PathVariable("uid") String uid){
        return new R(wxHelper.getOpenIdByCode(uid));
    }

    @GetMapping("getUserInfo/{uid}")
    private R getUserInfo(@PathVariable String uid){

        return new R(userService.findUserById(uid));
    }

    @PostMapping("register")
    private R register(@RequestBody Map<String,Object> userInfo){
        String uid = (String) userInfo.get("uid");
        String nickname = (String) userInfo.get("nickname");
        String avatar = (String) userInfo.get("avatar");
        // TODO: 2022/2/12 进行注册验证
        if(userService.findUserById(uid) != null) return new R(R.STATUS.error,"注册失败,用户已存在");
        return new R(userService.register(uid,nickname,avatar));
    }

    @GetMapping("addUserExp/{uid}/{num}")
    private void addUserExp(@PathVariable("uid") String uid,@PathVariable("num") int num){
       User user = userService.findUserById(uid);
       user.setExp(user.getExp() + num);
       userService.updateUser(user);
    }
}
