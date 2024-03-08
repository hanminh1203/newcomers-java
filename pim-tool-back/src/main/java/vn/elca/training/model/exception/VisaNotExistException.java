package vn.elca.training.model.exception;

import java.util.List;
import java.util.Set;

public class VisaNotExistException extends Exception{
    final String visas;

    public VisaNotExistException(List<String> visas) {
        super(String.format("visa not available: %s",visas));
        this.visas = String.valueOf(visas);
    }

    public String getVisas() {
        return visas;
    }
}
