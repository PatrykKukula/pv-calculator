package com.github.PatrykKukula.Photovoltaic.materials.calculator.Caching;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.lang.Nullable;

@Slf4j
public class CaffeineCacheImpl extends CaffeineCache {

    public CaffeineCacheImpl(String name, Cache<Object, Object> cache) {
        super(name, cache);
    }
    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = super.get(key);
        log.info("[CACHE GET {} ] KEY = {} -> {}", getName(), key, wrapper != null ? "HIT" : "MISS");
        return wrapper;
    }
    @Override
    public void put(Object key, @Nullable Object value) {
        log.info("[CACHE PUT {}] key={}", getName(), key);
        super.put(key, value);
    }
    @Override
    public void evict(Object key) {
        log.info("[CACHE EVICT {}], key={}", getName(), key);
        super.evict(key);
    }
    @Override
    public boolean evictIfPresent(Object key) {
        log.info("[CACHE EVICT {}], key={}", getName(), key);
        return super.evictIfPresent(key);
    }
}
