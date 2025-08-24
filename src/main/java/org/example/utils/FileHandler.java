package org.example.utils;

import java.util.HashMap;
import java.util.List;

public interface FileHandler {
    <K, V> void save(String file, HashMap<K, V> content, Class<V> clazz);
    <K, V> HashMap<K, V> loadContent(String file, Class<V> clazz);
}
