package com.itmoli.query;

public class Thread {

    private static ThreadLocal threadLocal = new ThreadLocal<>();

    public static ThreadLocal getThreadLocal() {
        return threadLocal;
    }
}
