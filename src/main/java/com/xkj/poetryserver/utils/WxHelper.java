package com.xkj.poetryserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信工具
 */
@Component
public class WxHelper {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.grantType}")
    private String grantType;

    @Value("${wx.requestUrl}")
    private String requestUrl;

    @Value("${wxToken.url}")
    private String tokenUrl;

    @Value("${wxUnionId.url}")
    private String unionUrl;

    /*获取用户的openid*/
    public Map<String, Object> getOpenIdByCode(String code) {
        Map<String, Object> json = null;
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=" + grantType;
            // 发送请求
            String data = restTemplate.getForObject(url,String.class);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
//    /*获取用户的access_token*/
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> getUserAccessToken(String code) {
//        Map<String, Object> json = null;
//        try {
//            String url = tokenUrl + "?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
//            // 发送请求
//            System.out.println(url);
//            String data = HttpUtils.get(url);
//            ObjectMapper mapper = new ObjectMapper();
//            json = mapper.readValue(data, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    /*获取用户的unionId*/
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> getUnionId(String token, String openId) throws Exception {
//        Map<String, Object> json = null;
//        try {
//            String url = unionUrl + "?access_token=" + token + "&openid=" + openId;
//            // 发送请求
//            String data = HttpUtils.get(url);
//            ObjectMapper mapper = new ObjectMapper();
//            json = mapper.readValue(data, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }

}