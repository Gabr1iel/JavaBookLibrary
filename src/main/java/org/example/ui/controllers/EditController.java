package org.example.ui.controllers;

public interface EditController<S, M> {
    void setService(S modelService);
    void setModel(M model);
    void Edit();
}
