package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import java.util.List;
import java.util.Optional;

/**
 * @author vlp
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project findById(long id) throws ProjectNotFoundException {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        else {
            throw new ProjectNotFoundException(id);
        }
    }
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByProjectName(String keyWord) {
        return projectRepository.findProjectByName(keyWord);
    }


    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Project updateProject(long id, Project updateProject){
        Project project = projectRepository.findById(id).orElseThrow( ()-> new RuntimeException("Not found"));
        project.setName(updateProject.getName());
        project.setCustomer(updateProject.getCustomer());
        project.setEndDate(updateProject.getEndDate());
        return projectRepository.save(project);
    }

}
