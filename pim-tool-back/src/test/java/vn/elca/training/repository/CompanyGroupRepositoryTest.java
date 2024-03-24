package vn.elca.training.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.entity.QCompanyGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value= SpringRunner.class)
public class CompanyGroupRepositoryTest {

    @Autowired
    CompanyGroupRepository companyGroupRepository;
    @PersistenceContext
    private EntityManager em;

}