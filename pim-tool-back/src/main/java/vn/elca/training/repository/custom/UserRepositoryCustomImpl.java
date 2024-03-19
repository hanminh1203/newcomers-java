package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QCompanyGroup;
import vn.elca.training.model.entity.QEmployee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Employee> findMembersByVisa(Set<String> visas) {
        return new JPAQuery<Employee>(em)
                .from(QEmployee.employee)
                .where(QEmployee.employee.visa.in(visas))
                .fetch();
    }

    @Override
    public List<Employee> findByIds(List<Long> ids){
        return new JPAQuery<Employee>(em)
                .from(QEmployee.employee)
                .where(QEmployee.employee.id.in(ids))
                .fetch();
    }
}
