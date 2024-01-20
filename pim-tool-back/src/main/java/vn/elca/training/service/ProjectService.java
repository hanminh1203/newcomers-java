package vn.elca.training.service;

import java.util.List;

import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
public interface ProjectService {

    public Project updateProject(long id, Project updateProject);
    List<Project> findAll();

    List<Project> findByProjectName(String keyWord);

    Project findById(long id);

    long count();
}
