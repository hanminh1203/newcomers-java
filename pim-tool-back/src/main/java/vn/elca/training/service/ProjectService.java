package vn.elca.training.service;

import java.util.List;
import java.util.Optional;

import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectNotFoundException;

import javax.transaction.Transactional;

/**
 * @author vlp
 *
 */
public interface ProjectService {

    Project updateProject(long id, Project updateProject);
    List<Project> findAll();

    List<Project> findByProjectName(String keyWord);

    Project findById(long id) throws ProjectNotFoundException;

    long count();
}
