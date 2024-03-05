package vn.elca.training.model.dto;
import vn.elca.training.model.ProjectStatus;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private int projectNumber;
    private String name;
    private String customer;
    private String groupName;
    private Set<String> membersVisa;
    private Enum<ProjectStatus> status;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<String> getMembersVisa() {
        return membersVisa;
    }

    public void setMemberVisa(Set<String> membersVisa) {
        this.membersVisa = membersVisa;
    }

    public Enum<ProjectStatus> getStatus() {
        return status;
    }

    public void setStatus(Enum<ProjectStatus> status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
