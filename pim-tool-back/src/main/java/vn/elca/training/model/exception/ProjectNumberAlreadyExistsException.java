package vn.elca.training.model.exception;

public class ProjectNumberAlreadyExistsException extends Exception{
    final int projectNumber;

    public ProjectNumberAlreadyExistsException(int projectNumber) {
        super(String.format("Project number %d is already exist", projectNumber));
        this.projectNumber = projectNumber;
    }

    public int getProjectNumber() {
        return projectNumber;
    }
}
