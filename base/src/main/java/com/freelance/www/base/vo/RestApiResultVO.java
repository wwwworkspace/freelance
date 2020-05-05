package com.freelance.www.base.vo;

import com.freelance.www.base.util.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.reflect.Reflection;

@Slf4j
public class RestApiResultVO<T> {
    private String statusCode;
    private String statusMessage;
    private T content;
    private int count;
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RestApiResultVO))
            return false;
        RestApiResultVO<?> other = (RestApiResultVO)o;
        if (!other.canEqual(this))
            return false;
        Object this$statusCode = getStatusCode(), other$statusCode = other.getStatusCode();
        if ((this$statusCode == null) ? (other$statusCode != null) : !this$statusCode.equals(other$statusCode))
            return false;
        Object this$statusMessage = getStatusMessage(), other$statusMessage = other.getStatusMessage();
        if ((this$statusMessage == null) ? (other$statusMessage != null) : !this$statusMessage.equals(other$statusMessage))
            return false;
        Object this$content = getContent(), other$content = other.getContent();
        return ((this$content == null) ? (other$content != null) : !this$content.equals(other$content)) ? false : (!(getCount() != other.getCount()));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RestApiResultVO;
    }

    public int hashCode() {
        int PRIME = 59,
        result = 1;
        Object $statusCode = getStatusCode();
        result = result * 59 + (($statusCode == null) ? 43 : $statusCode.hashCode());
        Object $statusMessage = getStatusMessage();
        result = result * 59 + (($statusMessage == null) ? 43 : $statusMessage.hashCode());
        Object $content = getContent();
        result = result * 59 + (($content == null) ? 43 : $content.hashCode());
        return result * 59 + getCount();
    }

    public String toString() {
        return "RestApiResultVO(statusCode=" + getStatusCode() + ", statusMessage=" + getStatusMessage() + ", content=" + getContent() + ", count=" + getCount() + ")";
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public T getContent() {
        return this.content;
    }

    public int getCount() {
        return this.count;
    }

    @Deprecated
    public RestApiResultVO() {
        setStatusCode("API-COMMON-ERR-ERROR");
    }

    @Deprecated
    public RestApiResultVO(String statusCode, String statusMessage, T content, int count) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.content = content;
        this.count = count;
        if (StringUtils.isEmpty(statusCode)) {
            setStatusCode("API-COMMON-ERR-ERROR");
        } else {
            setStatusCode(statusCode, 5);
        }
    }

    public static <T> RestApiResultVO<T> success(T content, int count) {
        return builder().success().content(content).count(count).build();
    }

    public static <T> RestApiResultVO<T> success(T content) {
        return success(content, 0);
    }

    public static <T> RestApiResultVO<T> success() {
        return success(null, 0);
    }

    public static <T> RestApiResultVO<T> error(StatusCodeEnum statusCode, T content) {
        return builder().statusCode(statusCode.getStatusCode()).content(content).build();
    }

    public static <T> RestApiResultVO<T> error(StatusCodeEnum statusCode) {
        return error(statusCode, null);
    }

    public static <T> RestApiResultVO<T> error(String statusCode) {
        return builder().statusCode(statusCode).build();
    }

    public static <T> RestApiResultVO<T> paramError() {
        RestApiResultVO<T> restApiResultVo = new RestApiResultVO();
        restApiResultVo.setErrorParam();
        return restApiResultVo;
    }

    public static <T> RestApiResultVO<T> dbExistError() {
        RestApiResultVO<T> restApiResultVo = new RestApiResultVO();
        restApiResultVo.setErrorDbExist();
        return restApiResultVo;
    }

    public static <T> RestApiResultVO<T> dbExceptionError() {
        RestApiResultVO<T> restApiResultVo = new RestApiResultVO();
        restApiResultVo.setErrorDbException();
        return restApiResultVo;
    }

    public boolean checkSuccess() {
        return "API-COMMON-INF-OK".equals(this.statusCode);
    }

    @Deprecated
    public static ResultBuilder builder() {
        return new ResultBuilder();
    }

    @Deprecated
    public void setStatusCode(String statusCode) {
        setStatusCode(statusCode, 3);
    }

    private void setStatusCode(String statusCode, int stackLevel) {
        if (StringUtils.isEmpty(statusCode))
            return;
        this.statusCode = statusCode;
//        try { TODO 注意需不需要开启
//            Object message = ConfigManager.getPropertyById(Reflection.getCallerClass(stackLevel), statusCode, new String[0]);
//            if (message == null)
//                message = ConfigManager.getPropertyById(statusCode);
//            if (message != null)
//                this.statusMessage = (String)message;
//        } catch (Exception e) {
//            log.error("RestApiResultVO setStatusCode find Message error! statusCode = " + statusCode, e);
//        }
    }

    @Deprecated
    public RestApiResultVO setSuccess() {
        setStatusCode("API-COMMON-INF-OK");
        return this;
    }

    @Deprecated
    public RestApiResultVO setErrorParam() {
        setStatusCode("API-COMMON-ERR-PARAM");
        return this;
    }

    @Deprecated
    public RestApiResultVO setErrorDbException() {
        setStatusCode("API-COMMON-ERR-DB-EXCEPTION");
        return this;
    }

    @Deprecated
    public void setErrorDbExist() {
        setStatusCode("API-COMMON-ERR-DB-EXIST");
    }

    private static class ResultBuilder<T> {
        private String statusCode;

        private String statusMessage;

        private T content;

        private int count;

        public ResultBuilder statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResultBuilder statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResultBuilder content(T content) {
            this.content = content;
            return this;
        }

        public ResultBuilder count(int count) {
            this.count = count;
            return this;
        }

        public ResultBuilder success() {
            this.statusCode = "API-COMMON-INF-OK";
            return this;
        }

        public ResultBuilder errorParam() {
            this.statusCode = "API-COMMON-ERR-PARAM";
            return this;
        }

        public RestApiResultVO build() {
            return new RestApiResultVO<>(this.statusCode, this.statusMessage, this.content, this.count);
        }
    }
}
