package com.java.example.tanzu.wherefordinner.processor.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Profile("gemfire")
public class GemFireHashCache implements HashCache {
//  @Resource
//  protected final Map<String, String> cache;
  @Autowired
  protected GemfireTemplate gemFireTemplate;
//
//  @Bean
//  Properties gemfireProperties() {
//    Properties gemfireProperties = new Properties();
//    gemfireProperties.setProperty("name","SpringDataGemFireApplication");
//    gemfireProperties.setProperty("mcast-port", "0");
//    gemfireProperties.setProperty("log-level", "config");
//    return gemfireProperties;
//  }
//
//  @Bean
//  CacheFactoryBean gemfireCache() {
//    CacheFactoryBean gemFireCache = new CacheFactoryBean();
//    gemFireCache.setClose(true);
//    gemFireCache.setProperties(gemfireProperties());
//    return gemFireCache;
//  }
//
//  @Bean(name="cache")
//  Region<String, String> getCacheRegion(final GemFireCache cache) {
//    return cache. createClientRegion(RegionShortcuts.PROXY).create("cache");
//  }

  @Override
  public Mono<String> getHashForKey(String key)
  {
    String value = gemFireTemplate.get(key);
    if (value.isBlank() || value == null) {
      return Mono.just("");
    }
    return Mono.just(value);
  }

  @Override
  public Mono<String> setHashForKey(String key, String value, Duration expiration)
  {
    gemFireTemplate.put(key, value);
    return Mono.just(value);
  }

  @Override
  public Mono<Void> deleteHashForKey(String key)
  {
    gemFireTemplate.remove(key);
    return Mono.empty();
  }
}
