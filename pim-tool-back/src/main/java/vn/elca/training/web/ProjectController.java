package vn.elca.training.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.InputProjectDto;
import vn.elca.training.model.dto.OutputProjectDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.AuditService;
import vn.elca.training.service.ProjectService;

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

