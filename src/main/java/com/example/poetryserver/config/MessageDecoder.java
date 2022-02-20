package com.example.poetryserver.config;

import com.alibaba.fastjson.JSONObject;
import com.example.poetryserver.controller.utils.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

@Slf4j
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) {
        Message message = null;
        try {
            message = JSONObject.parseObject(s, Message.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {

        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
