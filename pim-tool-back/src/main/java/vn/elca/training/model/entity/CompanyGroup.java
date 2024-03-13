package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company_group")
public class CompanyGroup extends PimBase{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_leader_id", nullable = false)
    private Employee groupLeader;

    @OneToMany( mappedBy = "companyGroup", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();


    public CompanyGroup(){};



    public Employee getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(Employee groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Set<Project> getProjects() {
        return projects;
    }


}
