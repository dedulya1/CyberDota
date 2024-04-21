package com.project.opendota.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CacheEntity<K, V> {
  private static final int MAX_SIZE = 100;
  Map<K, V> cacheMap = new ConcurrentHashMap<>();

  public void put(K key, V value) {
    cacheMap.put(key, value);
    if (cacheMap.size() >= MAX_SIZE) {
      cacheMap.clear();
    }
  }

  public V get(K key) {
    return cacheMap.get(key);
  }

  public void remove(K key) {
    cacheMap.remove(key);
  }

  public void clear() {
    cacheMap.clear();
  }

  public int getSize() {
    return cacheMap.size();
  }
}