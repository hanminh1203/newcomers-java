package vn.elca.training.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/employees")
public class EmployeeController extends AbstractApplicationController{

    @Autowired
    UserService employeeService;

    @GetMapping("")
    ResponseEntity<List<EmployeeDto>> getAllGroup(){
        return new  ResponseEntity(employeeService.getEmployees(), HttpStatus.OK);
    }
}
