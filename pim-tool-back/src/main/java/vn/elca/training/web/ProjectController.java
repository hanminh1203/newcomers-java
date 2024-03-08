package vn.elca.training.web;

import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
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
import vn.elca.training.model.exception.*;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.ArrayList;
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


    @GetMapping("")
    public ResponseEntity<List<ProjectDto>> search() {
        List<ProjectDto> projectDtoList = projectService.findAll()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProjectDto>> searchByKeyWord(@PathVariable String keyword) {
            List<Project> projects = projectRepository.findAll();
            List<Project> filterdProjects = new ArrayList<>();
            String criteria = StringUtils.trimToEmpty(keyword);
            if (!StringUtils.isBlank(criteria)) {
                for (Project project : projects) {
                    if (StringUtils.containsIgnoreCase(project.getName(), criteria)
                            || StringUtils.containsIgnoreCase("" + project.getProjectNumber(), criteria)
                            || StringUtils.containsIgnoreCase(project.getCustomer(), criteria)){
                        filterdProjects.add(project);
                    }
                }
            }
            List<ProjectDto> projectDtoList = filterdProjects
                    .stream()
                    .map(mapper::projectToProjectDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> searchById(@PathVariable long id) throws ProjectNotFoundException {
        Project project = projectService.findById(id);
        ProjectDto projectDto = mapper.projectToProjectDto(project);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable long id, @RequestBody ProjectDto inputProjectDto) throws ProjectNotFoundException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException {
        ProjectDto updatedProjectDto = projectService.updateProject(id, inputProjectDto);
        return new ResponseEntity(updatedProjectDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProjectDto> CreateNewProject(@RequestBody ProjectDto inputProjectDto) throws ProjectNumberAlreadyExistsException, StatusNotAvailableException, GroupNotFoundException, VisaNotExistException {
        int projectNumber = inputProjectDto.getProjectNumber();
        if(projectRepository.countByNumber(projectNumber)>0){
            throw new ProjectNumberAlreadyExistsException(projectNumber);
        }
        else {
            Project saveProject = new Project();
             saveProject = projectRepository.save(mapper.projectDtoToProject(saveProject, inputProjectDto));
             ProjectDto savedProjectDto = mapper.projectToProjectDto(saveProject);
            return new ResponseEntity(savedProjectDto , HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable long id) throws ProjectNotFoundException {
        Project project = projectService.findById(id);
        ProjectDto projectDto = mapper.projectToProjectDto(project);
        projectRepository.delete(project);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProjects(@RequestParam("ids") List<Long> projectIds){
        projectRepository.deleteAllByIds(projectIds);
        return new ResponseEntity<>("Delete project with id:"+ projectIds.toString(), HttpStatus.OK);
    }
}

