package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

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
    public String searchById(@PathVariable long id) {
//        Function<Project, String> detail = project -> {
//            return "id=" + project.getId() +
//                    ", name='" + project.getName() + '\'' +
//                    ", finishingDate=" + project.getFinishingDate() +
//                    ", customer='" + project.getCustomer() + '\'';
//        };
//        return detail.apply(projectService.findById(id).get());
        return projectService.findById(id).get().toString();
    }

    @PostMapping("/{id}")
    public String updateProject (@PathVariable long id, @RequestBody Project project) {
        try {
            projectService.updateProject(id, project);
            return "Successfully updated";
        }
        catch (Exception e){
            return e.toString();
        }
    }

}

