package com.cheng.httpproxy;

import android.util.Log;

/**
 * 自定义日志工具
 *
 * @author : chengyue
 * @date : 2019-12-17 14:15
 * @since : v
 */
public final class LogUtil {

    private static String TAG = "[HttpProxy]";
    public static final int ERROR = 5;
    public static int level = ERROR;

    private static String className;
    private static String methodName;
    private static int lineNumber;

    private static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    // 拼接 方法名+行号+log
    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
//        buffer.append(":");
//        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames() {
        //判断是否含有 lambda 表达式
        boolean next = false;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        for (StackTraceElement value : traceElements) {
            if (value.getMethodName().startsWith("lambda")) {
                next = true;
            }
        }
        String className = traceElements[2].getClassName();
        if (className.isEmpty()) {
            return;
        } else if (className.contains("$")) { //用于内部类的名字解析
            className = className.substring(className.lastIndexOf(".") + 1, className.indexOf("$"));
        } else {
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }

        if (!next) {
            methodName = traceElements[2].getMethodName();
        } else {
            methodName = traceElements[4].getMethodName();
        }
        lineNumber = traceElements[2].getLineNumber();
        //生成指向java的字符串 加入到TAG标签里面
        TAG = "(" + className + ".java:" + lineNumber + ")";
    }

    public static void e(String message) {
        if (!isDebuggable()) {
            return;
        }
        // Throwable instance must be created before any methods
        getMethodNames();
        Log.e(TAG, createLog(message));
    }

    public static void e(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }
        // Throwable instance must be created before any methods
        getMethodNames();
        Log.e(TAG, createLog(tag + message));
    }

    public static void i(String message) {
        if (!isDebuggable()) {
            return;
        }
        getMethodNames();
        Log.i(TAG, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable()) {
            return;
        }
        getMethodNames();
        Log.d(TAG, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable()) {
            return;
        }
        getMethodNames();
        Log.v(TAG, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable()) {
            return;
        }
        getMethodNames();
        Log.w(TAG, createLog(message));
    }
}