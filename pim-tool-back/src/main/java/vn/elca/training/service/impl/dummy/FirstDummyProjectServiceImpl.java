package vn.elca.training.service.impl.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.Optional;

/**
 * @author gtn
 *
 */
@Component
@Profile("dummy")
@Primary
public class FirstDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project updateProject(long id, Project updateProject) {

        return updateProject;
    }

    @Override
    public List<Project> findAll() {
        throw new UnsupportedOperationException("This is first dummy service");
    }

    @Override
    public List<Project> findByProjectName(String keyWord) {
        return null;
    }

    @Override
    public Optional<Project> findById(long id) {
        return null;
    }
    @Override
    public long count() {
        printCurrentActiveProfiles();
        throw new UnsupportedOperationException("This is first dummy service");
    }
}
