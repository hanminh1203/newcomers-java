package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.repository.custom.UserRepositoryCustom;

/**
 * @author gtn
 *
 */
@Repository
public interface UserRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee>, UserRepositoryCustom {
}
