package com.cheng.httpproxy;

import android.text.TextUtils;
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
        return FlavorUtils.isDebug();
    }

    // 拼接 方法名+行号+log
    private static String createLog(String log) {
        return createLog("handsetETC", log);
    }

    // 拼接 方法名+行号+log
    private static String createLog(String tag, String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TextUtils.isEmpty(tag) ? "(handsetETC)" : "(" + tag + ")");
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
        String className = traceElements[3].getClassName();
        if (className.isEmpty()) {
            return;
        } else if (className.contains("$")) { //用于内部类的名字解析
            className = className.substring(className.lastIndexOf(".") + 1, className.indexOf("$"));
        } else {
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }

        if (!next) {
            methodName = traceElements[3].getMethodName();
        } else {
            methodName = traceElements[5].getMethodName();
        }
        lineNumber = traceElements[3].getLineNumber();
        //生成指向java的字符串 加入到TAG标签里面
        TAG = "(" + className + ".java:" + lineNumber + ")";
//        TAG = "handsetETC";
    }

    public static void e(Exception e) {
        logger(Log.getStackTraceString(e));
        if (!isDebuggable()) {
            return;
        }
        Log.e(TAG, createLog("", Log.getStackTraceString(e)));
    }

    public static void e(String tag, Exception e) {

        logger(Log.getStackTraceString(e));
        if (!isDebuggable()) {
            return;
        }
        Log.e(TAG, createLog("", Log.getStackTraceString(e)));
    }

    public static void e(String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.e(TAG, createLog("", message));
    }

    public static void e(String tag, String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.e(TAG, createLog(tag, message));
    }

    public static void i(String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.i(TAG, createLog("", message));
    }

    public static void i(String tag, String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.i(TAG, createLog(tag, message));
    }

    public static void d(String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.d(TAG, createLog("", message));
    }

    public static void d(String tag, String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.d(TAG, createLog(tag, message));
    }

    public static void v(String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.v(TAG, createLog("", message));
    }

    public static void v(String tag, String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.v(TAG, createLog(tag, message));
    }

    public static void w(String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.w(TAG, createLog("", message));
    }

    public static void w(String tag, String message) {
        logger(message);
        if (!isDebuggable()) {
            return;
        }
        Log.w(TAG, createLog(tag, message));
    }

    private static void logger(String message) {
        getMethodNames();
//        logger.info(TAG + " : " + message);
    }
}