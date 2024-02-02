package vn.elca.training.repository;

import java.time.LocalDate;
import java.util.*;

import static org.fest.assertions.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.dao.CompanyGroupRepository;
import vn.elca.training.model.entity.*;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value=SpringRunner.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyGroupRepository companyGroupRepository;

    @Test
    public void testCountAll() {
        projectRepository.save(new Project("KSTA", LocalDate.now()));
        projectRepository.save(new Project("LAGAPEO", LocalDate.now()));
        projectRepository.save(new Project("ZHQUEST", LocalDate.now()));
        projectRepository.save(new Project("SECUTIX", LocalDate.now()));
        Assert.assertEquals(9, projectRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String PROJECT_NAME = "KSTA";
        projectRepository.save(new Project(PROJECT_NAME, LocalDate.now()));
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq(PROJECT_NAME))
                .fetchFirst();
        Assert.assertEquals(PROJECT_NAME, project.getName());
    }

    @Test
    //Test save one project
    public void testSaveOne() {
        Project savedProject = projectRepository.save(new Project("Test saving Project", LocalDate.now()));
        Project retriveSavedProject = projectRepository.findById(savedProject.getId()).orElse(null);
        Assert.assertNotNull(retriveSavedProject);
        Assert.assertEquals("Test saving Project", retriveSavedProject.getName());
    }

    @Test
    public void testSaveManyProjects() {
        User groupLeader1 = new User("QMV");
        User groupLeader2 = new User("HNH");
        User projectLeader1 = new User("HTV");
        User projectLeader2 = new User("QKP");
        User projectLeader3 = new User("MKN");
        User member1 = new User("TQP");
        User member2 = new User("NQN");
        User member3 = new User("PHL");

        Project projectNumber1 = new Project("Test project number 1", LocalDate.now(), true, projectLeader1);
        Project projectNumber2 = new Project("Test project number 2", LocalDate.now(), false, projectLeader2);
        Project projectNumber3 = new Project("Test project number 3", LocalDate.now(), true, projectLeader3);

        CompanyGroup companyGroup1 = new CompanyGroup("Test group number 1", groupLeader1);
        CompanyGroup companyGroup2 = new CompanyGroup("Test group number 2", groupLeader2);

        //set member for project
        Set<User> memberproject1 = new HashSet<>();
        memberproject1.add(member1);
        memberproject1.add(member2);
        memberproject1.add(member3);
        projectNumber1.setMember(memberproject1);

        //set projects' group
        projectNumber1.setCompanyGroup(companyGroup1);
        projectNumber2.setCompanyGroup(companyGroup1);
        projectNumber3.setCompanyGroup(companyGroup1);

        // set projects to group
        companyGroup1.addProject(projectNumber1);
        companyGroup1.addProject(projectNumber2);
        companyGroup1.addProject(projectNumber3);
        //
        companyGroupRepository.save(companyGroup1);
        List<Project> projects = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.contains("Test project number"))
                .fetch();
        assertThat(extractProperty("name").from(companyGroup1.getProjects())).isEqualTo(extractProperty("name").from(projects));

    }

    @Test
    public void testRemoveProject(){
        Project savedProject = projectRepository.save(new Project("project to delete", LocalDate.now()));
        Assert.assertNotNull(projectRepository.findById(savedProject.getId()));
        projectRepository.delete(savedProject);
        Assert.assertEquals(Optional.empty(), projectRepository.findById(savedProject.getId()));

    }

    @Test
    public void simpleTestFindProject() {
        User projectLeader = new User("HTV");
        final String PROJECT_NAME = "simple test project";
        Project simpleTestProject = new Project(PROJECT_NAME, LocalDate.now(), true, projectLeader);
        projectRepository.save(simpleTestProject);
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq("simple test project")
                        .and(QProject.project.activated.eq(true)))
                .fetchFirst();
        Assert.assertNotNull(project);
        Assert.assertEquals(PROJECT_NAME, project.getName());
        }

    @Test
    public void complextTestFindProject(){
        //init pl and gl
        User groupLeader1 = new User("QMV");
        User projectLeader1 = new User("HTV");
        User projectLeader2 = new User("QKP");

        CompanyGroup companyGroup1 = new CompanyGroup("Test group for complex query", groupLeader1);

        // init projects
        Project projectNumber1 = new Project("Test project for complex query", LocalDate.now(), true, projectLeader1);
        Project projectNumber2 = new Project("Test project for complex query number 2", LocalDate.now(), false, projectLeader2);


        projectNumber1.setCompanyGroup(companyGroup1);
        projectNumber2.setCompanyGroup(companyGroup1);
        projectNumber1.setCustomer("Customer 1");
        projectNumber2.setCustomer("Customer 2");

        companyGroup1.addProject(projectNumber1);
        companyGroup1.addProject(projectNumber2);

        companyGroupRepository.save(companyGroup1);

        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.companyGroup, QCompanyGroup.companyGroup)
                .where(QProject.project.name.contains("complex")
                        .and(QProject.project.activated.eq(true))
                        .and(QCompanyGroup.companyGroup.name.contains("complex"))
                        .and(QProject.project.customer.contains("Customer")))
                .fetchFirst();

        Assert.assertNotNull(project);
        Assert.assertEquals(companyGroup1.getName(), project.getCompanyGroup().getName());

    }

}
