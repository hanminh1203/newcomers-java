package vn.elca.training.service;

import java.util.List;
import java.util.Optional;

import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
public interface ProjectService {

    public void updateProject(long id, Project updateProject);
    List<Project> findAll();

    List<Project> findByProjectName(String keyWord);

    Optional<Project> findById(long id);

    long count();
}
