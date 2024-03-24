package vn.elca.training.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vn.elca.training.model.ProjectStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vlp
 */
@Entity
public class Project extends PimBase {
    @JoinColumn(nullable = false, name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CompanyGroup companyGroup;

    @Column(nullable = false, unique = true, length = 4)
    private int projectNumber;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String customer;


    @Column(name = "status", nullable = false)
    private Enum<ProjectStatus> projectStatus;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", nullable = false)
    )
    private Set<Employee> members = new HashSet<>();


    public Project() {
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

    public Set<Employee> getMembers() {
        return members;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }

    public Enum<ProjectStatus> getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Enum<ProjectStatus> projectStatus) {
        this.projectStatus = projectStatus;
    }


    public void setMembers(List<Employee> members) {
        this.members = new HashSet<>(members);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

}
