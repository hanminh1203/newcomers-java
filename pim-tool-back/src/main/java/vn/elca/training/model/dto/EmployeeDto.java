package vn.elca.training.model.dto;

public class EmployeeDto {

    private Long id;
    private String visa;
    private String employeeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String firstName, String lastName) {
        this.employeeName = String.format("%s %s", firstName, lastName);
    }
}
