package vn.elca.training.service.impl.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.Optional;

/**
 * @author gtn
 *
 */
@Service("SecondOne")
@Profile("dummy")
public class SecondDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() { throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public List<Project> findByProjectName(String keyWord) {
        return null;
    }

    @Override
    public Optional<Project> findById(long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project maintainProject(long id) {
        return null;
    }

    @Override
    public Project updateProject(long id, Project updateProject){
        Project project = projectRepository.findById(id).orElseThrow( ()-> new RuntimeException("Not found"));
            project.setName(updateProject.getName());
            project.setCustomer(updateProject.getCustomer());
            project.setFinishingDate(updateProject.getFinishingDate());
            return projectRepository.save(project);
        }

    @Override
    public long count() {
        printCurrentActiveProfiles();
        throw new UnsupportedOperationException("This is second dummy service");
    }
}
