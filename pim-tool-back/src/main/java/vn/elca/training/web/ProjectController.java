package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.InputProjectDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
    @Qualifier("SecondOne")
    private ProjectService projectService;

    @GetMapping("/search")
    public List<ProjectDto> search() {
        return projectService.findAll()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/search/{keyword}")
    public List<ProjectDto> searchByKeyWord(@PathVariable String keyword) {
        return projectService.findByProjectName(keyword)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Object searchById(@PathVariable long id) {
        return mapper.projectToProjectFindByIdDto(projectService.findById(id));
    }

    @PostMapping("/{id}")
    public ProjectDto InputProjectDto (@PathVariable long id, @RequestBody InputProjectDto inputProjectDto) {
        Project project = mapper.inputProjectToProject( projectService.findById(id), inputProjectDto);
        project = projectService.updateProject(project.getId(), project);
        return mapper.projectToProjectDto(project);
    }

}

