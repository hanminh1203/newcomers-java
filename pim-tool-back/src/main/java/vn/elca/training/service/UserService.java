package vn.elca.training.service;

import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.VisaNotExistException;

import java.util.List;
import java.util.Set;

/**
 * @author gtn
 *
 */
public interface UserService {

    List<String> getEmployeeVisa();
    boolean checkIfVisasNotExist(Set<String> visas) throws VisaNotExistException;

}
