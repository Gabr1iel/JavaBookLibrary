package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileHandler implements FileHandler {
    @Override
    public <T> void save(String file, List<T> content, Class<T> clazz) {
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
    public <T> List<T> loadContent(String file, Class<T> clazz) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<?> content = (List<?>) ois.readObject();

            for (Object obj : content) {
                if (!clazz.isInstance(obj))
                    throw new ClassCastException("Invalid type of class " + obj.getClass());
            }
            System.out.println(clazz.getSimpleName() + " were loaded successfully!");
            return (List<T>) content;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during loading !" + clazz.getSimpleName() + e.getMessage());
            return new ArrayList<>();
        }
    }
}
