package vn.elca.training.model.exception;

public class GroupNotFoundException extends Exception{
    final Long id;

    public GroupNotFoundException(Long id) {
        super(String.format("Group %d not found", id));
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
