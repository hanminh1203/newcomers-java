package vn.elca.training.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.StatusNotAvailableException;
import vn.elca.training.model.exception.VisaNotExistException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.CompanyGroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vlp
 *
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    ApplicationMapper mapper;

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
        return projectRepository.findAll()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> search(String keyword, String status) {
        List<ProjectDto> result = new ArrayList<>();
        List<ProjectDto> allProjectsDto = this.findAll();
        keyword = StringUtils.trimToEmpty(keyword);
        status = StringUtils.trimToEmpty(status);
        if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(status)) {
            for (ProjectDto projectDto : allProjectsDto) {
                if ((keyword.equals(String.valueOf(projectDto.getProjectNumber()))
                        || keyword.equals(projectDto.getCustomer())
                        || keyword.equals(projectDto.getName()))
                        && status.equals(projectDto.getStatus())) {
                    result.add(projectDto);
                }
            }
        } else if (StringUtils.isNotBlank(keyword)) {
            for (ProjectDto projectDto : allProjectsDto) {
                if ((keyword.equals(String.valueOf(projectDto.getProjectNumber()))
                        || keyword.equals(projectDto.getCustomer())
                        || keyword.equals(projectDto.getName()))) {
                    result.add(projectDto);
                }
            }
        } else if (StringUtils.isNotBlank(status)) {
            for (ProjectDto projectDto : allProjectsDto) {
                if (status.equals(projectDto.getStatus())) {
                    result.add(projectDto);
                }
            }

        } else {
            result = allProjectsDto;
        }
        return result;
    }


    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public ProjectDto updateProject(long id, ProjectDto updateProject) throws ProjectNotFoundException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException {
        Project project = projectRepository.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        Project savedProject = projectRepository.save(mapper.projectDtoToProject(project, updateProject));
        return mapper.projectToProjectDto(savedProject);
    }

}
