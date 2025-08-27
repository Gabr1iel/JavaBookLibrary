package org.example.common.file;

import java.util.HashMap;

public interface FileHandler {
    <K, V> void save(String file, HashMap<K, V> content, Class<V> clazz);
    <K, V> HashMap<K, V> loadContent(String file, Class<V> clazz);
}
