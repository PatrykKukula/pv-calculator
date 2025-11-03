package com.github.PatrykKukula.Photovoltaic.materials.calculator.Caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.CacheConstants.*;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(){
          @Override
          protected Cache createCaffeineCache(String name){
              Caffeine<Object, Object> caffeine = cacheBuilder(name);
              return new CaffeineCacheImpl(name, caffeine.build());
          }
        };
        return cacheManager;
    }
    private Caffeine<Object, Object> cacheBuilder(String name){
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        switch (name){
            case PROJECT_CACHE, INSTALLATION_CACHE -> {
                caffeine.recordStats();
                caffeine.maximumSize(100);
                caffeine.initialCapacity(50);
                caffeine.expireAfterAccess(Duration.ofMinutes(30));
            }
            case INSTALLATION_COUNT, PROJECT_POWER-> {
                caffeine.recordStats();
                caffeine.maximumSize(200);
                caffeine.initialCapacity(50);
                caffeine.expireAfterWrite(Duration.ofMinutes(60));
            }
            case USER, USER_VAADIN -> {
                caffeine.recordStats();
                caffeine.maximumSize(50);
                caffeine.initialCapacity(10);
                caffeine.expireAfterWrite(Duration.ofMinutes(120));
            }
            case CONSTRUCTION_MATERIAL, ELECTRICAL_MATERIAL-> {
                caffeine.recordStats();
                caffeine.maximumSize(30);
                caffeine.initialCapacity(30);
                caffeine.expireAfterAccess(Duration.ofHours(24));
            }
            case CONSTRUCTION_MATERIALS, ELECTRICAL_MATERIALS -> {
                caffeine.recordStats();
                caffeine.maximumSize(100);
                caffeine.initialCapacity(20);
                caffeine.expireAfterAccess(Duration.ofMinutes(30));
            }
            default -> {
                caffeine.recordStats();
                caffeine.maximumSize(100);
                caffeine.expireAfterAccess(Duration.ofMinutes(30));
            }
        }
        return caffeine;
    }
}
