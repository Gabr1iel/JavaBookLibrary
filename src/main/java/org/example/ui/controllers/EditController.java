package org.example.ui.controllers;

public interface EditController<M, S> {
    void setModel(M model);
    void setService(S modelService);
    void Edit();
}
