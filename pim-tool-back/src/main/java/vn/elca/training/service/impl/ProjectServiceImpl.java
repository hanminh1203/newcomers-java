package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import vn.elca.training.service.impl.dummy.FirstDummyProjectServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * @author vlp
 *
 */
@Service
@Profile("!dummy | dev")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    @Qualifier("FirstDummyProjectServiceImpl")
    private ProjectRepository projectRepository;

    @Override
    public void updateProject(long id, Project updateProject) {

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
    public Optional<Project> findById(long id) {
        return projectRepository.findById(id);
    }


    @Override
    public long count() {
        return projectRepository.count();
    }

}
