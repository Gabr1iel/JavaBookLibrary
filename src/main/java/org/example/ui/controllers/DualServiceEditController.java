package org.example.ui.controllers;

public interface DualServiceEditController<M, S, T> extends EditController<M, S> {
    void setSecondaryService(T secondaryService);
}
