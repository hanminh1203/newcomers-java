package vn.elca.training.repository.custom;

import org.springframework.data.jpa.repository.EntityGraph;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {
    Project findProjectById(long id);

    long countByNumber(int projectNumber);

    List<Project> findAllByIds(List<Long> ids);
}
