package vn.elca.training.service;

import java.util.List;
import java.util.Optional;

import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.StatusNotAvailableException;
import vn.elca.training.model.exception.VisaNotExistException;

import javax.transaction.Transactional;

/**
 * @author vlp
 *
 */
public interface ProjectService {

    ProjectDto updateProject(long id, ProjectDto updateProject) throws ProjectNotFoundException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException;
    List<ProjectDto> findAll();
    List<ProjectDto> search(String keyword, String status);
    Project findById(long id) throws ProjectNotFoundException;

    long count();
}
