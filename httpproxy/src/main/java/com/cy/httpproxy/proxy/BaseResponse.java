package com.cy.httpproxy.proxy;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/30
 * @history : change on v1.0
 */
public class BaseResponse<T> {
    private String code;
    private String message;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
