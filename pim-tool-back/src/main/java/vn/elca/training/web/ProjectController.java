package vn.elca.training.web;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

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

    @CrossOrigin("http://localhost:4200/")
    @GetMapping("/{id}")
    public ProjectDto searchById(@PathVariable long id) throws ProjectNotFoundException {
        Project project = projectService.findById(id);
        return mapper.projectToProjectDto(project);
    }


    @PostMapping("/{id}")
        public ResponseEntity<?> UpdateProject (@PathVariable long id, @RequestBody ProjectDto inputProjectDto) throws ProjectNotFoundException {

            Project updatingProject = projectService.findById(id);
                updatingProject = mapper.projectDtoToProject(updatingProject, inputProjectDto);
                projectService.updateProject(id, updatingProject);
                ProjectDto outputProjectDto = mapper.projectToProjectDto(updatingProject);
                return new ResponseEntity<ProjectDto>(outputProjectDto, HttpStatus.OK);
            }
    }

