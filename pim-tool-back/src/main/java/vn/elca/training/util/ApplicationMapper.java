package vn.elca.training.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.EndDateB4StartDateException;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.StatusNotAvailableException;
import vn.elca.training.model.exception.VisaNotExistException;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.CompanyGroupService;
import vn.elca.training.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setGroupId(entity.getCompanyGroup().getId());
        dto.setCustomer(entity.getCustomer());
        dto.setMemberVisaFromInner(entity.getMemberVisa()); // setMemberVisaFromInner() transfer Set<String> visas to a String visas
        dto.setStatusFromInner(entity.getProjectStatus()); // setStatusFromInner() transfer enum status to String status
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        return dto;
    }
    public Project projectDtoToProject(Project project, ProjectDto dto) throws StatusNotAvailableException, GroupNotFoundException, VisaNotExistException {
       Set<String> setInputVisa = this.stringVisasToSetVisas(dto.getMembersVisa());
        project.setProjectNumber(dto.getProjectNumber());
        project.setName(dto.getName());
        project.setCustomer(dto.getCustomer());
        project.setCompanyGroup(companyGroupService.findById(dto.getGroupId()));
        // input visa can have value "" and after transfer into a set, it can contain one "" element
        if(!(setInputVisa.size() == 1 && setInputVisa.contains(""))) {
            if (userService.checkIfVisasNotExist(setInputVisa)) { // check if visas valid before setting them to project
                project.setMembers(userRepository.findMembersByVisa(setInputVisa));
            }
        }
        project.setProjectStatus(this.stringStatustoEnumStatus(dto.getStatus())); // transfer String status to enum b4 set to project
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        return project;
    }

    public Set<String> stringVisasToSetVisas(String visas){
        String[] visa = visas.split(",");
        return Arrays.stream(visa)
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    public Enum<ProjectStatus> stringStatustoEnumStatus(String status) throws StatusNotAvailableException {
        if(status.equals("Planned")){
            return ProjectStatus.PLA;
        }
        else if(status.equals("In progess")){
            return ProjectStatus.INP;
        }
        else if(status.equals("New")){
            return ProjectStatus.NEW;
        }
        else if(status.equals("Finished")){
            return ProjectStatus.FIN;
        }
        else throw new StatusNotAvailableException(status);
        }
}
