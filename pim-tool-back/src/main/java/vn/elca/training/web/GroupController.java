package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.service.CompanyGroupService;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/groups")
public class GroupController extends AbstractApplicationController{

    @Autowired
    CompanyGroupService companyGroupService;
    @GetMapping("")
    ResponseEntity<List<Long>> getAllGroup(){
        return new  ResponseEntity(companyGroupService.getAllGroups(), HttpStatus.OK);
    }
}
