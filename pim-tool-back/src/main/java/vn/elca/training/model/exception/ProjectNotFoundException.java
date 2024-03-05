package vn.elca.training.model.exception;

public class ProjectNotFoundException extends Exception{

    final Long id;

    public ProjectNotFoundException(Long id) {
        super(String.format("Project %d not found", id));
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
