package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.service.UserService;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractApplicationController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyGroupRepository companyGroupRepository;


}
