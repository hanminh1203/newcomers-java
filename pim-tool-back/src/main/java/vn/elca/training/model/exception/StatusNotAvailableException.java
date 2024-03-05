package vn.elca.training.model.exception;
import vn.elca.training.model.ProjectStatus;

public class StatusNotAvailableException extends Exception{
    final ProjectStatus status;

    public StatusNotAvailableException(ProjectStatus status) {
        super(String.format("Status "));
        this.status = status;
    }
}
