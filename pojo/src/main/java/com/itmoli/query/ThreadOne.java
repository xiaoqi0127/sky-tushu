package com.itmoli.query;

public class ThreadOne {

    private static ThreadLocal threadLocal = new ThreadLocal<>();

    public static ThreadLocal getThreadLocal() {
        return threadLocal;
    }
}
