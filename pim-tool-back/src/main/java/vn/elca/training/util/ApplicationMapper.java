package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.entity.User;

import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class  ApplicationMapper {
    public ApplicationMapper() {
        // Mapper utility class
    }

    public ProjectFindByIdDto projectToProjectFindByIdDto(Project entity){
        ProjectFindByIdDto projectFindByIdDto = new ProjectFindByIdDto();
        projectFindByIdDto.setId(entity.getId());
        projectFindByIdDto.setCustomer(entity.getCustomer());
        projectFindByIdDto.setFinishingDate(entity.getFinishingDate());
        projectFindByIdDto.setName(entity.getName());
        return projectFindByIdDto;
    }
    public Project inputProjectToProject(Project projectToUpdate, InputProjectDto entity){
        if (!entity.getName().isBlank()) projectToUpdate.setName(entity.getName());
        if (!entity.getCustomer().isBlank()) projectToUpdate.setCustomer(entity.getCustomer());
        projectToUpdate.setFinishingDate(entity.getFinishingDate());
        return projectToUpdate;
    }
    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFinishingDate(entity.getFinishingDate());

        return dto;
    }

    public TaskDto taskToTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTaskName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setProjectName(task.getProject() != null
                ? task.getProject().getName()
                : null);

        return dto;
    }

    public UserDto userToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setTasks(user.getTasks().stream().map(this::taskToTaskDto).collect(Collectors.toList()));

        return dto;
    }
}
