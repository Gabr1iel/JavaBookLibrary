package org.example.utils;

public interface EditController<S, M> {
    void setService(S modelService);
    void setModel(M model);
    void Edit();
}
