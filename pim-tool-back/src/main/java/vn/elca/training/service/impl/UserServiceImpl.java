package vn.elca.training.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.VisaNotExistException;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.UserService;
import vn.elca.training.util.ApplicationMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationMapper mapper;

    @Override
    public List<EmployeeDto> getEmployees() {
        List<Employee> employees = userRepository.findAll();
        return employees.stream()
                .map(e -> mapper.employeeToEmployeeDto(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }


//    @Override
//    public List<String> getEmployeeVisa() {
//        List<Employee> employees = userRepository.findAll();
//        List<String> allEmployeeVisas = new ArrayList<>();
//        for (Employee employee: employees){
//            allEmployeeVisas.add(employee.getVisa());
//        }
//        return allEmployeeVisas;
//    }
//
//    @Override
//    public boolean checkIfVisasNotExist(Set<String> inputVisas) throws VisaNotExistException {
//        // get all employee visas from database
//        List<String> visaFromDbList = this.getEmployeeVisa();
//        visaFromDbList.replaceAll(String::trim);
//        // filter input visas through database visas
//        List<String> filterVisaList = inputVisas.stream()
//                .map(String::trim)
//                .filter(visa -> !visaFromDbList.contains(visa))
//                .collect(Collectors.toList());
//        if(filterVisaList.isEmpty())
//            return true;
//        else
//            // if there's any visa that have not been filtered, means it not a available visa
//            throw new VisaNotExistException(filterVisaList);
//    }
}
