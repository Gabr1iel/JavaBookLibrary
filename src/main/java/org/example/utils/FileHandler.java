package org.example.utils;

import java.util.List;

public interface FileHandler {
    <T> void save(String file, List<T> content, Class<T> clazz);
    <T> List<T> loadContent(String file, Class<T> clazz);
}
