package vn.elca.training.model.exception;

import java.util.Set;

public class VisaNotExistException extends Exception{
    final Set<String> visas;

    public VisaNotExistException(Set<String> visas) {
        this.visas = visas;
    }
}
