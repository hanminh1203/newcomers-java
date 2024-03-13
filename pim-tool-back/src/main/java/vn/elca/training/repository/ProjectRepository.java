package vn.elca.training.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.custom.ProjectRepositoryCustom;

import java.util.List;
import java.util.Optional;

/**
 * @author vlp
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project>, ProjectRepositoryCustom {
    @EntityGraph(attributePaths = {"members", "members.companyGroup"})
    List<Project> findAllByOrderByProjectNumberAsc();
    @EntityGraph(attributePaths = {"members", "members.companyGroup"})
    Optional<Project> findById(Long id);
}
