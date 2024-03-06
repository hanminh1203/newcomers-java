package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QCompanyGroup;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CompanyGroupRepositoryCustomImpl implements CompanyGroupRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    @Override
    public CompanyGroup findByLeaderName(String leaderName) {
        return new JPAQuery<CompanyGroup>(em)
                .from(QCompanyGroup.companyGroup)
                .where(QCompanyGroup.companyGroup.groupLeader.firstName.eq(leaderName))
                .fetchOne();
    }
}
