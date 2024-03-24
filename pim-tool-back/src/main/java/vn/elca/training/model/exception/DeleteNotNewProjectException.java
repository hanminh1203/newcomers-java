package vn.elca.training.model.exception;

public class DeleteNotNewProjectException extends Exception{
    public DeleteNotNewProjectException(){
        super(String.format("Can not delete project(s) because status is not new"));
    }
}
