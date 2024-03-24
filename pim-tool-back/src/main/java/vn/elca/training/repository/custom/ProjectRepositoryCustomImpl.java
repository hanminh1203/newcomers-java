package vn.elca.training.repository.custom;


import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;



    @Override
    public Project findProjectById(long id) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.id.eq(id))
                .fetchOne();
    }

    @Override
    public long countByNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchCount();
    }

    @Override
    public List<Project> findAllByIds(List<Long> ids) {
            return new JPAQuery<Project>(em)
                    .from(QProject.project)
                    .where(QProject.project.id.in(ids))
                    .fetch();
    }
}
