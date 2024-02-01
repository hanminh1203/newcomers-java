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


    @Test
    public void testMaintainProject(){
        Project savedProject = projectRepository.save(new Project("Project_Test_Maintain", LocalDate.now(), true));
        Assert.assertEquals(6,projectRepository.count());
        Project updatedProject = projectService.maintainProject(savedProject.getId());

        Project project = projectRepository.findById(savedProject.getId()).get();

        Assert.assertEquals(false, project.getActivated());
        Assert.assertEquals(7, projectRepository.count());
        Assert.assertTrue(updatedProject.getName().contains("Maint.2024"));
    }

    @Test
    public void testIsTrulyTransaction(){
        ProjectService projectService = applicationContext.getBean(ProjectService.class);
        String projectServiceClassName = projectService.getClass().getName();
        Assert.assertTrue(projectServiceClassName.contains("EnhancerBySpringCGLIB"));
    }
    @Test
    public void testIsNotTransaction(){
        TestService testService = applicationContext.getBean(TestService.class);
        String name = testService.getClass().getName();
        Assert.assertTrue(name.contains("EnhancerBySpringCGLIB"));
    }
}
