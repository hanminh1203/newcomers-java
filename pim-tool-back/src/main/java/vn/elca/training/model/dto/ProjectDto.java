package vn.elca.training.model.dto;
import vn.elca.training.model.ProjectStatus;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private long id;
    private int projectNumber;
    private String name;
    private String customer;
    private Long groupId;
    private String membersVisa;
    private String status;

    private LocalDate startDate;
    private LocalDate endDate;

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getMembersVisa() {
        return membersVisa;
    }

    public void setMemberVisa(String membersVisa) {
        this.membersVisa = membersVisa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
