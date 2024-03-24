package vn.elca.training.service;

import java.util.List;
import java.util.Optional;

import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.*;

import javax.transaction.Transactional;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    void deleteAllByIds(List<Long> ids) throws DeleteNotNewProjectException;

    ProjectDto deleteById(Long id) throws DeleteNotNewProjectException, ProjectNotFoundException;

    ProjectDto createProject(ProjectDto inputProject) throws ProjectNumberAlreadyExistsException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException, EndDateB4StartDateException;

    ProjectDto updateProject(Long id, ProjectDto updateProject) throws StatusNotAvailableException, GroupNotFoundException, VisaNotExistException, ProjectNotFoundException, EndDateB4StartDateException;
    List<ProjectDto> findAll();
    List<ProjectDto> search(String keyword, String status);
    Project findById(long id) throws ProjectNotFoundException;

    long count();
}
