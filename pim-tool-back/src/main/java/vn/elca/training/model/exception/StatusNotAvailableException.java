package vn.elca.training.model.exception;
import vn.elca.training.model.ProjectStatus;

public class StatusNotAvailableException extends Exception{
    final String status;

    public StatusNotAvailableException(String status) {
        super(String.format("Status %s not available", status));
        this.status = status;
    }
}
