package vn.elca.training.repository.custom;


import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAll() {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .orderBy(QProject.project.projectNumber.asc())
                .fetch();
    }
    @Override
    public List<Project> findProjectByKeyword(String keyword) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(Integer.valueOf(keyword))
                        .or(QProject.project.customer.eq(keyword))
                        .or(QProject.project.name.eq(keyword)))
                .fetch();
    }

    @Override
    public List<Project> findProjectByStatus(Enum<ProjectStatus> status) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectStatus.eq(status))
                .fetch();
    }

    @Override
    public List<Project> findProjectByKeywordAndStatus(String keyword, Enum<ProjectStatus> status) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where((QProject.project.projectNumber.eq(Integer.valueOf(keyword))
                        .or(QProject.project.customer.eq(keyword))
                        .or(QProject.project.name.eq(keyword)))
                        .and(QProject.project.projectStatus.eq(status)))
                .fetch();
    }


    @Override
    public long countByNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchCount();
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
            new JPADeleteClause(em, QProject.project)
                .where(QProject.project.id.in(ids))
                .execute();
    }
}
