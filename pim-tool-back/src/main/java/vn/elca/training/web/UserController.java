package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.service.UserService;

import java.util.List;

/**
 * @author gtn
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController extends AbstractApplicationController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyGroupRepository companyGroupRepository;

    @GetMapping("")
    ResponseEntity<List<String>> getEmployeeVisa(){
        return new ResponseEntity<>(userService.getEmployeeVisa(), HttpStatus.OK) ;
    }
}
