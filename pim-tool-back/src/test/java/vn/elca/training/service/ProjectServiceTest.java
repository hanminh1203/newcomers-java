package vn.elca.training.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value= SpringRunner.class)
public class ProjectServiceTest {
    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private ProjectService projectService;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;

}
