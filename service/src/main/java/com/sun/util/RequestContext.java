package com.sun.util;

import java.util.Map;

public class RequestContext {
    private static final ThreadLocal<Map<String, String>> headers = new ThreadLocal<>();

    public static void setHeaders(Map<String, String> headersMap) {
        headers.set(headersMap);
    }

    public static Map<String, String> getHeaders() {
        return headers.get();
    }

    public static void clear() {
        headers.remove();
    }
}

