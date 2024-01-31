package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CompanyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "companyGroup", cascade = CascadeType.PERSIST)
    private User groupLeader;

    @OneToMany( mappedBy = "companyGroup", cascade = CascadeType.PERSIST)
    private Set<Project> projects = new HashSet<>();

    public CompanyGroup(String name) {
        this.name = name;
    }
    public CompanyGroup(){};
    public CompanyGroup(String name, User groupLeader){
        this.name = name;
        this.groupLeader = groupLeader;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(User groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    public void addProject(Project project){
        this.projects.add(project);
    }
    public void removeProject(Project project){
        this.projects.remove(project);
    }
}
