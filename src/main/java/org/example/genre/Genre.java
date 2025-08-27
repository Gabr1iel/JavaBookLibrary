package org.example.genre;

import java.io.Serializable;
import java.util.UUID;

public class Genre implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String Id = UUID.randomUUID().toString();
    private String title;

    public Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return Id;
    }
}
