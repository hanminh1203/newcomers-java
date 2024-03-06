package vn.elca.training.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.StatusNotAvailableException;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.CompanyGroupService;
import vn.elca.training.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gtn
 */
@Component
public class  ApplicationMapper {
    public ApplicationMapper() {
        // Mapper utility class
    }
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CompanyGroupService companyGroupService;

    @Autowired
    CompanyGroupRepository companyGroupRepository;

    public ProjectDto projectToProjectDto(Project entity){
        ProjectDto dto = new ProjectDto();
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setGroupId(entity.getCompanyGroup().getId());
        dto.setCustomer(entity.getCustomer());
        dto.setMemberVisa(entity.getMemberVisa().stream()
                .reduce((s1, s2) -> s1 + "," + s2)
                .orElse(""));
        dto.setStatus(String.valueOf(entity.getProjectStatus()));
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        return dto;
    }
    public Project projectDtoToProject(Project project, ProjectDto dto) throws StatusNotAvailableException, GroupNotFoundException {
        project.setProjectNumber(dto.getProjectNumber());
        project.setName(dto.getName());
        project.setCustomer(dto.getCustomer());
        project.setCompanyGroup(companyGroupService.findById(dto.getGroupId()));
        project.setMembers(userRepository.findMembersByVisa(this.stringVisasToSetVisas(dto.getMembersVisa())));
        project.setProjectStatus(this.stringStatustoEnumStatus(dto.getStatus()));
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        return project;
    }

    public Set<String> stringVisasToSetVisas(String visas){
        String[] visa = visas.split(",");
        Set<String> setVisas = new HashSet<>(List.of(visa));
        System.out.println(setVisas);
        return setVisas;
    }

    public Enum<ProjectStatus> stringStatustoEnumStatus(String status) throws StatusNotAvailableException {
        switch (status){
            case "PLA":
                return ProjectStatus.PLA;
            case "NEW":
                return ProjectStatus.NEW;
            case "FIN":
                return ProjectStatus.FIN;
            case "INP":
                return ProjectStatus.INP;
            default: throw new StatusNotAvailableException(status);
        }
    }
}
