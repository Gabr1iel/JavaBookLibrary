package org.example.common.file;

import java.io.*;
import java.util.HashMap;

public class BinaryFileHandler implements FileHandler {
    @Override
    public <K, V> void save(String file, HashMap<K, V> content, Class<V> clazz) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (content == null || content.isEmpty()) {
            System.out.print("Žádné" + clazz.getSimpleName() + "nejsou k dispozici pro uložení!");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(content);
            System.out.println(clazz.getSimpleName() + " byly úspěšně uloženy!");

        } catch (IOException e) {
            System.out.println("Error during saving " + clazz.getSimpleName() + "!" + e.getMessage());
        }
    }

    @Override
    public <K, V> HashMap<K, V> loadContent(String file, Class<V> clazz) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            HashMap<K, V> content = (HashMap<K, V>) ois.readObject();

            for (V value : content.values()) {
                if (!clazz.isInstance(value))
                    throw new ClassCastException("Invalid type of class " + value.getClass());
            }
            System.out.println(clazz.getSimpleName() + " were loaded successfully!");
            return content;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during loading !" + clazz.getSimpleName() + e.getMessage());
            return new HashMap<>();
        }
    }
}
