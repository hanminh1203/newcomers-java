package vn.elca.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.CompanyGroup;

@Repository
public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long>, QuerydslPredicateExecutor<CompanyGroup> {}
