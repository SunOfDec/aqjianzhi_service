package com.jz.aqjianzhi.ws_service.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MessageUtils {

    public static <T> T JSONStringToObject(String msg, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        return jsonObject.toJavaObject(clazz);
    }

    public static String ObjectToJSONString(Object msg) {
        return JSONObject.toJSONString(msg);
    }

    public static String ObjectToJSONStringWithDateFormat(Object msg) {
        return JSONObject.toJSONStringWithDateFormat(msg, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }
}
