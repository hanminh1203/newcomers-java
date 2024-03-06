package vn.elca.training.repository.custom;


import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Project> findProjectByName(String keyWord) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.contains(keyWord))
                .fetch();
    }
    @Override
    public Project findOneProjectByName(String keyWord) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.contains(keyWord))
                .fetchFirst();
    }

    @Override
    public long countByNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchCount();
    }
}
