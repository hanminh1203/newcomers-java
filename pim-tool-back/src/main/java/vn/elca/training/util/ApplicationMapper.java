package vn.elca.training.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.repository.UserRepository;

/**
 * @author gtn
 */
@Component
public class  ApplicationMapper {
    public ApplicationMapper() {
        // Mapper utility class
    }

    @Autowired
    CompanyGroupRepository companyGroupRepository;
    @Autowired
    UserRepository userRepository;

    public ProjectDto projectToProjectDto(Project entity){
        ProjectDto dto = new ProjectDto();
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setGroupName(entity.getCompanyGroup().getGroupLeader().getFirstName());
        dto.setCustomer(entity.getCustomer());
        dto.setMemberVisa(entity.getMemberVisa());
        dto.setStatus(entity.getProjectStatus());
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        return dto;
    }
    public Project projectDtoToProject(Project project, ProjectDto dto){
        project.setProjectNumber(dto.getProjectNumber());
        project.setCustomer(dto.getCustomer());
        project.setName(dto.getName());
        project.setProjectStatus(dto.getStatus());
        project.setStartDate(dto.getStartDate());
        project.setMembers(userRepository.findMembersByVisa(dto.getMembersVisa()));
        project.setEndDate(dto.getEndDate());
        return project;
    };
}
