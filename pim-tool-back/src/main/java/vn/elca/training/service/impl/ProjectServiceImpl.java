package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.StatusNotAvailableException;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.CompanyGroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author vlp
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyGroupService companyGroupService;

    @Autowired
    ApplicationMapper mapper;

    @Override
    public Project findById(long id) throws ProjectNotFoundException {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        else {
            throw new ProjectNotFoundException(id);
        }
    }
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByProjectName(String keyWord) {
        return projectRepository.findProjectByName(keyWord);
    }


    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public ProjectDto updateProject(long id, ProjectDto updateProject) throws ProjectNotFoundException, StatusNotAvailableException, GroupNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        project.setProjectNumber(updateProject.getProjectNumber());
        project.setName(updateProject.getName());
        project.setCustomer(updateProject.getCustomer());
        project.setCompanyGroup(companyGroupService.findById(updateProject.getGroupId()));
        project.setMembers(userRepository.findMembersByVisa(mapper.stringVisasToSetVisas(updateProject.getMembersVisa())));
        project.setProjectStatus(mapper.stringStatustoEnumStatus(updateProject.getStatus()));
        project.setStartDate(updateProject.getStartDate());
        project.setEndDate(updateProject.getEndDate());
        Project savedProject = projectRepository.save(project);
        return mapper.projectToProjectDto(savedProject);
    }

}
