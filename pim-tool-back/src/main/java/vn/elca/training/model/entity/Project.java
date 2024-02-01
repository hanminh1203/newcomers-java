package vn.elca.training.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vlp
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Boolean activated;

    @OneToOne( mappedBy = "project",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User projectLeader;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.PERSIST)
    private Set<User> member = new HashSet<>();

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    private CompanyGroup companyGroup;

    @Column
    private LocalDate finishingDate;

    @Column
    private String customer;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    public Project() {
    }

    public Project(String name, LocalDate finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }
    public Project(String name, LocalDate finishingDate, Boolean activated) {
        this.name = name;
        this.finishingDate = finishingDate;
        this.activated = activated;
    }
    public Project(String name, LocalDate finishingDate, Boolean activated, User projectLeader){
        this.setProjectLeader(projectLeader);
        this.name = name;
        this.activated = activated;
        this.finishingDate = finishingDate;
    }


    public Project(Long id, String name, LocalDate finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public static Project copy(Project project) {
        Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setCustomer(project.getCustomer());
        newProject.setActivated(project.getActivated());
        newProject.setTasks(project.getTasks());
        newProject.setProjectLeader(project.getProjectLeader());
        newProject.setMember(project.getMember());
        newProject.setCompanyGroup(project.getCompanyGroup());
        newProject.setFinishingDate(project.getFinishingDate());
        return newProject;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public User getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(User projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Set<User> getMember() {
        return member;
    }

    public void setMember(Set<User> member) {
        this.member = member;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }
}
