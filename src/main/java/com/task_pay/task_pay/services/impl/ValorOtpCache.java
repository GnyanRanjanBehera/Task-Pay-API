package com.task_pay.task_pay.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.task_pay.task_pay.services.ValorCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ValorOtpCache implements ValorCache {

    Logger logger = LoggerFactory.getLogger(ValorOtpCache.class);
    CacheLoader<String, String> loader;
    LoadingCache<String, String> cache;

    public ValorOtpCache() {
        loader =
                new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                };
        cache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build(loader);
    }

    public String storeInCache(String key, String value) {
        String checkValue = cache.getIfPresent(key);
        if (checkValue != null) {
            return checkValue;
        } else {
            cache.put(key, value);
            logger.info("Size in put Sms cache method " + cache.size());
            logger.info("key value in SmsCache = "+key+" value = "+value);
            return value;
        }
    }

    public String getCachedToken(String key) {
        logger.info("Size in get cache method " + cache.size());
        return cache.getIfPresent(key);
    }
}
