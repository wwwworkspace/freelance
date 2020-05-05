package com.freelance.www.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RestResultVO<T> {
    //状态码
    private int statusCode;
    //返回消息
    private String statusMessage;
    //接口返回数据，以实际接口为准")
    private T content;
    //数据条数，分页查询时生效
    private long count;

    private RestResultVO() {
    }

    private RestResultVO(T content, long count) {
        this.content = content;
        this.count = count;
    }

    private RestResultVO(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static <T> RestResultVO<T> ok(T content) {
        return new RestResultVO(content, 0L);
    }

    public static <T> RestResultVO<T> ok(T content, long count) {
        return new RestResultVO(content, count);
    }

    public static <T> RestResultVO<T> error(int code, String message) {
        return new RestResultVO(code, message);
    }

    public static <T> RestResultVO<T> error(int code) {
        return new RestResultVO(code, (String)null);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public T getContent() {
        return this.content;
    }

    public long getCount() {
        return this.count;
    }
}
