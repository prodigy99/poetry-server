package com.example.poetryserver.config;

import com.alibaba.fastjson.JSONObject;
import com.example.poetryserver.controller.utils.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@Slf4j
public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message object) {

        String s = null;
        try {

            s = JSONObject.toJSONString(object);
            log.info("primal: " + s);

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return s;
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
