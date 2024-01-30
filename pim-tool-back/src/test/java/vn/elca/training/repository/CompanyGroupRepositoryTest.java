package vn.elca.training.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.dao.CompanyGroupRepository;
import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QCompanyGroup;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value= SpringRunner.class)
public class CompanyGroupRepositoryTest {

    @Autowired
    CompanyGroupRepository companyGroupRepository;
    @PersistenceContext
    private EntityManager em;


    @Test
    public void testSave() {
        final String GROUP_NAME = "Group1";
        CompanyGroup companyGroupsaved = companyGroupRepository.save(new CompanyGroup(GROUP_NAME));
        Assert.assertEquals("Group1", companyGroupsaved.getName());
    }

    @Test
    public void testCountGroup() {
        companyGroupRepository.save(new CompanyGroup("Group2"));
        Assert.assertEquals(2, companyGroupRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String GROUP_NAME = "Group3";
        companyGroupRepository.save(new CompanyGroup(GROUP_NAME));
        CompanyGroup companyGroup = new JPAQuery<CompanyGroup>(em)
                .from(QCompanyGroup.companyGroup)
                .where(QCompanyGroup.companyGroup.name.eq(GROUP_NAME))
                .fetchFirst();
        Assert.assertEquals(GROUP_NAME, companyGroup.getName());
    }
}