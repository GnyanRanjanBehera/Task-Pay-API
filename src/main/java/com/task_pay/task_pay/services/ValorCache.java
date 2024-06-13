package com.task_pay.task_pay.services;

public interface ValorCache {
    default String storeInCache(String key, String value) {
        return null;
    }

    default String getCachedToken(String key) {
        return null;
    }

    default String updateCachedToken(String key , String value){return null;}
}
