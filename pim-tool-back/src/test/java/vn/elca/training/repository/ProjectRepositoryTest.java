package vn.elca.training.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        Project projectNumber1 = new Project("Test project number 1", LocalDate.now());
        Project projectNumber2 = new Project("Test project number 2", LocalDate.now());
        Project projectNumber3 = new Project("Test project number 3", LocalDate.now());

        CompanyGroup companyGroup1 = new CompanyGroup("Test group number 1");
        CompanyGroup companyGroup2 = new CompanyGroup("Test group number 2");

        //set project leaders for projects
        projectNumber1.setProjectLeader(projectLeader1);
        projectNumber2.setProjectLeader(projectLeader2);
        projectNumber3.setProjectLeader(projectLeader3);

        //set member for project
        Set<User> memberproject1 = new HashSet<>();
        memberproject1.add(member1);
        memberproject1.add(member2);
        memberproject1.add(member3);
        projectNumber1.setMember(memberproject1);

        // set group leader
        companyGroup1.setGroupLeader(groupLeader1);
        companyGroup2.setGroupLeader(groupLeader2);

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
    public void simpleTestFindProject() {
    Project simpleTestProject = new Project("simple test project", LocalDate.now());
    simpleTestProject.setStatus(true);
    projectRepository.save(simpleTestProject);
    Project project = new JPAQuery<Project>(em)
            .from(QProject.project)
            .where(QProject.project.name.eq("simple test project")
                    .and(QProject.project.status.eq(true)))
            .fetchFirst();
    Assert.assertEquals(simpleTestProject.getName(), project.getName());
    }

    @Test
    public void complextTestFindProject(){
        User groupLeader1 = new User("QMV");
        User projectLeader1 = new User("HTV");
        Project projectNumber1 = new Project("Test project for complext query", LocalDate.now(), true);
        CompanyGroup companyGroup1 = new CompanyGroup("Test group for complex query");

        projectNumber1.setProjectLeader(projectLeader1);
        companyGroup1.setGroupLeader(groupLeader1);
        companyGroup1.addProject(projectNumber1);
        companyGroupRepository.save(companyGroup1);

        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.projectLeader, QUser.user)
                .where(QProject.project.name.contains("complex")
                        .and(QUser.user.username.eq("HTV")))
                .fetchFirst();

        Assert.assertEquals(projectNumber1.getProjectLeader().getUsername(), project.getProjectLeader().getUsername());
    }

}
