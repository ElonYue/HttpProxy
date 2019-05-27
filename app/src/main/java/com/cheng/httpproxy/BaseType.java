package com.cheng.httpproxy;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/30
 * @history : change on v1.0
 */
public class BaseType<T> {

    String code;
    String message;
    T result;

    public BaseType() {
//        BaseType baseType = new Gson().fromJson(respone, BaseType.class);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
