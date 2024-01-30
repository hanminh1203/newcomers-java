package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.InputProjectDto;
import vn.elca.training.model.dto.OutputProjectDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
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
    public ResponseEntity<?> searchById(@PathVariable long id) {
        Optional<Project> project = projectService.findById(id);
        if(project.isPresent()){
            Project foundProject = project.get();
            OutputProjectDto outputProjectDto = mapper.projectToOutputProjectDto(foundProject);
            return new ResponseEntity<>(outputProjectDto, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Not found Project "+ id, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> UpdateProject (@PathVariable long id, @RequestBody InputProjectDto inputProjectDto) {

        Optional<Project> project = projectService.findById(id);
        if(project.isPresent()){
            Project updatingProject = project.get();
            updatingProject = mapper.inputProjectToProject(updatingProject, inputProjectDto);
            projectService.updateProject(id, updatingProject);
            OutputProjectDto outputProjectDto = mapper.projectToOutputProjectDto(updatingProject);
            return new ResponseEntity<OutputProjectDto>(outputProjectDto, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Not found Project "+ id,HttpStatus.NOT_FOUND);
        }
    }

