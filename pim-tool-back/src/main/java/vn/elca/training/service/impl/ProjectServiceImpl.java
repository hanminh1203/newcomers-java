package vn.elca.training.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.*;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.CompanyGroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vlp
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    ApplicationMapper mapper;

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Project findById(long id) throws ProjectNotFoundException {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            return optionalProject.get();
        } else {
            throw new ProjectNotFoundException(id);
        }
    }

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectRepository.findAllByOrderByProjectNumberAsc();
        return projects.stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> search(String keyword, String status) {
        // get all projects and transfer into project DTO.
        List<ProjectDto> result = new ArrayList<>();
        List<ProjectDto> allProjectsDto = this.findAll();
        keyword = StringUtils.trimToEmpty(keyword);
        status = StringUtils.trimToEmpty(status);

        if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(status)) {
            // if both arguments are present, find projects match both
            for (ProjectDto projectDto : allProjectsDto) {
                if ((keyword.equals(String.valueOf(projectDto.getProjectNumber()))
                        || keyword.equals(projectDto.getCustomer())
                        || keyword.equals(projectDto.getName()))
                        && status.equals(projectDto.getStatus())) {
                    result.add(projectDto);
                }
            }
        } else if (StringUtils.isNotBlank(keyword)) {
            // if only keywork presents, find by keyword
            for (ProjectDto projectDto : allProjectsDto) {
                if ((keyword.equals(String.valueOf(projectDto.getProjectNumber()))
                        || keyword.equals(projectDto.getCustomer())
                        || keyword.equals(projectDto.getName()))) {
                    result.add(projectDto);
                }
            }
        } else if (StringUtils.isNotBlank(status)) {
            // if only status present, find by status
            for (ProjectDto projectDto : allProjectsDto) {
                if (status.equals(projectDto.getStatus())) {
                    result.add(projectDto);
                }
            }

        } else {
            // if both are empty, return all projects
            result = allProjectsDto;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {OptimisticLockException.class, StatusNotAvailableException.class,
            GroupNotFoundException.class, VisaNotExistException.class, ProjectNotFoundException.class})
    public ProjectDto updateProject(Long id, ProjectDto updateProject) throws StatusNotAvailableException, GroupNotFoundException, VisaNotExistException, ProjectNotFoundException, EndDateB4StartDateException {
        if (null != updateProject.getEndDate() && updateProject.getEndDate().isBefore(updateProject.getStartDate())) {
            // if end date is present and it comes before start date, throw exception and don't process updation
            throw new EndDateB4StartDateException(updateProject.getStartDate(), updateProject.getEndDate());
        } else {
            Project project = findById(id);
            Project savedProject = projectRepository.save(mapper.projectDtoToProject(project, updateProject));
            return mapper.projectToProjectDto(savedProject);
        }
    }

    @Override
    @Transactional(rollbackFor = {OptimisticLockException.class, ProjectNumberAlreadyExistsException.class, StatusNotAvailableException.class, GroupNotFoundException.class, VisaNotExistException.class})
    public ProjectDto createProject(ProjectDto inputProject) throws ProjectNumberAlreadyExistsException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException, EndDateB4StartDateException {
        int projectNumber = inputProject.getProjectNumber();
        if (null != inputProject.getEndDate() && inputProject.getEndDate().isBefore(inputProject.getStartDate())) {
            throw new EndDateB4StartDateException(inputProject.getStartDate(), inputProject.getEndDate());
            // if end date is present and it comes before start date, throw exception and don't process creation
        } else if (projectRepository.countByNumber(projectNumber) > 0) {
            // check if there's any project holding the same project number
            throw new ProjectNumberAlreadyExistsException(projectNumber);
        } else {
            Project saveProject = new Project();
            saveProject = projectRepository.save(mapper.projectDtoToProject(saveProject, inputProject));
            return mapper.projectToProjectDto(saveProject);
        }
    }

    @Override
    @Transactional(rollbackFor = {OptimisticLockException.class, DeleteNotNewProjectException.class, ProjectNotFoundException.class})
    public ProjectDto deleteById(Long id) throws DeleteNotNewProjectException, ProjectNotFoundException {
        Project project = this.findById(id); // check if this project presents in database, if not, it will throw not found exception
        if (ProjectStatus.NEW != project.getProjectStatus()) {
            // if found project status not new, throw exception and don't process deletion
            throw new DeleteNotNewProjectException();
        } else {
            projectRepository.delete(project);
            return mapper.projectToProjectDto(project);
        }
    }

    @Override
    @Transactional(rollbackFor = {OptimisticLockException.class, DeleteNotNewProjectException.class})
    public void deleteAllByIds(List<Long> ids) throws DeleteNotNewProjectException {
        List<Project> projectByIds = projectRepository.findAllByIds(ids);
        boolean hasNotNewProject = false;
        for (Project project : projectByIds) {
            if (ProjectStatus.NEW != project.getProjectStatus()) {
                // check if any project is not new
                hasNotNewProject = true;
                break;
            }
        }
        if(hasNotNewProject){
            // if there's not new project, throw exception and don't process deletion
            throw new DeleteNotNewProjectException();
        }
        else {
            projectRepository.deleteAll(projectByIds);
        }
    }
}
