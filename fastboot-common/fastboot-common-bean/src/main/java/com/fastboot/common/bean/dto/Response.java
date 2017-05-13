package com.fastboot.common.bean.dto;


import com.alibaba.fastjson.JSON;
import com.fastboot.common.util.TimeUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 响应消息。controller中处理后，返回此对象，响应请求结果给客户端。
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 8992436576262574064L;
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 反馈数据
     */
    private Object data;

    /**
     * 反馈信息
     */
    private String message;

    /**
     * 响应码
     */
    private int code;



    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", this.success);
        if (data != null)
            map.put("data", this.getData());
        if (message != null)
            map.put("message", this.getMessage());
        map.put("code", this.getCode());
        return map;
    }

    protected Response(String message) {
        this.code = 500;
        this.message = message;
        this.success = false;
    }

    protected Response(boolean success, Object data) {
        this.code = success ? 200 : 500;
        this.data = data;
        this.success = success;
    }

    protected Response(boolean success, Object data, int code) {
        this(success, data);
        this.code = code;
    }

    protected Set<String> getStringListFormMap(Map<Class<?>, Set<String>> map, Class type) {
        Set<String> list = map.get(type);
        if (list == null) {
            list = new HashSet<>();
            map.put(type, list);
        }
        return list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, TimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public static Response fromJson(String json) {
        return JSON.parseObject(json, Response.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response ok() {
        return ok(null);
    }

    public static Response ok(Object data) {
        return new Response(true, data);
    }

    public static Response created(Object data) {
        return new Response(true, data, 201);
    }

    public static Response error(String message) {
        return new Response(message);
    }

    public static Response error(String message, int code) {
        return new Response(message).setCode(code);
    }

}