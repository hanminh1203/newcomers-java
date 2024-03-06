package vn.elca.training.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gtn
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<String> getEmployeeVisa() {
        List<Employee> employees = userRepository.findAll();
        List<String> allEmployeeVisas = new ArrayList<>();
        for (Employee employee: employees){
            allEmployeeVisas.add(employee.getVisa());
        }
        return allEmployeeVisas;
    }
}
