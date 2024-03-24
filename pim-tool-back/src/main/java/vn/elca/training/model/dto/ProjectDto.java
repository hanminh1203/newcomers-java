package vn.elca.training.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.StatusNotAvailableException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<EmployeeDto> members;
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

    public Set<EmployeeDto> getMembers() {
        return members;
    }

    public void setMembers(Set<EmployeeDto> members) {
        this.members = members;
    }
    @JsonIgnore
    public List<Long> getMemberIds(){
        List<Long> memberIds = new ArrayList<>();
        for (EmployeeDto employeeDto: this.members){
            memberIds.add(employeeDto.getId());
        }
        return memberIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusFromInner(Enum<ProjectStatus> status) {
        if (status.equals(ProjectStatus.PLA)) {
            this.status = "Planned";
        } else if (status.equals(ProjectStatus.NEW)) {
            this.status = "New";
        } else if (status.equals(ProjectStatus.FIN)) {
            this.status = "Finished";
        } else if (status.equals(ProjectStatus.INP)) {
            this.status = "In progess";
        }
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
